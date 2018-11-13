package net.ewant.redis;

import net.ewant.redis.factory.JedisConnectionFactory;
import net.ewant.redis.utils.AskTodoInCluster;
import net.ewant.redis.utils.RedisDistributeLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@ConditionalOnProperty(value = "redis.mode")
@Configuration
@ConfigurationProperties(prefix = "redis")
public class RedisConfiguration {

	/**
	 * connectTimeout and soTimeout
	 */
	private int timeout = 6000;
	
	private String password;
	
	private int dbIndex;

	private int clusterId;
	/**
	 * 连接意外断开时（因连接异常而拿不到连接时，执行过程中连接断开时），尝试重新获取次数。-1 表示无限次，0 表示不重试，N 为具体次数
	 */
	private int retry = 5;
	/**
	 * 两次重连尝试之间的间隔，单位（秒）
	 */
	private int retryInterval = 3;
	
	RedisMode mode;
	
	HostAndPort single;
	
	List<RedisSentinelConfiguration> sentinel;
	
	RedisClusterConfiguration cluster;
	
	RedisPoolConfig pool;
	
	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getDbIndex() {
		return dbIndex;
	}

	public void setDbIndex(int dbIndex) {
		this.dbIndex = dbIndex;
	}

	public int getClusterId() {
		return clusterId;
	}

	public void setClusterId(int clusterId) {
		this.clusterId = clusterId;
	}

	public int getRetry() {
		return retry;
	}

	public void setRetry(int retry) {
		this.retry = retry;
	}

	public int getRetryInterval() {
		return retryInterval;
	}

	public void setRetryInterval(int retryInterval) {
		if(retryInterval < 1){
			throw new IllegalArgumentException("invalid value[" + retryInterval + "] of parameter retryInterval. please set a number larger 0");
		}
		this.retryInterval = retryInterval;
	}

	public HostAndPort getSingle() {
		return single;
	}

	public void setSingle(HostAndPort single) {
		this.single = single;
	}

	public RedisMode getMode() {
		return mode;
	}

	public void setMode(RedisMode mode) {
		this.mode = mode;
	}

	public List<RedisSentinelConfiguration> getSentinel() {
		return sentinel;
	}

	public void setSentinel(List<RedisSentinelConfiguration> sentinel) {
		this.sentinel = sentinel;
	}

	public RedisClusterConfiguration getCluster() {
		return cluster;
	}

	public void setCluster(RedisClusterConfiguration cluster) {
		this.cluster = cluster;
	}

	public RedisPoolConfig getPool() {
		return pool;
	}

	public void setPool(RedisPoolConfig pool) {
		this.pool = pool;
	}
	
	public static enum RedisMode{
		SINGLE(0),SENTINEL(1),CLUSTER(2);
		private int value;
		RedisMode(int mode){
			this.value = mode;
		}
		public int getValue() {
			return value;
		}
	}

	public static class RedisSentinelConfiguration{
		/**
		 * 主redis服务器的name
		 */
		private String master;
		/**
		 * 哨兵ip端口信息
		 */
		private List<HostAndPort> nodes;
		
		public String getMaster() {
			return master;
		}
		public void setMaster(String master) {
			this.master = master;
		}
		public List<HostAndPort> getNodes() {
			return nodes;
		}
		public void setNodes(List<HostAndPort> nodes) {
			this.nodes = nodes;
		}
	}
	
	public static class RedisClusterConfiguration{
		/**
		 * 连接重试次数
		 */
		private int maxRetry = 5;
		
		/**
		 * 集群节点ip端口信息
		 */
		private List<HostAndPort> nodes;

		public int getMaxRetry() {
			return maxRetry;
		}

		public void setMaxRetry(int maxRetry) {
			this.maxRetry = maxRetry;
		}

		public List<HostAndPort> getNodes() {
			return nodes;
		}

		public void setNodes(List<HostAndPort> nodes) {
			this.nodes = nodes;
		}

	}
	
	public static class RedisPoolConfig{
		/**
		 * 后进先出
		 */
		private boolean lifo = true;
		/**
		 * 从idle队列里面取对象时，若阻塞的话，则最大等待时长
		 */
		private long maxWaitMillis = -1;
		/**
		 * 处于idle状态超过此值时，会被destory
		 */
		private long minEvictableIdleTimeMillis = 60000;
		/**
		 * 处于idle状态超过此值，且处于idle状态个数大于minIdle时会被destory
		 */
		private long softMinEvictableIdleTimeMillis = 1800000;
		/**
		 * idle object evitor每次扫描的最多的对象数
		 */
		private int numTestsPerEvictionRun = 3;
		/**
		 * 当创建连接时，检测连接是否有效
		 */
		private boolean testOnCreate;
		/**
		 * 从池子拿出连接时，检测连接是否有效
		 */
		private boolean testOnBorrow;
		/**
		 * 归还连接时，检测连接是否有效
		 */
		private boolean testOnReturn;
		/**
		 * idle状态时，检测连接是否有效
		 */
		private boolean testWhileIdle = true;
		/**
		 * evitor线程执行时间间隔
		 */
		private long timeBetweenEvictionRunsMillis = 30000;
		/**
		 * 从池中取对象时，若池子用尽的话，是否阻塞等待 maxWaitMillis。此值为false时 maxWaitMillis设置无效
		 */
		private boolean blockWhenExhausted = true;
		/**
		 * 是否开启jmx监控
		 */
		private boolean jmxEnabled = true;
		/**
		 * 最大总连接数
		 */
		private int maxTotal = 8;
		/**
		 * 最大空闲连接数
		 */
	    private int maxIdle = 8;
	    /**
		 * 最小空闲连接数
		 */
	    private int minIdle = 0;
	    
		public boolean isLifo() {
			return lifo;
		}
		public void setLifo(boolean lifo) {
			this.lifo = lifo;
		}
		public long getMaxWaitMillis() {
			return maxWaitMillis;
		}
		public void setMaxWaitMillis(long maxWaitMillis) {
			this.maxWaitMillis = maxWaitMillis;
		}
		public long getMinEvictableIdleTimeMillis() {
			return minEvictableIdleTimeMillis;
		}
		public void setMinEvictableIdleTimeMillis(long minEvictableIdleTimeMillis) {
			this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
		}
		public long getSoftMinEvictableIdleTimeMillis() {
			return softMinEvictableIdleTimeMillis;
		}
		public void setSoftMinEvictableIdleTimeMillis(long softMinEvictableIdleTimeMillis) {
			this.softMinEvictableIdleTimeMillis = softMinEvictableIdleTimeMillis;
		}
		public int getNumTestsPerEvictionRun() {
			return numTestsPerEvictionRun;
		}
		public void setNumTestsPerEvictionRun(int numTestsPerEvictionRun) {
			this.numTestsPerEvictionRun = numTestsPerEvictionRun;
		}
		public boolean isTestOnCreate() {
			return testOnCreate;
		}
		public void setTestOnCreate(boolean testOnCreate) {
			this.testOnCreate = testOnCreate;
		}
		public boolean isTestOnBorrow() {
			return testOnBorrow;
		}
		public void setTestOnBorrow(boolean testOnBorrow) {
			this.testOnBorrow = testOnBorrow;
		}
		public boolean isTestOnReturn() {
			return testOnReturn;
		}
		public void setTestOnReturn(boolean testOnReturn) {
			this.testOnReturn = testOnReturn;
		}
		public boolean isTestWhileIdle() {
			return testWhileIdle;
		}
		public void setTestWhileIdle(boolean testWhileIdle) {
			this.testWhileIdle = testWhileIdle;
		}
		public long getTimeBetweenEvictionRunsMillis() {
			return timeBetweenEvictionRunsMillis;
		}
		public void setTimeBetweenEvictionRunsMillis(long timeBetweenEvictionRunsMillis) {
			this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
		}
		public boolean isBlockWhenExhausted() {
			return blockWhenExhausted;
		}
		public void setBlockWhenExhausted(boolean blockWhenExhausted) {
			this.blockWhenExhausted = blockWhenExhausted;
		}
		public boolean isJmxEnabled() {
			return jmxEnabled;
		}
		public void setJmxEnabled(boolean jmxEnabled) {
			this.jmxEnabled = jmxEnabled;
		}
		public int getMaxTotal() {
			return maxTotal;
		}
		public void setMaxTotal(int maxTotal) {
			this.maxTotal = maxTotal;
		}
		public int getMaxIdle() {
			return maxIdle;
		}
		public void setMaxIdle(int maxIdle) {
			this.maxIdle = maxIdle;
		}
		public int getMinIdle() {
			return minIdle;
		}
		public void setMinIdle(int minIdle) {
			this.minIdle = minIdle;
		}
	}
	
	public static class HostAndPort{
		private String host;
		private int port;
		public String getHost() {
			return host;
		}
		public void setHost(String host) {
			this.host = host;
		}
		public int getPort() {
			return port;
		}
		public void setPort(int port) {
			this.port = port;
		}
	}

	@Bean
	public JedisConnectionFactory createJedisConnectionFactory(RedisConfiguration config){
		return new JedisConnectionFactory(config);
	}

	@Bean
	public RedisHelper createRedisHelper(){
		RedisHelper redisHelper = new RedisHelper();
		componentConfig(redisHelper, this);
		return redisHelper;
	}

	private void componentConfig(RedisHelper redisHelper, RedisConfiguration config){
		RedisDistributeLock.setRedisHelper(redisHelper);
		AskTodoInCluster.init(redisHelper, String.valueOf(config.getClusterId()));
	}
}
