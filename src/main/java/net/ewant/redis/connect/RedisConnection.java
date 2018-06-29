package net.ewant.redis.connect;

import net.ewant.redis.CommandsExecutor;
import net.ewant.redis.commands.RedisCommands;
import net.ewant.redis.factory.RedisConnectionFactory;

import java.util.List;

public interface RedisConnection extends RedisCommands{
	
	CommandsExecutor getCommandsExecutor();
	
	/**
	 * Closes (or quits) the connection.
	 */
	void close();

	/**
	 * Indicates whether the underlying connection is closed or not.
	 * 
	 * @return true if the connection is closed, false otherwise.
	 */
	boolean isClosed();

	/**
	 * Returns the native connection (the underlying library/driver object).
	 * 
	 * @return underlying, native object
	 */
	Object getNativeConnection();

	/**
	 * Indicates whether the connection is in "queue"(or "MULTI") mode or not. When queueing, all commands are postponed
	 * until EXEC or DISCARD commands are issued. Since in queueing no results are returned, the connection will return
	 * NULL on all operations that interact with the data.
	 * 
	 * @return true if the connection is in queue/MULTI mode, false otherwise
	 */
	boolean isQueueing();

	/**
	 * Indicates whether the connection is currently pipelined or not.
	 * 
	 * @return true if the connection is pipelined, false otherwise
	 * @see #openPipeline()
	 * @see #isQueueing()
	 */
	boolean isPipelined();

	/**
	 * Activates the pipeline mode for this connection. When pipelined, all commands return null (the reply is read at the
	 * end through {@link #closePipeline()}. Calling this method when the connection is already pipelined has no effect.
	 * Pipelining is used for issuing commands without requesting the response right away but rather at the end of the
	 * batch. While somewhat similar to MULTI, pipelining does not guarantee atomicity - it only tries to improve
	 * performance when issuing a lot of commands (such as in batching scenarios).
	 * <p>
	 * Note:
	 * </p>
	 * Consider doing some performance testing before using this feature since in many cases the performance benefits are
	 * minimal yet the impact on usage are not.
	 * 
	 * @see #multi()
	 */
	void openPipeline();

	/**
	 * Executes the commands in the pipeline and returns their result. If the connection is not pipelined, an empty
	 * collection is returned.
	 * 
	 * @return the result of the executed commands.
	 */
	List<Object> closePipeline();
	

	void setSelectedDB(int selectedDB);

	RedisConnectionFactory getRedisConnectionFactory();
}
