package net.ewant.redis.factory;

import net.ewant.redis.RedisConfiguration;
import net.ewant.redis.connect.RedisClusterConnection;
import net.ewant.redis.connect.RedisConnection;
import net.ewant.redis.connect.RedisSentinelConnection;
import net.ewant.redis.connect.RedisSingleConnection;

public interface RedisConnectionFactory {
	
	RedisConnection getConnection() throws Exception;
	
	RedisConnection wrapConnection(RedisConnection connect) throws Exception;

	RedisClusterConnection getClusterConnection() throws Exception;

	RedisSentinelConnection getSentinelConnection() throws Exception;

	RedisSingleConnection getSingleConnection() throws Exception;

	RedisConfiguration getRedisConfiguration() throws Exception;
}
