package net.ewant.redis.connect;

import net.ewant.redis.CommandsExecutor;
import net.ewant.redis.factory.RedisConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisCommands;

import java.util.List;

public abstract class AbstractRedisConnection implements RedisConnection {

	final Logger logger = LoggerFactory.getLogger(getClass());
	
	private int selectedDB;
	
	private CommandsExecutor executor;
	
	private JedisCommands sources;

	private RedisConnectionFactory factory;
	
	public AbstractRedisConnection(RedisConnectionFactory factory, JedisCommands sources) {
		this.factory = factory;
		this.sources = sources;
		this.executor = new CommandsExecutor(sources);
	}

	@Override
	public CommandsExecutor getCommandsExecutor() {
		return this.executor;
	}

	@Override
	public Object getNativeConnection() {
		return this.sources;
	}

	@Override
	public boolean isQueueing() {
		return false;
	}

	@Override
	public boolean isPipelined() {
		return false;
	}

	@Override
	public void openPipeline() {
		
	}

	@Override
	public List<Object> closePipeline() {
		return null;
	}

	public int getSelectedDB() {
		return selectedDB;
	}

	@Override
	public void setSelectedDB(int selectedDB) {
		this.selectedDB = selectedDB;
	}

	public boolean resetDB() throws Exception{
		Long db = null;
		try {
			db = getDB();
		} catch (Exception e) {
			if(sources instanceof JedisCluster){
				return false;
			}else{
				throw e;
			}
		}
		return db.intValue() != this.selectedDB;
	}

	@Override
	public RedisConnectionFactory getRedisConnectionFactory() {
		return factory;
	}
}
