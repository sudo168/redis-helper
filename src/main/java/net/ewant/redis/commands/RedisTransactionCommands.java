package net.ewant.redis.commands;

import java.util.List;

public interface RedisTransactionCommands {

	void multi();
	
	/**
	 * @return 每个元素与原子事务中的指令一一对应 当使用WATCH时，如果被终止，EXEC 则返回一个空的应答集合
	 */
	List<Object> exec();
	
	/**
	 * @return 总是 OK
	 */
	boolean discard();
	
	/**
	 * @return 总是 OK
	 */
	boolean watch(final String... keys);
	
	/**
	 * @return 总是 OK
	 */
	boolean unwatch();
}
