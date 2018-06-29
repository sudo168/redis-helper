package net.ewant.redis.commands;

import java.util.Set;

public interface RedisCommands extends RedisConnectCommands, RedisKeysCommands, RedisStringCommands, RedisHashesCommands, 
	RedisListsCommands, RedisSetsCommands, RedisZSetsCommands{

	Long hdelByRename(String routeKey, String dataKey, String... fields) throws Exception;
	
	String hgetByRename(String routeKey, String dataKey, String field) throws Exception;
	
	Set<String> hkeysByRename(String routeKey, String dataKey) throws Exception;
}
