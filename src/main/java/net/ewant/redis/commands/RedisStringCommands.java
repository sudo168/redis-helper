package net.ewant.redis.commands;

import java.util.List;

public interface RedisStringCommands {

	Long append(final String key, final String value) throws Exception;
	
	/**
	 * 统计字符串被设置为1的bit数.
	 * @param key
	 * @return 被设置为 1 的位的数量,key不存在返回0
	 */
	Long bitcount(final String key) throws Exception;

	/**
	 * 统计指定范围内，字符串被设置为1的bit数.
	 * @param key
	 * @param start
	 * @param end
	 * @return 被设置为 1 的位的数量,key不存在返回0
	 */
	Long bitcount(final String key, final long start, final long end) throws Exception;

	/**
	 * @param destKey
	 * @param srcKeys
	 * @return
	 */
	Long bitopAnd(final String destKey, final String... srcKeys) throws Exception;
	Long bitopOr(final String destKey, final String... srcKeys) throws Exception;
	Long bitopXor(final String destKey, final String... srcKeys) throws Exception;
	Long bitopNot(final String destKey, final String... srcKeys) throws Exception;

	Long bitpos(final String key, final boolean value) throws Exception;

	Long bitpos(final String key, final boolean value, final long start) throws Exception;

	Long bitpos(final String key, final boolean value, final long start, final long end) throws Exception;

	/**
	 * 对key对应的数字做减1操作.如果key不存在，那么在操作之前，这个key对应的值会被置为0
	 * @param key
	 * @return 减小之后的value . ERROR when value is not an integer or out of range
	 * 最大支持在64位有符号的整型数字
	 */
	Long decr(final String key) throws Exception;

	/**
	 * 对key对应的数字做减decrement操作.如果key不存在，那么在操作之前，这个key对应的值会被置为0
	 * @param key
	 * @param decrement
	 * @return 减小之后的value . ERROR when value is not an integer or out of range
	 * 最大支持在64位有符号的整型数字
	 */
	Long decrBy(final String key, final long decrement) throws Exception;

	/**
	 * 获取key对应的值
	 * @param key
	 * @return 返回key的value。如果key不存在，返回特殊值nil。如果key的value不是string，就返回错误
	 */
	String get(final String key) throws Exception;

	/**
	 * 返回key对应的string在offset处的bit值 当offset超出了字符串长度的时候，
	 * 这个字符串就被假定为由0比特填充的连续空间。当key不存在的时候，它就认为是一个空字符串，所以offset总是超出范围，
	 * 然后value也被认为是由0比特填充的连续空间。
	 * @param key
	 * @param offset
	 * @return 在offset处的bit值 (0/1 即false/true)
	 */
	Boolean getbit(final String key, final long offset) throws Exception;

	/**
	 * 这个命令是被改成GETRANGE的，在小于2.0的Redis版本中叫SUBSTR。
	 *  返回key对应的字符串value的子串，这个子串是由start和end位移决定的（两者都在string内）。
	 *  可以用负的位移来表示从string尾部开始数的下标。所以-1就是最后一个字符，-2就是倒数第二个，以此类推。
	 *  这个函数处理超出范围的请求时，都把结果限制在string内(即返回值本身)
	 * @param key
	 * @param startOffset
	 * @param endOffset
	 * @return
	 */
	String getRange(final String key, final long startOffset, final long endOffset) throws Exception;

	/**
	 * 自动将key对应到value并且返回原来key对应的value。如果key存在但是对应的value不是字符串，就返回错误。
	 * @param key
	 * @param value
	 * @return 返回之前的旧值，如果之前Key不存在将返回nil。
	 */
	String getSet(final String key, final String value) throws Exception;

	/**
	 * 对存储在指定key的数值执行原子的加1操作。
	 * 如果指定的key不存在，那么在执行incr操作之前，会先将它的值设定为0。
	 * 如果指定的key中存储的值不是字符串类型（fix：）或者存储的字符串类型不能表示为一个整数，
	 * 那么执行这个命令时服务器会返回一个错误(eq:(error) ERR value is not an integer or out of range)。
	 * 这个操作仅限于64位的有符号整型数据。
	 * @param key
	 * @return 执行递增操作后key对应的值
	 */
	Long incr(final String key) throws Exception;

	Long incrBy(final String key, final long increment) throws Exception;

	Double incrByFloat(final String key, final double increment) throws Exception;

	List<String> mget(final String... keys) throws Exception;

	/**
	 * @param keysvalues
	 * @return 总是OK，因为MSET不会失败。
	 */
	String mset(final String... keysvalues) throws Exception;

	/**
	 * @param keysvalues
	 * @return Integer reply, specifically: 1 if the all the keys were set 0 if no key was set (at
     *         least one key already existed)
	 */
	Long msetnx(final String... keysvalues) throws Exception;

	String set(final String key, String value) throws Exception;

	/**
	 *
	 * @param key
	 * @param value
	 * @param nxxx NX|XX, NX -- Only set the key if it does not already exist. XX -- Only set the key
	 *          if it already exist.
	 * @return
	 */
	String set(final String key, final String value, final String nxxx) throws Exception;

	/**
	 *
	 * @param key
	 * @param value
	 * @return Integer reply, specifically: 1 if the key was set 0 if the key was not set
	 * @throws Exception
	 */
	Long setNx(final String key, final String value) throws Exception;

	String setEx(final String key, final int seconds, final String value) throws Exception;

	/**
	   * Set the string value as value of the key. The string can't be longer than 1073741824 bytes (1
	   * GB).
	   * @param key
	   * @param value
	   * @param nxxx NX|XX, NX -- Only set the key if it does not already exist. XX -- Only set the key
	   *          if it already exist.
	   * @param expx EX|PX, expire time units: EX = seconds; PX = milliseconds
	   * @param time expire time in the units of <code>expx</code>
	   * @return Status code reply
	   */
	String set(final String key, final String value, final String nxxx, final String expx,
               final long time) throws Exception;
	
	/**
	 * Sets or clears the bit at offset in the string value stored at key
	 * @param key
	 * @param offset
	 * @param value
	 * @return
	 */
	Boolean setbit(String key, long offset, boolean value) throws Exception;
	
	/**
	 * @param key
	 * @param offset
	 * @param value
	 * @return 该命令修改后的字符串长度
	 */
	Long setRange(String key, long offset, String value) throws Exception;
	
	/**
	 * 返回key的string类型value的长度。如果key对应的非string类型，就返回错误。
	 * @param key
	 * @return key对应的字符串value的长度，或者0（key不存在）
	 */
	Long strlen(final String key) throws Exception;
}
