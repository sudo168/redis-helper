package net.ewant.redis.factory;

import net.ewant.redis.RedisConfiguration;
import net.ewant.redis.RedisConfiguration.HostAndPort;
import net.ewant.redis.RedisConfiguration.*;
import net.ewant.redis.connect.*;
import net.ewant.rolling.redis.connect.*;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.*;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class JedisConnectionFactory extends AbstractRedisConnectionFactory {
	
	private JedisPoolConfig poolConfig;

	@Autowired
	public JedisConnectionFactory(RedisConfiguration config) {
		super(config);
	}
	
	@Override
	public RedisConnection getConnection() throws Exception {
		RedisConnection connect = getConnection(0);
		if(connect != null){
			connect.setSelectedDB(config.getDbIndex());
		}
		
		return wrapConnection(connect);
	}

	@Override
	public RedisConnection wrapConnection(RedisConnection connect) throws Exception {
		if(connect != null){
			return createConnectionProxy(connect);
		}
		return null;
	}

	private RedisConnection createConnectionProxy(RedisConnection connect) {
		return ProxyFactory.create(connect, RedisConnection.class);
	}

	@Override
	public RedisClusterConnection getClusterConnection() throws Exception {
		//JedisCluster 不要直接调用close，因为相当于调用连接池close。
		return new DefaultRedisClusterConnection(this, (JedisCluster) pool);
	}

	@Override
	public RedisSentinelConnection getSentinelConnection() throws Exception {
		ShardedJedis shardedJedis = ((ShardedJedisSentinelPool)pool).getResource();
		if(shardedJedis != null){
			return new DefaultRedisSentinelConnection(this, shardedJedis);
		}
		return null;
	}

	@Override
	public RedisSingleConnection getSingleConnection() throws Exception {
		Jedis jedis = ((JedisPool)pool).getResource();
		if(jedis != null){
			return new DefaultRedisSingleConnection(this, jedis);
		}
		return null;
	}

	@Override
	protected Object initPool() {
		
		try {
			
			poolConfig = new JedisPoolConfig();
			
			initPoolConfig();
			
			if(isUsePool() && config.getPool() == null) throw new IllegalArgumentException("RedisPoolConfig is required !");
			
			if(redisMode == null || redisMode == RedisMode.SINGLE){
				HostAndPort single = config.getSingle();
				if(single == null || single.getHost() == null || single.getPort() < 1) throw new IllegalArgumentException("host port config is required in SINGLE mode!");
				return new JedisPool(poolConfig, single.getHost(), single.getPort(), config.getTimeout(), config.getPassword(), this.getDbIndex(), this.getClientName(),false,null,null,null);
			} else if(redisMode == RedisMode.SENTINEL) {
				List<RedisSentinelConfiguration> sentinel = config.getSentinel();
				if(sentinel == null || sentinel.isEmpty()) throw new IllegalArgumentException("RedisSentinelConfiguration is required in SENTINEL mode!");
				List<String> masters = new ArrayList<>();
				Set<String> sentinels = new LinkedHashSet<>();
				for (RedisSentinelConfiguration sentinelConfiguration : sentinel) {
					masters.add(sentinelConfiguration.getMaster());
					List<RedisConfiguration.HostAndPort> sentinelSet = sentinelConfiguration.getNodes();
					for (HostAndPort hostAndPost : sentinelSet) {
						sentinels.add(hostAndPost.getHost() + ":" + hostAndPost.getPort());
					}
				}
				return new ShardedJedisSentinelPool(masters, sentinels, poolConfig, config.getTimeout(), config.getPassword(), this.getDbIndex());
			} else if(redisMode == RedisMode.CLUSTER) {
				RedisClusterConfiguration cluster = config.getCluster();
				List<HostAndPort> clusterNodes = cluster.getNodes();
				if(cluster == null || clusterNodes == null || clusterNodes.isEmpty() || clusterNodes.size() < 3) throw new IllegalArgumentException("RedisClusterConfiguration is required and has at least three nodes in CLUSTER mode!");
				Set<redis.clients.jedis.HostAndPort> jedisClusterNodes = new LinkedHashSet<>();
				for (HostAndPort hostAndPort : clusterNodes) {
					jedisClusterNodes.add(new redis.clients.jedis.HostAndPort(hostAndPort.getHost(), hostAndPort.getPort()));
				}
				return new JedisCluster(jedisClusterNodes, config.getTimeout(), cluster.getMaxRetry(), poolConfig);
			}
		} catch (Exception e) {
			throw new RuntimeException("initPool error!", e);
		}
		throw new IllegalArgumentException("initPool failed! unknown connect mode : " + redisMode);
	}

	private void initPoolConfig() {
		RedisPoolConfig redisPoolConfig = config.getPool();
		poolConfig.setBlockWhenExhausted(redisPoolConfig.isBlockWhenExhausted());
		poolConfig.setJmxEnabled(redisPoolConfig.isJmxEnabled());
		poolConfig.setLifo(redisPoolConfig.isLifo());
		poolConfig.setMaxIdle(redisPoolConfig.getMaxIdle());
		poolConfig.setMaxTotal(redisPoolConfig.getMaxTotal());
		poolConfig.setMaxWaitMillis(redisPoolConfig.getMaxWaitMillis());
		poolConfig.setMinEvictableIdleTimeMillis(redisPoolConfig.getMinEvictableIdleTimeMillis());
		poolConfig.setMinIdle(redisPoolConfig.getMinIdle());
		poolConfig.setNumTestsPerEvictionRun(redisPoolConfig.getNumTestsPerEvictionRun());
		poolConfig.setSoftMinEvictableIdleTimeMillis(redisPoolConfig.getSoftMinEvictableIdleTimeMillis());
		poolConfig.setTestOnBorrow(redisPoolConfig.isTestOnBorrow());
		poolConfig.setTestOnCreate(redisPoolConfig.isTestOnCreate());
		poolConfig.setTestOnReturn(redisPoolConfig.isTestOnReturn());
		poolConfig.setTestWhileIdle(redisPoolConfig.isTestWhileIdle());
		poolConfig.setTimeBetweenEvictionRunsMillis(redisPoolConfig.getTimeBetweenEvictionRunsMillis());
	}

}
