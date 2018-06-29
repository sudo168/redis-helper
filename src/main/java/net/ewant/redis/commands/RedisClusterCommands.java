package net.ewant.redis.commands;

import java.util.List;

public interface RedisClusterCommands {

	/**
	 * @param slots
	 * @return 如果命令执行成功返回OK，否则返回错误信息
	 */
	boolean clusterAddSlots(final int... slots);
	
	/**
	 * @param slots
	 * @return 如果命令成功执行返回OK，否则返回一个错误
	 */
	boolean clusterDelSlots(final int... slots);
	
	String clusterInfo();
	
	/**
	 * @param slot
	 * @param count
	 * @return 返回count个key的列表
	 */
	List<String> clusterGetKeysInSlot(final int slot, final int count);
	
	/**
	 * Bind the hash slot to a different node.
	 * @param slot
	 * @param nodeId
	 * @return OK if the command was successful. Otherwise an error is returned.
	 */
	boolean clusterSetSlotNode(final int slot, final String nodeId);
	
	/**
	 * Set a hash slot in migrating state.
	 * @param slot
	 * @param nodeId
	 * @return OK if the command was successful. Otherwise an error is returned.
	 */
	boolean clusterSetSlotMigrating(final int slot, final String nodeId);
	
	/**
	 * Set a hash slot in importing state.
	 * @param slot
	 * @param nodeId
	 * @return OK if the command was successful. Otherwise an error is returned.
	 */
	boolean clusterSetSlotImporting(final int slot, final String nodeId);
	
	/**
	 * Clear any importing / migrating state from hash slot.
	 * @param slot
	 * @return OK if the command was successful. Otherwise an error is returned.
	 */
	boolean clusterSetSlotStable(final int slot);
	
	/**
	 * @param nodeId
	 * @return OK if the command was executed successfully, otherwise an error is returned.
	 */
	String clusterForget(final String nodeId);
	
	String clusterFlushSlots();
	
	/**
	 * 获取指定key的哈希槽
	 * @param key
	 * @return 哈希槽的值。
	 */
	Long clusterKeySlot(final String key);
	
	/**
	 * @param slot
	 * @return 返回连接节点负责的指定hash slot的key的数量, 如果hash slot不合法则返回错误
	 */
	Long clusterCountKeysInSlot(final int slot);
	
	/**
	 * Forces a node to save the nodes.conf configuration on disk.
	 * @return OK or an error if the operation fails.
	 */
	String clusterSaveConfig();
	
	/**
	 * @param nodeId
	 * @return OK if the command was executed successfully, otherwise an error is returned.
	 */
	String clusterReplicate(final String nodeId);
	
	List<String> clusterSlaves(final String nodeId);
	
	/**
	 * @return OK if the command was accepted and a manual failover is going to be attempted. 
	 * An error if the operation cannot be executed, 
	 * for example if we are talking with a node which is already a master.
	 */
	boolean clusterFailover();
	
	List<Object> clusterSlots();
	
}
