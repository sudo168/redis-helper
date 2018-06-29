package net.ewant.redis.commands;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface RedisKeysCommands {

	/**
	 * Set the value and expiration in milliseconds of a key
	 * @param key
	 * @param milliseconds 过期时间ms
	 * @param value
	 * @return 总是OK
	 */
	String psetex(final String key, final long milliseconds, final String value) throws Exception;
	
	/**
	 * @param key
	 * @return 返回被删除key的数量
	 */
	Long del(final String... key) throws Exception;
	
	/**
	 * 序列化给定 key ，并返回被序列化的值，使用 RESTORE 命令可以将这个值反序列化为 Redis 键
	 * @param key
	 * @return 如果 key 不存在，那么返回 nil。否则，返回序列化之后的值。
	 */
	byte[] dump(final String key) throws Exception;
	
	Boolean exists(final String key) throws Exception;
	
	/**
	 * 为指定key设置过期时间
	 * @param key
	 * @param seconds 秒
	 * @return 1 如果成功设置过期时间。0 如果key不存在或者不能设置过期时间。
	 */
	Long expire(final String key, final int seconds) throws Exception;
	
	/**
	 * 指定过期时间节点
	 * @param key
	 * @param unixTime Number of seconds elapsed since 1 Gen 1970
	 * @return 1 如果成功设置过期时间。0 如果key不存在或者不能设置过期时间。
	 */
	Long expireAt(final String key, final long unixTime) throws Exception;
	
	Set<String> keys(final String pattern) throws Exception;
	
	/**
	 * 将 key 原子性地从当前实例传送到目标实例的指定数据库上，一旦传送成功， 
	 * key 保证会出现在目标实例上，而当前实例上的 key 会被删除。
	 * 这个命令是一个原子操作，它在执行的时候会阻塞进行迁移的两个实例，
	 * 直到以下任意结果发生：迁移成功，迁移失败，等到超时。
	 * @param host
	 * @param port
	 * @param key
	 * @param destinationDb
	 * @param timeout 参数以毫秒为格式
	 * @return 迁移成功时返回 OK ，否则返回相应的错误。
	 */
	boolean migrate(final String host, final int port, final String key,
                    final int destinationDb, final int timeout) throws Exception;

	/**
	 * 将当前数据库的 key 移动到给定的数据库 db 当中。
	 * 如果当前数据库(源数据库)和给定数据库(目标数据库)有相同名字的给定 key ，或者 key 不存在于当前数据库，那么 MOVE 没有任何效果
	 * @param key
	 * @param dbIndex
	 * @return 移动成功返回 1, 失败则返回 0
	 */
	Long move(final String key, final int dbIndex) throws Exception;

	/**
	 * 移除给定key的生存时间
	 * @param key
	 * @return 当生存时间移除成功时，返回 1 . 如果 key 不存在或 key 没有设置生存时间，返回 0 .
	 */
	Long persist(final String key) throws Exception;

	/**
	 * 为指定key设置过期时间
	 * @param key
	 * @param seconds 秒
	 * @return 1 如果成功设置过期时间。0 如果key不存在或者不能设置过期时间。
	 */
	Long pexpire(final String key, final long seconds) throws Exception;

	/**
	 * 指定过期时间节点
	 * @param key
	 * @param millisecondsTimestamp Number of milliseconds elapsed since 1 Gen 1970
	 * @return 1 如果成功设置过期时间。0 如果key不存在或者不能设置过期时间。
	 */
	Long pexpireAt(final String key, final long millisecondsTimestamp) throws Exception;

	/**
	 * 这个命令类似于TTL命令，但它以毫秒为单位返回 key 的剩余生存时间，而不是像TTL命令那样，以秒为单位。
	 * @param key
	 * @return 如果key不存在返回-2  如果key存在但是没有过期时间返回-1 否则返回具体毫秒数
	 */
	Long pttl(final String key) throws Exception;
	/**
	 * 将key重命名为newkey，如果key与newkey相同，将返回一个错误。如果newkey已经存在，则值将被覆盖。
	 * @param key
	 * @param newKey
	 * @return 成功时返回 OK ，否则返回相应的错误。
	 */
	boolean rename(final String key, final String newKey) throws Exception;

	/**
	 * 当且仅当 newkey 不存在时，将 key 改名为 newkey 。当 key 不存在时，返回一个错误。
	 * @param key
	 * @param newKey
	 * @return 修改成功时，返回 1 。如果 newkey 已经存在，返回 0
	 */
	boolean renameNx(final String key, final String newKey) throws Exception;

	/**
	 * 反序列化给定的序列化值，并将它和给定的 key 关联。参数 ttl 以毫秒为单位为 key 设置生存时间；如果 ttl 为 0 ，那么不设置生存时间。
	 * @param key
	 * @param ttl 毫秒
	 * @param serializedValue
	 * @return
	 */
	String restore(final String key, final int ttl, final byte[] serializedValue) throws Exception;

	/**
	 * SCAN命令是一个基于游标的迭代器。这意味着命令每次被调用都需要使用上一次这个调用返回的游标作为该次调用的游标参数，以此来延续之前的迭代过程
	 * 当SCAN命令的游标参数被设置为 0 时， 服务器将开始一次新的迭代， 而当服务器向用户返回值为 0 的游标时， 表示迭代已结束。
	 * @param cursor
	 * @return
	 */
	Map<String,List<String>> scan(final String cursor) throws Exception;

	Map<String,List<String>> scan(final String cursor, final String matchPattern) throws Exception;

	Map<String,List<String>> scan(final String cursor, final String matchPattern, final int count) throws Exception;
	
	/**
	 * 获取指定key的过期时间
	 * @param key
	 * @return 如果key不存在或者已过期，返回 -2, 如果key没有设置过期时间（永久有效），返回 -1 否则返回具体的秒数
	 */
	Long ttl(final String key) throws Exception;
}
