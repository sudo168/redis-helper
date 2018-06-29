package net.ewant.redis.commands;

public interface RedisConnectCommands {
	
	String ping() throws Exception;

    String quit() throws Exception;

    String select(final int index) throws Exception;
	
	String auth(final String password) throws Exception;

	String echo(final String string) throws Exception;
	
	String flushDB() throws Exception;

    Long dbSize() throws Exception;

    String flushAll() throws Exception;
  
    Long getDB() throws Exception;
	
}
