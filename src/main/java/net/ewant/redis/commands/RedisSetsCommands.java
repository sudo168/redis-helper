package net.ewant.redis.commands;

import java.util.List;
import java.util.Set;

public interface RedisSetsCommands {

	Long sadd(String key, String... members) throws Exception;

	Set<String> smembers(String key) throws Exception;

	Long srem(String key, String... members) throws Exception;

	String spop(String key) throws Exception;

	Set<String> spop(String key, long count) throws Exception;

	Long scard(String key) throws Exception;

	Boolean sismember(String key, String member) throws Exception;

	String srandmember(String key) throws Exception;

	List<String> srandmember(String key, int count) throws Exception;
	
	Set<String> sdiff(String... keys) throws Exception;

	Long sdiffstore(String dstkey, String... keys) throws Exception;

	Set<String> sinter(String... keys) throws Exception;

	Long sinterstore(String dstkey, String... keys) throws Exception;

	Long smove(String srckey, String dstkey, String member) throws Exception;
	
	Set<String> sunion(String... keys) throws Exception;

	Long sunionstore(String dstkey, String... keys) throws Exception;
}
