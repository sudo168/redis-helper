package net.ewant.redis.commands;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface RedisHashesCommands {

	Long hset(String key, String field, String value) throws Exception;

	String hget(String key, String field) throws Exception;

	Long hsetnx(String key, String field, String value) throws Exception;

	String hmset(String key, Map<String, String> hash) throws Exception;

	List<String> hmget(String key, String... fields) throws Exception;

	Long hincrBy(String key, String field, long value) throws Exception;

	Double hincrByFloat(final String key, final String field, final double value) throws Exception;

	Boolean hexists(String key, String field) throws Exception;

	Long hdel(String key, String... fields) throws Exception;

	Long hlen(String key) throws Exception;

	Set<String> hkeys(String key) throws Exception;

	List<String> hvals(String key) throws Exception;

	Map<String, String> hgetAll(String key) throws Exception;
	
}
