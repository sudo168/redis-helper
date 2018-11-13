package net.ewant.redis.utils;

import net.ewant.redis.RedisDB;
import net.ewant.redis.RedisHelper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 基于setnx原子操作的redis分布式锁, 支持锁重入，死锁检测
 *
 * @author huangzh
 * @date 2016年09月25日
 */
public class RedisDistributeLock {

    // 随机数生成器
    public static final Random RANDOM = new Random();
    private static final Logger logger = LoggerFactory.getLogger(RedisDistributeLock.class);
    // 默认锁超时,秒
    private static final int DEFAULT_LOCK_TIMEOUT = 120;
    // 默认锁等待机制,毫秒
    private static final int DEFAULT_LOCK_WAIT_TIMEOUT = 3000;
    private static final String LOCK_KEY_SUFFIX = "_SF-LOCK";
    /**
     * 标记当前锁时间
     * 解决问题：
     * 1.根据该值是否存在可判断当前线程是否已经有锁，避免同线程访问同一资源锁重入问题
     * 2.在释放锁时，可根据该值判断锁是否过期，避免在当前锁过期时释放掉其他线程的锁
     */
    private static final ThreadLocal<Map<String, Long>> CURRENT_LOCKED_TIME = new ThreadLocal<Map<String, Long>>();
    /**
     * 记录锁重入次数
     * 解决问题：
     * 1. 在释放锁时，逐级递减，防止直接重置锁
     */
    private static final ThreadLocal<Map<String, Integer>> RELOCK_COUNT = new ThreadLocal<Map<String, Integer>>();
    private static RedisHelper redisHelper;

    private RedisDistributeLock() {
    }

    /**
     * 获取锁，可设置是否等待及等待时间，过期时间。
     *
     * @param key           需要锁操作的资源key
     * @param expireSecond  锁过期时间（秒）
     * @param waitLockMills 尝试1次拿不到锁时，继续等待的毫秒数。小于1则不等待
     * @return
     */
    public static boolean lock(String key, int expireSecond, int waitLockMills) {
        try {
            if (StringUtils.isBlank(key)) {
                return false;
            }
            key += LOCK_KEY_SUFFIX;
            Map<String, Long> localMap = CURRENT_LOCKED_TIME.get();
            if (localMap != null) {
                Long startLock = localMap.get(key);
                if (startLock != null && startLock > System.currentTimeMillis()) {// 有锁且锁未过期  , 防止锁重入
                    Map<String, Integer> relockMap = RELOCK_COUNT.get();
                    if (relockMap != null) {
                        Integer relock = relockMap.get(key);
                        if (relock == null) {
                            relock = 1;
                        } else {
                            relock++;
                        }
                        relockMap.put(key, relock);
                    } else {
                        HashMap<String, Integer> map = new HashMap<String, Integer>();
                        map.put(key, 1);
                        RELOCK_COUNT.set(map);
                    }
                    return true;
                }
            } else {
                CURRENT_LOCKED_TIME.set(new HashMap<String, Long>());
            }
            boolean waitLock = waitLockMills > 0;
            if (expireSecond < 1) expireSecond = DEFAULT_LOCK_TIMEOUT;
            long start = System.currentTimeMillis();
            do {
                //1. 第一次获取锁或者锁过期时返回null
                long expireAt = start + expireSecond * 1000;
                Long lock = getRedisHelper().cmd(RedisDB._0).setNx(key, String.valueOf(expireAt));
                if (lock != null && lock > 0) {// 在获取锁成功后，设置过期时间前，服务器挂了的话，有可能造成“死锁”
                    getRedisHelper().cmd(RedisDB._0).expire(key, expireSecond);
                    CURRENT_LOCKED_TIME.get().put(key, expireAt);
                    return true;
                }
                if (!waitLock) break;
                try {
                    Thread.sleep(RANDOM.nextInt(495) + 5);// 随机5-500毫秒休眠
                } catch (Exception e) {
                    logger.error("lock do-while ERROR ~~~", e);
                    break;
                }
            } while (System.currentTimeMillis() - start < waitLockMills);
            return false;
        } catch (Exception e) {
            logger.error("lock ERROR ~~~", e);
            return false;
        }
    }

    /**
     * 获取锁，直到获取超时才返回（DEFAULT_LOCK_WAIT_TIMEOUT）
     *
     * @param key          需要锁操作的资源key
     * @param expireSecond 锁过期时间（秒）
     * @return
     */
    public static boolean lock(String key, int expireSecond) {
        return lock(key, expireSecond, DEFAULT_LOCK_WAIT_TIMEOUT);
    }

    /**
     * 获取锁，直到获取超时才返回（DEFAULT_LOCK_WAIT_TIMEOUT），使用默认的锁过期时间（DEFAULT_LOCK_TIMEOUT）
     *
     * @param key 需要锁操作的资源key
     * @return
     */
    public static boolean lock(String key) {
        return lock(key, DEFAULT_LOCK_TIMEOUT);
    }

    /**
     * 释放锁
     *
     * @param key 需要解锁的资源key
     */
    public static boolean unlock(String key) {
        try {
            if (StringUtils.isBlank(key)) {
                return false;
            }
            key += LOCK_KEY_SUFFIX;
            Long startLock = CURRENT_LOCKED_TIME.get().get(key);
            if (startLock != null) {// 有锁
                Map<String, Integer> relockMap = RELOCK_COUNT.get();
                Integer relock = null;
                if (relockMap != null) {
                    relock = relockMap.get(key);
                }
                if (relock == null || relock == 0) {
                    CURRENT_LOCKED_TIME.get().remove(key);
                    if (startLock > System.currentTimeMillis()) {//且锁未过期
                        try {
                            Long del = getRedisHelper().cmd(RedisDB._0).del(key);
                            if (del != null && del > 0L) {
                                return true;
                            }
                            logger.error(Thread.currentThread().getName() + " 解锁失败：" + key + ":" + startLock);
                            return false;
                        } catch (Exception e) {
                            logger.error(Thread.currentThread().getName() + " 解锁失败：" + key + ":" + startLock, e);
                        }
                    }
                    logger.error(Thread.currentThread().getName() + " 锁过期：" + key + ":" + startLock);
                } else {
                    RELOCK_COUNT.get().put(key, --relock);
                }
                return true;
            }
        } catch (Exception e) {
            logger.error(Thread.currentThread().getName() + " locak 解锁失败：" + key, e);
        }
        return false;
    }

    private static RedisHelper getRedisHelper() {
        if (redisHelper == null) {
            throw new IllegalArgumentException("RedisHelper is required !");
        }
        return redisHelper;
    }

    public static void setRedisHelper(RedisHelper redisHelper){
        RedisDistributeLock.redisHelper = redisHelper;
    }
}