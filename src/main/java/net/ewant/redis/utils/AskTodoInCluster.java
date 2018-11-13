package net.ewant.redis.utils;

import net.ewant.redis.RedisDB;
import net.ewant.redis.RedisHelper;
import org.apache.log4j.Logger;

public class AskTodoInCluster {

	private static String ME;
	
    private static final Logger logger = Logger.getLogger(AskTodoInCluster.class);
    /**
     * 单位，秒
     */
    private static final int EXPIRE = 30;

    /**
     * 单位，秒
     */
    private static final int SWAP_WAIT = 120;

    private static final int DBINDEX = RedisDB._0;
    
    private static RedisHelper redisHelper;

    /**
     * 通过 setnx ， expire 30s 方式解决集群中不同节点操作同一资源的并发问题
     *
     * @param doWhat 做什么
     * @param which  具体是哪一个 （标记）
     * @return boolean
     */
    public static boolean ask(DoWhat doWhat, String... which) {
        return ask(doWhat, EXPIRE, which);
    }

    /**
     * 通过 setnx ， 默认 expire 30s 方式解决集群中不同节点操作同一资源的并发问题
     *
     * @param doWhat 做什么
     * @param expire 过期时间
     * @param which  具体是哪一个 （标记）
     * @return boolean
     */
    public static boolean ask(DoWhat doWhat, int expire, String... which) {
        String key = null;
        try {
            key = getKey(doWhat, which);
            Long exists = getRedisHelper().cmd(DBINDEX).setNx(key, ME);
            if (exists != null && exists > 0L) {
                expire = expire < 0 ? EXPIRE : expire;
                getRedisHelper().cmd(DBINDEX).expire(key, expire);
                return true;
            }
        } catch (Exception e) {
            logger.error("ask:" + key + " in " + expire, e);
        }
        return false;
    }

    /**
     * 交替做某事，如果一个节点在超过一定时间仍没有做事，则被其他相同节点代替
     * 通过 setnx ， expire 30s 方式解决集群中不同节点操作同一资源的并发问题
     *
     * @param doWhat 做什么
     * @param which  具体是哪一个 （标记）
     * @return Boolean 返回值：null、false、true。
     * null: 表示不能做
     * false : 表示可以做
     * true  : 表示可以做，且是替换做的（知道是替代其他节点做事情，可以接管资源，重新开始做）
     */
    public static Boolean askSwap(DoWhat doWhat, String... which) {
        return askSwap(doWhat, EXPIRE, SWAP_WAIT, which);
    }

    /**
     * 交替做某事，如果一个节点在超过一定时间仍没有做事，则被其他相同节点代替
     * 通过 setnx ， expire 30s 方式解决集群中不同节点操作同一资源的并发问题
     *
     * @param doWhat 做什么
     * @param expire 自定key过期时间（单位，秒）
     * @param which  具体是哪一个 （标记）
     * @return Boolean 返回值：null、false、true。
     * null: 表示不能做
     * false : 表示可以做
     * true  : 表示可以做，且是替换做的（知道是替代其他节点做事情，可以接管资源，重新开始做）
     */
    public static Boolean askSwap(DoWhat doWhat, int expire, String... which) {
        return askSwap(doWhat, expire, SWAP_WAIT, which);
    }

    /**
     * 交替做某事，如果一个节点在超过一定时间仍没有做事，则被其他相同节点代替
     * 通过 setnx ， expire 30s 方式解决集群中不同节点操作同一资源的并发问题
     *
     * @param doWhat   做什么
     * @param expire   自定key过期时间（单位，秒）
     * @param swapWait 自定替代的等待时间（单位，秒）
     * @param which    具体是哪一个 （标记）
     * @return Boolean 返回值：null、false、true。
     * null: 表示不能做
     * false : 表示可以做
     * true  : 表示可以做，且是替换做的（知道是替代其他节点做事情，可以接管资源，重新开始做）
     */
    public static Boolean askSwap(DoWhat doWhat, int expire, int swapWait, String... which) {
        String key = null;
        try {
            expire = expire < 0 ? EXPIRE : expire;
            swapWait = swapWait < 0 ? SWAP_WAIT : swapWait;
            key = getKey(doWhat, which);
            //1. 先判断是不是自己在做，是则更新时间
            String info = getRedisHelper().cmd(DBINDEX).get(key + "doing");
            long preTime = 0L;
            if (info != null) {
                String[] split = info.split("_");
                preTime = Long.parseLong(split[1]);
                String who = split[0];
                if (ME.equals(who)) {
                    getRedisHelper().cmd(DBINDEX).set(key + "doing", ME + "_" + String.valueOf(System.currentTimeMillis()));
                    if (System.currentTimeMillis() < 1000 * expire + preTime) {// 未过期
                        getRedisHelper().cmd(DBINDEX).expire(key, expire);
                    } else {// 已过期重新赋值
                        getRedisHelper().cmd(DBINDEX).setEx(key, expire, ME);
                    }
                    return false;
                }
            }
            //2. 不是自己做，但是没到时间（2分钟）返回false继续等待
            if (System.currentTimeMillis() < 1000 * swapWait + preTime) {
                return null;
            }
            //3. 没有人做info==null，或者不是自己做但需要替换做的。
            Long exists = getRedisHelper().cmd(DBINDEX).setNx(key, ME);
            if (exists != null && exists > 0L) {
                getRedisHelper().cmd(DBINDEX).set(key + "doing", ME + "_" + String.valueOf(System.currentTimeMillis()));
                getRedisHelper().cmd(DBINDEX).expire(key, expire);
                return info != null;//info != null 表示替代其他节点做事情
            }
            //3. 已有人做
            return null;
        } catch (Exception e) {
            logger.error("askSwap:" + key + " in " + expire + " swapWait " + swapWait, e);
        }
        //4. 异常返回
        return null;
    }

    /**
     * 刷新过期时间
     *
     * @param doWhat 做什么
     * @param which  具体是哪一个 （标记）
     */
    public static void refresh(DoWhat doWhat, String... which) {
        String key = getKey(doWhat, which);
        try {
			getRedisHelper().cmd(DBINDEX).expire(key, EXPIRE);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
    }

    private static String getKey(DoWhat doWhat, String... which) {
        StringBuilder sb = new StringBuilder("DO:" + doWhat.get() + ":");
        if (which != null && which.length > 0) {
            for (String f : which) {
                sb.append(f);
            }
            sb.append(":");
        }
        return sb.toString();
    }

    private static RedisHelper getRedisHelper() {
        if (redisHelper == null) {
            throw new IllegalArgumentException("RedisHelper is required !");
        }
        return redisHelper;
    }

    public static void init(RedisHelper redisHelper, String clusterId){
        AskTodoInCluster.redisHelper = redisHelper;
        AskTodoInCluster.ME = clusterId;
    }

    public interface DoWhat{
    	String get();
    }
}
