package net.ewant.redis.factory;

import net.ewant.redis.RedisConfiguration;
import net.ewant.redis.RedisConfiguration.RedisMode;
import net.ewant.redis.commands.RedisCommands;
import net.ewant.redis.connect.AbstractRedisConnection;
import net.ewant.redis.connect.RedisConnection;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.exceptions.JedisException;

import java.lang.reflect.Method;

public abstract class AbstractRedisConnectionFactory implements RedisConnectionFactory {

	protected final Logger log = LoggerFactory.getLogger(getClass().getName());
	
	private boolean usePool = true;
	private boolean useSsl = false;
	private int dbIndex = 0;
	private String clientName;
	
	protected RedisConfiguration config;
	
	protected RedisMode redisMode;
	
	protected Object pool;

	public AbstractRedisConnectionFactory(RedisConfiguration config) {
		this.config = config;
		this.dbIndex = config.getDbIndex();
		this.redisMode = config.getMode();
		this.pool = initPool();
	}

	protected RedisConnection getConnection(int retryCount) throws Exception {
		RedisConnection connect = null;
		try {
			if(redisMode == RedisMode.SINGLE){
				connect = getSingleConnection();
			}else if(redisMode == RedisMode.SENTINEL){
				connect = getSentinelConnection();
			}else if(redisMode == RedisMode.CLUSTER){
				connect = getClusterConnection();
			}
		} catch (Exception e) {
			if(e instanceof JedisException){
				int retry = config.getRetry();
				if(retry != 0){
					boolean canRetry ;
					log.error("retry to get Redis Connection. config retry[" + retry + "], current[" + (retryCount + 1) + "]");
					if(retry > 0){
						if(retryCount < retry){
							canRetry = true;
						}else{
							throw new JedisException("retry to get Redis Connection. count out..." + retryCount);
						}
					}else{
						canRetry = true;
					}
					if(canRetry){
						try {
							Thread.sleep(config.getRetryInterval() * 1000);
						} catch (InterruptedException e1) {
							throw new JedisException("thread Interrupted when waiting to get Redis Connection form pool.", e1);
						}
						return getConnection(++retryCount);
					}
				}
			}
			throw e;
		}

		return connect;
	}

	protected abstract Object initPool();

	@Override
	public RedisConfiguration getRedisConfiguration() throws Exception {
		return config;
	}

	public boolean isUsePool() {
		return usePool;
	}

	public void setUsePool(boolean usePool) {
		this.usePool = usePool;
	}

	public boolean isUseSsl() {
		return useSsl;
	}

	public void setUseSsl(boolean useSsl) {
		this.useSsl = useSsl;
	}

	public int getDbIndex() {
		return dbIndex;
	}

	public void setDbIndex(int dbIndex) {
		this.dbIndex = dbIndex;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public static class ProxyFactory {
		@SuppressWarnings("unchecked")
		public static <T> T create(Object target, Class<T> type){
			return (T) Enhancer.create(type, new ProxyMethodInterceptor(target));
		}
	}
	
	public static class ProxyMethodInterceptor implements MethodInterceptor {

		private static final Logger log = LoggerFactory.getLogger(ProxyMethodInterceptor.class);

		private long startExecTime = 0;

		private int retryTimes = 0;

		private Object target;

		public ProxyMethodInterceptor(Object target) {
			this.target = target;
		}

		@Override
		public Object intercept(Object proxy, Method method, Object[] args, MethodProxy proxyMethod) throws Throwable {
			//Object result = method.invoke(target, args);
			//Object result = proxyMethod.invokeSuper(proxy, args);
			Object result = null;
			if(target instanceof RedisConnection){
				AbstractRedisConnection redisConnection = (AbstractRedisConnection) target;
				String methodName = method.getName();
				if("select".equals(methodName)){
					// NOOP
				}else if("close".equals(methodName)){
					// NOOP
				}else{
					try {
						if(this.retryTimes == 0){
							startExecTime = System.currentTimeMillis();
						}
						boolean resetDB = redisConnection.resetDB();
						if (resetDB) {
							Object nativeConnection = redisConnection.getNativeConnection();
							if(nativeConnection instanceof ShardedJedis){
								if(args != null && args.length > 0){
									Object key = args[0];
									if(key instanceof String){
										Jedis shard = ((ShardedJedis)nativeConnection).getShard((String)key);
										shard.select(redisConnection.getSelectedDB());
										redisConnection.select(redisConnection.getSelectedDB());
									}
								}
							}else if(nativeConnection instanceof Jedis){
								((Jedis)nativeConnection).select(redisConnection.getSelectedDB());
							}else if(nativeConnection instanceof JedisCluster){
								// not support select
							}else{
								// unknown connection
							}
						}
						result = method.invoke(target, args);
					} catch (Exception e) {
						if(e instanceof JedisConnectionException){
							AbstractRedisConnectionFactory factory = (AbstractRedisConnectionFactory) redisConnection.getRedisConnectionFactory();
							RedisConfiguration redisConfiguration = factory.getRedisConfiguration();
							if(redisConfiguration.getRetry() != 0){
								try {
									Thread.sleep(redisConfiguration.getRetryInterval() * 1000);
								} catch (InterruptedException e1) {
									throw new JedisException("thread Interrupted when waiting to get Redis Connection to retry execute command [" + methodName + "].", e1);
								}
								log.error("Redis Connection lost when execute command [{}], retry...", methodName);
								RedisConnection newConnection = factory.getConnection(0);
								if(newConnection != null){
									target = newConnection;
									newConnection.setSelectedDB(redisConnection.getSelectedDB());
									this.retryTimes++;
									return intercept(proxy, method, args, proxyMethod);// 如果再次执行异常，上面getConnection(0)传0, 会再次重试。。。
								}
							}
						}
						throw e;
					} finally {
						this.retryTimes--;
						if(method.getDeclaringClass().isAssignableFrom(RedisCommands.class)){
							try {
								redisConnection.close();
							} catch (Exception e) {
								log.error(e.getMessage(), e);
							}
							if(this.retryTimes < 1){
								log.debug("execute redis command[{}] taking time: {}ms", methodName, System.currentTimeMillis() - startExecTime);
							}
						}
						if(this.retryTimes < 1){
							this.retryTimes = 0;
						}
					}
				}
			}else{
				result = method.invoke(target, args);
			}
			return result;
		}
		
	}
}
