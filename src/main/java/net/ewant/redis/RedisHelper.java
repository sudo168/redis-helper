package net.ewant.redis;

import net.ewant.redis.commands.RedisCommands;
import net.ewant.redis.connect.RedisConnection;
import net.ewant.redis.factory.RedisConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class RedisHelper {
	
	private Logger log = LoggerFactory.getLogger(RedisHelper.class);

	@Autowired
	private RedisConnectionFactory factory;
	
	public RedisCommands cmd(int dbindex){
		if(factory == null){
			throw new RuntimeException("before use redis plugin, please set framework:[enableRedis=true] in your application configuration file...");
		}
		RedisConnection connection = null;
		try {
			connection = factory.getConnection();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		if(connection != null){
			if(dbindex > -1){
				connection.setSelectedDB(dbindex);
			}
			return connection;
		}
		return null;
	}
	
	/**
	 * 使用配置文件中的默认配置dbIndex，避免频繁选库操作
	 * @return
	 */
	public RedisCommands cmd(){
		return cmd(-1);
	}

}
