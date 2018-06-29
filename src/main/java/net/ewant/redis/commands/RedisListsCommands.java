package net.ewant.redis.commands;

import java.util.List;

public interface RedisListsCommands {

	Long lpush(String key, String... values) throws Exception;

	Long llen(String key) throws Exception;

	List<String> lrange(String key, long start, long end) throws Exception;

	String ltrim(String key, long start, long end) throws Exception;

	String lindex(String key, long index) throws Exception;

	String lset(String key, long index, String value) throws Exception;

	Long lrem(String key, long count, String value) throws Exception;

	String lpop(String key) throws Exception;

	Long lpushx(String key, String... values) throws Exception;
	
	List<String> blpop(int timeout, String key) throws Exception;
	
	List<String> brpop(int timeout, String key) throws Exception;
	
	String brpoplpush(String source, String destination, int timeout) throws Exception;
	
	String rpoplpush(String srckey, String dstkey) throws Exception;
	
	String rpop(String key) throws Exception;

	Long rpushx(String key, String... values) throws Exception;
	
	Long rpush(String key, String... values) throws Exception;
	
	Long linsertBefore(String key, String pivot, String value) throws Exception;
	
	Long linsertAfter(String key, String pivot, String value) throws Exception;
}
