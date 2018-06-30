package net.ewant.redis;

import net.ewant.redis.commands.RedisCommands;
import redis.clients.jedis.BinaryClient.LIST_POSITION;
import redis.clients.jedis.*;
import redis.clients.jedis.params.sortedset.ZAddParams;
import redis.clients.jedis.params.sortedset.ZIncrByParams;

import java.util.*;

/**
 * @author huangzh  <没有使用>
 * @date 2017年8月31日
 */
public class CommandsExecutor implements RedisCommands {
	
	JedisCommands sources;
	
	public CommandsExecutor(JedisCommands sources){
		this.sources = sources;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public String ping() throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).ping();
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).ping();
		}else{
			throw new RedisOperationException("not support command [ping] in " + sources.getClass().getName());
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public String quit() throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).quit();
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).quit();
		}else{
			throw new RedisOperationException("not support command [quit] in " + sources.getClass().getName());
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public String select(int index) throws RedisOperationException {
		if(index < 0){
			throw new IllegalArgumentException("index must be an int value that grant than -1");
		}
		if(sources instanceof Jedis){
			return ((Jedis)sources).select(index);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).select(index);
		}else{
			throw new RedisOperationException("not support command [select] in " + sources.getClass().getName());
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public String auth(String password) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).auth(password);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).auth(password);
		}else{
			throw new RedisOperationException("not support command [auth] in " + sources.getClass().getName());
		}
	}

	@Override
	public String echo(String string) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).echo(string);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).echo(string);
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).echo(string);
		}else{
			throw new RedisOperationException("not support command [echo] in " + sources.getClass().getName());
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public String flushDB() throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).flushDB();
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).flushDB();
		}else{
			throw new RedisOperationException("not support command [flushDB] in " + sources.getClass().getName());
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public Long dbSize() throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).dbSize();
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).dbSize();
		}else{
			throw new RedisOperationException("not support command [dbSize] in " + sources.getClass().getName());
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public String flushAll() throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).flushAll();
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).flushAll();
		}else{
			throw new RedisOperationException("not support command [flushAll] in " + sources.getClass().getName());
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public Long getDB() throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).getDB();
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).getDB();
		}else{
			throw new RedisOperationException("not support command [getDB] in " + sources.getClass().getName());
		}
	}

	@Override
	public String psetex(String key, long milliseconds, String value) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).psetex(key, milliseconds, value);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).psetex(key, milliseconds, value);
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).psetex(key, milliseconds, value);
		}else{
			throw new RedisOperationException("not support command [psetex] in " + sources.getClass().getName());
		}
	}

	@Override
	public Long del(String... key) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).del(key);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).del(key);
		}else{
			throw new RedisOperationException("not support command [del] in " + sources.getClass().getName());
		}
	}

	@Override
	public byte[] dump(String key) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).dump(key);
		}else{
			throw new RedisOperationException("not support command [dump] in " + sources.getClass().getName());
		}
	}

	@Override
	public Boolean exists(String key) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).exists(key);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).exists(key);
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).exists(key);
		}else{
			throw new RedisOperationException("not support command [exists] in " + sources.getClass().getName());
		}
	}

	@Override
	public Long expire(String key, int seconds) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).expire(key,seconds);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).expire(key,seconds);
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).expire(key,seconds);
		}else{
			throw new RedisOperationException("not support command [expire] in " + sources.getClass().getName());
		}
	}

	@Override
	public Long expireAt(String key, long unixTime) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).expireAt(key,unixTime);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).expireAt(key,unixTime);
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).expireAt(key,unixTime);
		}else{
			throw new RedisOperationException("not support command [expireAt] in " + sources.getClass().getName());
		}
	}

	@Override
	public Set<String> keys(String pattern) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).keys(pattern);
		}else{
			throw new RedisOperationException("not support command [keys] in " + sources.getClass().getName());
		}
	}

	@Override
	public boolean migrate(String host, int port, String key, int destinationDb, int timeout) throws RedisOperationException {
		if(sources instanceof Jedis){
			return "OK".equals(((Jedis)sources).migrate(host, port, key, destinationDb, timeout));
		}else{
			throw new RedisOperationException("not support command [migrate] in " + sources.getClass().getName());
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public Long move(String key, int dbIndex) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).move(key,dbIndex);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).move(key,dbIndex);
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).move(key,dbIndex);
		}else{
			throw new RedisOperationException("not support command [move] in " + sources.getClass().getName());
		}
	}

	@Override
	public Long persist(String key) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).persist(key);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).persist(key);
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).persist(key);
		}else{
			throw new RedisOperationException("not support command [persist] in " + sources.getClass().getName());
		}
	}

	@Override
	public Long pexpire(String key, long seconds) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).pexpire(key,seconds);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).pexpire(key,seconds);
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).pexpire(key,seconds);
		}else{
			throw new RedisOperationException("not support command [pexpire] in " + sources.getClass().getName());
		}
	}

	@Override
	public Long pexpireAt(String key, long millisecondsTimestamp) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).pexpireAt(key,millisecondsTimestamp);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).pexpireAt(key,millisecondsTimestamp);
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).pexpireAt(key,millisecondsTimestamp);
		}else{
			throw new RedisOperationException("not support command [pexpireAt] in " + sources.getClass().getName());
		}
	}

	@Override
	public Long pttl(String key) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).pttl(key);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).pttl(key);
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).pttl(key);
		}else{
			throw new RedisOperationException("not support command [pttl] in " + sources.getClass().getName());
		}
	}

	@Override
	public boolean rename(String key, String newKey) throws RedisOperationException {
		if(sources instanceof Jedis){
			return "OK".equals(((Jedis)sources).rename(key,newKey));
		}else if(sources instanceof JedisCluster){
			return "OK".equals(((JedisCluster)sources).rename(key,newKey));
		}else{
			throw new RedisOperationException("not support command [rename] in " + sources.getClass().getName());
		}
	}

	@Override
	public boolean renameNx(String key, String newKey) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).renamenx(key,newKey) == 1;
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).renamenx(key,newKey) == 1;
		}else{
			throw new RedisOperationException("not support command [renamenx] in " + sources.getClass().getName());
		}
	}

	@Override
	public String restore(String key, int ttl, byte[] serializedValue) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).restore(key,ttl,serializedValue);
		}else{
			throw new RedisOperationException("not support command [restore] in " + sources.getClass().getName());
		}
	}

	@Override
	public Map<String, List<String>> scan(String cursor) throws RedisOperationException {
		if(sources instanceof Jedis){
			Map<String, List<String>> res = new HashMap<>(1,2);
			ScanResult<String> scan = ((Jedis)sources).scan(cursor);
			if(scan != null){
				res.put(scan.getStringCursor(), scan.getResult());
			}
			return res;
		}else{
			throw new RedisOperationException("not support command [scan] in " + sources.getClass().getName());
		}
	}

	@Override
	public Map<String, List<String>> scan(String cursor, String matchPattern) throws RedisOperationException {
		if(sources instanceof Jedis){
			Map<String, List<String>> res = new HashMap<>(1,2);
			ScanResult<String> scan = ((Jedis)sources).scan(cursor, new ScanParams().match(matchPattern));
			if(scan != null){
				res.put(scan.getStringCursor(), scan.getResult());
			}
			return res;
		}else{
			throw new RedisOperationException("not support command [scan] in " + sources.getClass().getName());
		}
	}

	@Override
	public Map<String, List<String>> scan(String cursor, String matchPattern, int count) throws RedisOperationException {
		if(sources instanceof Jedis){
			Map<String, List<String>> res = new HashMap<>(1,2);
			ScanResult<String> scan = ((Jedis)sources).scan(cursor, new ScanParams().match(matchPattern).count(count));
			if(scan != null){
				res.put(scan.getStringCursor(), scan.getResult());
			}
			return res;
		}else{
			throw new RedisOperationException("not support command [scan] in " + sources.getClass().getName());
		}
	}

	@Override
	public Long ttl(String key) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).ttl(key);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).ttl(key);
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).ttl(key);
		}else{
			throw new RedisOperationException("not support command [ttl] in " + sources.getClass().getName());
		}
	}

	@Override
	public Long append(String key, String value) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).append(key,value);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).append(key,value);
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).append(key,value);
		}else{
			throw new RedisOperationException("not support command [append] in " + sources.getClass().getName());
		}
	}

	@Override
	public Long bitcount(String key) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).bitcount(key);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).bitcount(key);
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).bitcount(key);
		}else{
			throw new RedisOperationException("not support command [bitcount] in " + sources.getClass().getName());
		}
	}

	@Override
	public Long bitcount(String key, long start, long end) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).bitcount(key,start,end);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).bitcount(key,start,end);
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).bitcount(key,start,end);
		}else{
			throw new RedisOperationException("not support command [bitcount] in " + sources.getClass().getName());
		}
	}

	@Override
	public Long bitopAnd(String destKey, String... srcKeys) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).bitop(BitOP.AND, destKey, srcKeys);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).bitop(BitOP.AND, destKey, srcKeys);
		}else{
			throw new RedisOperationException("not support command [bitop] in " + sources.getClass().getName());
		}
	}
	
	@Override
	public Long bitopOr(String destKey, String... srcKeys) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).bitop(BitOP.OR, destKey, srcKeys);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).bitop(BitOP.OR, destKey, srcKeys);
		}else{
			throw new RedisOperationException("not support command [bitop] in " + sources.getClass().getName());
		}
	}
	
	@Override
	public Long bitopXor(String destKey, String... srcKeys) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).bitop(BitOP.XOR, destKey, srcKeys);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).bitop(BitOP.XOR, destKey, srcKeys);
		}else{
			throw new RedisOperationException("not support command [bitop] in " + sources.getClass().getName());
		}
	}
	
	@Override
	public Long bitopNot(String destKey, String... srcKeys) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).bitop(BitOP.NOT, destKey, srcKeys);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).bitop(BitOP.NOT, destKey, srcKeys);
		}else{
			throw new RedisOperationException("not support command [bitop] in " + sources.getClass().getName());
		}
	}

	@Override
	public Long bitpos(String key, boolean value) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).bitpos(key,value);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).bitpos(key,value);
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).bitpos(key,value);
		}else{
			throw new RedisOperationException("not support command [bitpos] in " + sources.getClass().getName());
		}
	}

	@Override
	public Long bitpos(String key, boolean value, long start) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).bitpos(key,value,new BitPosParams(start));
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).bitpos(key,value,new BitPosParams(start));
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).bitpos(key,value,new BitPosParams(start));
		}else{
			throw new RedisOperationException("not support command [bitpos] in " + sources.getClass().getName());
		}
	}

	@Override
	public Long bitpos(String key, boolean value, long start, long end) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).bitpos(key,value,new BitPosParams(start,end));
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).bitpos(key,value,new BitPosParams(start));
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).bitpos(key,value,new BitPosParams(start));
		}else{
			throw new RedisOperationException("not support command [bitpos] in " + sources.getClass().getName());
		}
	}

	@Override
	public Long decr(String key) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).decr(key);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).decr(key);
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).decr(key);
		}else{
			throw new RedisOperationException("not support command [decr] in " + sources.getClass().getName());
		}
	}

	@Override
	public Long decrBy(String key, long decrement) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).decrBy(key,decrement);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).decrBy(key,decrement);
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).decrBy(key,decrement);
		}else{
			throw new RedisOperationException("not support command [decrBy] in " + sources.getClass().getName());
		}
	}

	@Override
	public String get(String key) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).get(key);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).get(key);
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).get(key);
		}else{
			throw new RedisOperationException("not support command [get] in " + sources.getClass().getName());
		}
	}

	@Override
	public Boolean getbit(String key, long offset) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).getbit(key,offset);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).getbit(key,offset);
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).getbit(key,offset);
		}else{
			throw new RedisOperationException("not support command [getbit] in " + sources.getClass().getName());
		}
	}

	@Override
	public String getRange(String key, long startOffset, long endOffset) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).getrange(key,startOffset,endOffset);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).getrange(key,startOffset,endOffset);
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).getrange(key,startOffset,endOffset);
		}else{
			throw new RedisOperationException("not support command [getrange] in " + sources.getClass().getName());
		}
	}

	@Override
	public String getSet(String key, String value) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).getSet(key,value);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).getSet(key,value);
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).getSet(key,value);
		}else{
			throw new RedisOperationException("not support command [getSet] in " + sources.getClass().getName());
		}
	}

	@Override
	public Long incr(String key) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).incr(key);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).incr(key);
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).incr(key);
		}else{
			throw new RedisOperationException("not support command [incr] in " + sources.getClass().getName());
		}
	}

	@Override
	public Long incrBy(String key, long increment) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).incrBy(key,increment);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).incrBy(key,increment);
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).incrBy(key,increment);
		}else{
			throw new RedisOperationException("not support command [incrBy] in " + sources.getClass().getName());
		}
	}

	@Override
	public Double incrByFloat(String key, double increment) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).incrByFloat(key,increment);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).incrByFloat(key,increment);
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).incrByFloat(key,increment);
		}else{
			throw new RedisOperationException("not support command [incrByFloat] in " + sources.getClass().getName());
		}
	}

	@Override
	public List<String> mget(String... keys) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).mget(keys);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).mget(keys);
		}else{
			throw new RedisOperationException("not support command [mget] in " + sources.getClass().getName());
		}
	}

	@Override
	public String mset(String... keysvalues) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).mset(keysvalues);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).mset(keysvalues);
		}else{
			throw new RedisOperationException("not support command [mset] in " + sources.getClass().getName());
		}
	}

	@Override
	public Long msetnx(String... keysvalues) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).msetnx(keysvalues);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).msetnx(keysvalues);
		}else{
			throw new RedisOperationException("not support command [msetnx] in " + sources.getClass().getName());
		}
	}

	@Override
	public String set(String key, String value) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).set(key,value);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).set(key,value);
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).set(key,value);
		}else{
			throw new RedisOperationException("not support command [set] in " + sources.getClass().getName());
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public String set(String key, String value, String nxxx) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).set(key,value,nxxx);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).set(key,value,nxxx);
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).set(key,value,nxxx);
		}else{
			throw new RedisOperationException("not support command [set] in " + sources.getClass().getName());
		}
	}

	@Override
	public Long setNx(String key, String value) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).setnx(key,value);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).setnx(key,value);
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).setnx(key,value);
		}else{
			throw new RedisOperationException("not support command [setnx] in " + sources.getClass().getName());
		}
	}

	@Override
	public String setEx(String key, int seconds, String value) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).setex(key, seconds, value);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).setex(key, seconds, value);
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).setex(key, seconds, value);
		}else{
			throw new RedisOperationException("not support command [setex] in " + sources.getClass().getName());
		}
	}

	@Override
	public String set(String key, String value, String nxxx, String expx, long time) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).set(key, value, nxxx, expx, time);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).set(key, value, nxxx, expx, time);
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).set(key, value, nxxx, expx, time);
		}else{
			throw new RedisOperationException("not support command [set] in " + sources.getClass().getName());
		}
	}

	@Override
	public Boolean setbit(String key, long offset, boolean value) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).setbit(key, offset, value);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).setbit(key, offset, value);
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).setbit(key, offset, value);
		}else{
			throw new RedisOperationException("not support command [setbit] in " + sources.getClass().getName());
		}
	}

	@Override
	public Long setRange(String key, long offset, String value) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).setrange(key, offset, value);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).setrange(key, offset, value);
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).setrange(key, offset, value);
		}else{
			throw new RedisOperationException("not support command [setrange] in " + sources.getClass().getName());
		}
	}

	@Override
	public Long strlen(String key) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).strlen(key);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).strlen(key);
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).strlen(key);
		}else{
			throw new RedisOperationException("not support command [strlen] in " + sources.getClass().getName());
		}
	}

	@Override
	public Long hset(String key, String field, String value) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).hset(key, field, value);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).hset(key, field, value);
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).hset(key, field, value);
		}else{
			throw new RedisOperationException("not support command [hset] in " + sources.getClass().getName());
		}
	}

	@Override
	public String hget(String key, String field) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).hget(key, field);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).hget(key, field);
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).hget(key, field);
		}else{
			throw new RedisOperationException("not support command [hget] in " + sources.getClass().getName());
		}
	}

	@Override
	public Long hsetnx(String key, String field, String value) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).hsetnx(key, field, value);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).hsetnx(key, field, value);
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).hsetnx(key, field, value);
		}else{
			throw new RedisOperationException("not support command [hsetnx] in " + sources.getClass().getName());
		}
	}

	@Override
	public String hmset(String key, Map<String, String> hash) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).hmset(key, hash);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).hmset(key, hash);
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).hmset(key, hash);
		}else{
			throw new RedisOperationException("not support command [hmset] in " + sources.getClass().getName());
		}
	}

	@Override
	public List<String> hmget(String key, String... fields) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).hmget(key, fields);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).hmget(key, fields);
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).hmget(key, fields);
		}else{
			throw new RedisOperationException("not support command [hmget] in " + sources.getClass().getName());
		}
	}

	@Override
	public Long hincrBy(String key, String field, long value) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).hincrBy(key, field, value);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).hincrBy(key, field, value);
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).hincrBy(key, field, value);
		}else{
			throw new RedisOperationException("not support command [hincrBy] in " + sources.getClass().getName());
		}
	}

	@Override
	public Double hincrByFloat(String key, String field, double value) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).hincrByFloat(key, field, value);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).hincrByFloat(key, field, value);
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).hincrByFloat(key, field, value);
		}else{
			throw new RedisOperationException("not support command [hincrByFloat] in " + sources.getClass().getName());
		}
	}

	@Override
	public Boolean hexists(String key, String field) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).hexists(key, field);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).hexists(key, field);
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).hexists(key, field);
		}else{
			throw new RedisOperationException("not support command [hexists] in " + sources.getClass().getName());
		}
	}

	@Override
	public Long hdel(String key, String... fields) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).hdel(key, fields);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).hdel(key, fields);
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).hdel(key, fields);
		}else{
			throw new RedisOperationException("not support command [hdel] in " + sources.getClass().getName());
		}
	}

	@Override
	public Long hlen(String key) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).hlen(key);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).hlen(key);
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).hlen(key);
		}else{
			throw new RedisOperationException("not support command [hlen] in " + sources.getClass().getName());
		}
	}

	@Override
	public Set<String> hkeys(String key) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).hkeys(key);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).hkeys(key);
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).hkeys(key);
		}else{
			throw new RedisOperationException("not support command [hkeys] in " + sources.getClass().getName());
		}
	}

	@Override
	public List<String> hvals(String key) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).hvals(key);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).hvals(key);
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).hvals(key);
		}else{
			throw new RedisOperationException("not support command [hvals] in " + sources.getClass().getName());
		}
	}

	@Override
	public Map<String, String> hgetAll(String key) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).hgetAll(key);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).hgetAll(key);
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).hgetAll(key);
		}else{
			throw new RedisOperationException("not support command [hgetAll] in " + sources.getClass().getName());
		}
	}

	@Override
	public Long lpush(String key, String... values) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).lpush(key, values);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).lpush(key, values);
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).lpush(key, values);
		}else{
			throw new RedisOperationException("not support command [lpush] in " + sources.getClass().getName());
		}
	}

	@Override
	public Long llen(String key) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).llen(key);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).llen(key);
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).llen(key);
		}else{
			throw new RedisOperationException("not support command [llen] in " + sources.getClass().getName());
		}
	}

	@Override
	public List<String> lrange(String key, long start, long end) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).lrange(key, start, end);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).lrange(key, start, end);
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).lrange(key, start, end);
		}else{
			throw new RedisOperationException("not support command [lrange] in " + sources.getClass().getName());
		}
	}

	@Override
	public String ltrim(String key, long start, long end) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).ltrim(key, start, end);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).ltrim(key, start, end);
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).ltrim(key, start, end);
		}else{
			throw new RedisOperationException("not support command [ltrim] in " + sources.getClass().getName());
		}
	}

	@Override
	public String lindex(String key, long index) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).lindex(key, index);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).lindex(key, index);
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).lindex(key, index);
		}else{
			throw new RedisOperationException("not support command [lindex] in " + sources.getClass().getName());
		}
	}

	@Override
	public String lset(String key, long index, String value) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).lset(key, index, value);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).lset(key, index, value);
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).lset(key, index, value);
		}else{
			throw new RedisOperationException("not support command [lset] in " + sources.getClass().getName());
		}
	}

	@Override
	public Long lrem(String key, long count, String value) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).lrem(key, count, value);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).lrem(key, count, value);
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).lrem(key, count, value);
		}else{
			throw new RedisOperationException("not support command [lrem] in " + sources.getClass().getName());
		}
	}

	@Override
	public String lpop(String key) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).lpop(key);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).lpop(key);
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).lpop(key);
		}else{
			throw new RedisOperationException("not support command [lpop] in " + sources.getClass().getName());
		}
	}

	@Override
	public Long lpushx(String key, String... values) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).lpushx(key, values);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).lpushx(key, values);
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).lpushx(key, values);
		}else{
			throw new RedisOperationException("not support command [lpushx] in " + sources.getClass().getName());
		}
	}

	@Override
	public List<String> blpop(int timeout, String key) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).blpop(timeout, key);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).blpop(timeout, key);
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).blpop(timeout, key);
		}else{
			throw new RedisOperationException("not support command [blpop] in " + sources.getClass().getName());
		}
	}

	@Override
	public List<String> brpop(int timeout, String key) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).brpop(timeout, key);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).brpop(timeout, key);
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).brpop(timeout, key);
		}else{
			throw new RedisOperationException("not support command [brpop] in " + sources.getClass().getName());
		}
	}

	@Override
	public String brpoplpush(String source, String destination, int timeout) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).brpoplpush(source, destination, timeout);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).brpoplpush(source, destination, timeout);
		}else{
			throw new RedisOperationException("not support command [brpoplpush] in " + sources.getClass().getName());
		}
	}

	@Override
	public String rpoplpush(String srckey, String dstkey) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).rpoplpush(srckey, dstkey);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).rpoplpush(srckey, dstkey);
		}else{
			throw new RedisOperationException("not support command [rpoplpush] in " + sources.getClass().getName());
		}
	}

	@Override
	public String rpop(String key) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).rpop(key);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).rpop(key);
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).rpop(key);
		}else{
			throw new RedisOperationException("not support command [rpop] in " + sources.getClass().getName());
		}
	}

	@Override
	public Long rpushx(String key, String... values) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).rpushx(key, values);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).rpushx(key, values);
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).rpushx(key, values);
		}else{
			throw new RedisOperationException("not support command [rpushx] in " + sources.getClass().getName());
		}
	}

	@Override
	public Long rpush(String key, String... values) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).rpushx(key, values);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).rpushx(key, values);
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).rpushx(key, values);
		}else{
			throw new RedisOperationException("not support command [rpushx] in " + sources.getClass().getName());
		}
	}

	@Override
	public Long linsertBefore(String key, String pivot, String value) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).linsert(key, LIST_POSITION.BEFORE, pivot, value);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).linsert(key, LIST_POSITION.BEFORE, pivot, value);
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).linsert(key, LIST_POSITION.BEFORE, pivot, value);
		}else{
			throw new RedisOperationException("not support command [linsert] in " + sources.getClass().getName());
		}
	}

	@Override
	public Long linsertAfter(String key, String pivot, String value) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).linsert(key, LIST_POSITION.AFTER, pivot, value);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).linsert(key, LIST_POSITION.AFTER, pivot, value);
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).linsert(key, LIST_POSITION.AFTER, pivot, value);
		}else{
			throw new RedisOperationException("not support command [linsert] in " + sources.getClass().getName());
		}
	}

	@Override
	public Long sadd(String key, String... members) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).sadd(key, members);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).sadd(key, members);
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).sadd(key, members);
		}else{
			throw new RedisOperationException("not support command [sadd] in " + sources.getClass().getName());
		}
	}

	@Override
	public Set<String> smembers(String key) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).smembers(key);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).smembers(key);
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).smembers(key);
		}else{
			throw new RedisOperationException("not support command [smembers] in " + sources.getClass().getName());
		}
	}

	@Override
	public Long srem(String key, String... members) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).srem(key, members);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).srem(key, members);
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).srem(key, members);
		}else{
			throw new RedisOperationException("not support command [srem] in " + sources.getClass().getName());
		}
	}

	@Override
	public String spop(String key) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).spop(key);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).spop(key);
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).spop(key);
		}else{
			throw new RedisOperationException("not support command [spop] in " + sources.getClass().getName());
		}
	}

	@Override
	public Set<String> spop(String key, long count) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).spop(key, count);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).spop(key, count);
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).spop(key, count);
		}else{
			throw new RedisOperationException("not support command [spop] in " + sources.getClass().getName());
		}
	}

	@Override
	public Long scard(String key) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).scard(key);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).scard(key);
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).scard(key);
		}else{
			throw new RedisOperationException("not support command [scard] in " + sources.getClass().getName());
		}
	}

	@Override
	public Boolean sismember(String key, String member) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).sismember(key, member);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).sismember(key, member);
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).sismember(key, member);
		}else{
			throw new RedisOperationException("not support command [sismember] in " + sources.getClass().getName());
		}
	}

	@Override
	public String srandmember(String key) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).srandmember(key);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).srandmember(key);
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).srandmember(key);
		}else{
			throw new RedisOperationException("not support command [srandmember] in " + sources.getClass().getName());
		}
	}

	@Override
	public List<String> srandmember(String key, int count) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).srandmember(key, count);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).srandmember(key, count);
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).srandmember(key, count);
		}else{
			throw new RedisOperationException("not support command [srandmember] in " + sources.getClass().getName());
		}
	}

	@Override
	public Set<String> sdiff(String... keys) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).sdiff(keys);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).sdiff(keys);
		}else{
			throw new RedisOperationException("not support command [sdiff] in " + sources.getClass().getName());
		}
	}

	@Override
	public Long sdiffstore(String dstkey, String... keys) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).sdiffstore(dstkey, keys);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).sdiffstore(dstkey, keys);
		}else{
			throw new RedisOperationException("not support command [sdiffstore] in " + sources.getClass().getName());
		}
	}

	@Override
	public Set<String> sinter(String... keys) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).sinter(keys);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).sinter(keys);
		}else{
			throw new RedisOperationException("not support command [sinter] in " + sources.getClass().getName());
		}
	}

	@Override
	public Long sinterstore(String dstkey, String... keys) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).sinterstore(dstkey, keys);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).sinterstore(dstkey, keys);
		}else{
			throw new RedisOperationException("not support command [sinterstore] in " + sources.getClass().getName());
		}
	}

	@Override
	public Long smove(String srckey, String dstkey, String member) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).smove(srckey, dstkey, member);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).smove(srckey, dstkey, member);
		}else{
			throw new RedisOperationException("not support command [smove] in " + sources.getClass().getName());
		}
	}

	@Override
	public Set<String> sunion(String... keys) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).sunion(keys);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).sunion(keys);
		}else{
			throw new RedisOperationException("not support command [sunion] in " + sources.getClass().getName());
		}
	}

	@Override
	public Long sunionstore(String dstkey, String... keys) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).sunionstore(dstkey, keys);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).sunionstore(dstkey, keys);
		}else{
			throw new RedisOperationException("not support command [sunionstore] in " + sources.getClass().getName());
		}
	}

	@Override
	public Long zadd(String key, double score, String member) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).zadd(key, score, member);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).zadd(key, score, member);
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).zadd(key, score, member);
		}else{
			throw new RedisOperationException("not support command [zadd] in " + sources.getClass().getName());
		}
	}

	@Override
	public Long zaddXx(String key, double score, String member) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).zadd(key, score, member, ZAddParams.zAddParams().xx());
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).zadd(key, score, member, ZAddParams.zAddParams().xx());
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).zadd(key, score, member, ZAddParams.zAddParams().xx());
		}else{
			throw new RedisOperationException("not support command [zadd] in " + sources.getClass().getName());
		}
	}
	
	@Override
	public Long zaddNx(String key, double score, String member) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).zadd(key, score, member, ZAddParams.zAddParams().nx());
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).zadd(key, score, member, ZAddParams.zAddParams().nx());
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).zadd(key, score, member, ZAddParams.zAddParams().nx());
		}else{
			throw new RedisOperationException("not support command [zadd] in " + sources.getClass().getName());
		}
	}
	
	@Override
	public Long zaddCh(String key, double score, String member) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).zadd(key, score, member, ZAddParams.zAddParams().ch());
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).zadd(key, score, member, ZAddParams.zAddParams().ch());
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).zadd(key, score, member, ZAddParams.zAddParams().ch());
		}else{
			throw new RedisOperationException("not support command [zadd] in " + sources.getClass().getName());
		}
	}

	@Override
	public Long zadd(String key, Map<String, Double> scoreMembers) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).zadd(key, scoreMembers);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).zadd(key, scoreMembers);
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).zadd(key, scoreMembers);
		}else{
			throw new RedisOperationException("not support command [zadd] in " + sources.getClass().getName());
		}
	}

	@Override
	public Long zaddXx(String key, Map<String, Double> scoreMembers) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).zadd(key, scoreMembers, ZAddParams.zAddParams().xx());
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).zadd(key, scoreMembers, ZAddParams.zAddParams().xx());
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).zadd(key, scoreMembers, ZAddParams.zAddParams().xx());
		}else{
			throw new RedisOperationException("not support command [zadd] in " + sources.getClass().getName());
		}
	}
	
	@Override
	public Long zaddNx(String key, Map<String, Double> scoreMembers) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).zadd(key, scoreMembers, ZAddParams.zAddParams().nx());
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).zadd(key, scoreMembers, ZAddParams.zAddParams().nx());
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).zadd(key, scoreMembers, ZAddParams.zAddParams().nx());
		}else{
			throw new RedisOperationException("not support command [zadd] in " + sources.getClass().getName());
		}
	}
	
	@Override
	public Long zaddCh(String key, Map<String, Double> scoreMembers) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).zadd(key, scoreMembers, ZAddParams.zAddParams().ch());
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).zadd(key, scoreMembers, ZAddParams.zAddParams().ch());
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).zadd(key, scoreMembers, ZAddParams.zAddParams().ch());
		}else{
			throw new RedisOperationException("not support command [zadd] in " + sources.getClass().getName());
		}
	}

	@Override
	public Set<String> zrange(String key, long start, long end) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).zrange(key, start, end);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).zrange(key, start, end);
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).zrange(key, start, end);
		}else{
			throw new RedisOperationException("not support command [zrange] in " + sources.getClass().getName());
		}
	}

	@Override
	public Long zrem(String key, String... members) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).zrem(key, members);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).zrem(key, members);
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).zrem(key, members);
		}else{
			throw new RedisOperationException("not support command [zrem] in " + sources.getClass().getName());
		}
	}

	@Override
	public Double zincrby(String key, double score, String member) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).zincrby(key, score, member);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).zincrby(key, score, member);
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).zincrby(key, score, member);
		}else{
			throw new RedisOperationException("not support command [zincrby] in " + sources.getClass().getName());
		}
	}

	@Override
	public Double zincrbyNx(String key, double score, String member) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).zincrby(key, score, member, ZIncrByParams.zIncrByParams().nx());
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).zincrby(key, score, member, ZIncrByParams.zIncrByParams().nx());
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).zincrby(key, score, member, ZIncrByParams.zIncrByParams().nx());
		}else{
			throw new RedisOperationException("not support command [zincrby] in " + sources.getClass().getName());
		}
	}
	
	@Override
	public Double zincrbyXx(String key, double score, String member) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).zincrby(key, score, member, ZIncrByParams.zIncrByParams().xx());
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).zincrby(key, score, member, ZIncrByParams.zIncrByParams().xx());
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).zincrby(key, score, member, ZIncrByParams.zIncrByParams().xx());
		}else{
			throw new RedisOperationException("not support command [zincrby] in " + sources.getClass().getName());
		}
	}

	@Override
	public Long zrank(String key, String member) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).zrank(key, member);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).zrank(key, member);
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).zrank(key, member);
		}else{
			throw new RedisOperationException("not support command [zrank] in " + sources.getClass().getName());
		}
	}

	@Override
	public Long zrevrank(String key, String member) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).zrevrank(key, member);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).zrevrank(key, member);
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).zrevrank(key, member);
		}else{
			throw new RedisOperationException("not support command [zrevrank] in " + sources.getClass().getName());
		}
	}

	@Override
	public Set<String> zrevrange(String key, long start, long end) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).zrevrange(key, start, end);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).zrevrange(key, start, end);
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).zrevrange(key, start, end);
		}else{
			throw new RedisOperationException("not support command [zrevrange] in " + sources.getClass().getName());
		}
	}

	private Set<Map<String, Double>> zScoresResult(Set<Tuple> zrangeWithScores){
		if(zrangeWithScores != null){
			Set<Map<String, Double>> res = new LinkedHashSet<>();
			for (Tuple tuple : zrangeWithScores) {
				Map<String, Double> item = new HashMap<>(1,2);
				item.put(tuple.getElement(), tuple.getScore());
				res.add(item);
			}
			return res;
		}
		return null;
	}
	
	@Override
	public Set<Map<String, Double>> zrangeWithScores(String key, long start, long end) throws RedisOperationException {
		if(sources instanceof Jedis){
			return zScoresResult(((Jedis)sources).zrangeWithScores(key, start, end));
		}else if(sources instanceof JedisCluster){
			return zScoresResult(((JedisCluster)sources).zrangeWithScores(key, start, end));
		}else if(sources instanceof ShardedJedis){
			return zScoresResult(((ShardedJedis)sources).zrangeWithScores(key, start, end));
		}else{
			throw new RedisOperationException("not support command [zrangeWithScores] in " + sources.getClass().getName());
		}
	}

	@Override
	public Set<Map<String, Double>> zrevrangeWithScores(String key, long start, long end) throws RedisOperationException {
		if(sources instanceof Jedis){
			return zScoresResult(((Jedis)sources).zrevrangeWithScores(key, start, end));
		}else if(sources instanceof JedisCluster){
			return zScoresResult(((JedisCluster)sources).zrevrangeWithScores(key, start, end));
		}else if(sources instanceof ShardedJedis){
			return zScoresResult(((ShardedJedis)sources).zrevrangeWithScores(key, start, end));
		}else{
			throw new RedisOperationException("not support command [zrevrangeWithScores] in " + sources.getClass().getName());
		}
	}

	@Override
	public Long zcard(String key) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).zcard(key);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).zcard(key);
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).zcard(key);
		}else{
			throw new RedisOperationException("not support command [zcard] in " + sources.getClass().getName());
		}
	}

	@Override
	public Double zscore(String key, String member) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).zscore(key, member);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).zscore(key, member);
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).zscore(key, member);
		}else{
			throw new RedisOperationException("not support command [zscore] in " + sources.getClass().getName());
		}
	}

	@Override
	public Long zcount(String key, double min, double max) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).zcount(key, min, max);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).zcount(key, min, max);
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).zcount(key, min, max);
		}else{
			throw new RedisOperationException("not support command [zcount] in " + sources.getClass().getName());
		}
	}

	@Override
	public Long zcount(String key, String min, String max) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).zcount(key, min, max);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).zcount(key, min, max);
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).zcount(key, min, max);
		}else{
			throw new RedisOperationException("not support command [zcount] in " + sources.getClass().getName());
		}
	}

	@Override
	public Set<String> zrangeByScore(String key, double min, double max) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).zrangeByScore(key, min, max);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).zrangeByScore(key, min, max);
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).zrangeByScore(key, min, max);
		}else{
			throw new RedisOperationException("not support command [zrangeByScore] in " + sources.getClass().getName());
		}
	}

	@Override
	public Set<String> zrangeByScore(String key, String min, String max) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).zrangeByScore(key, min, max);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).zrangeByScore(key, min, max);
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).zrangeByScore(key, min, max);
		}else{
			throw new RedisOperationException("not support command [zrangeByScore] in " + sources.getClass().getName());
		}
	}

	@Override
	public Set<String> zrevrangeByScore(String key, double max, double min) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).zrevrangeByScore(key, min, max);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).zrevrangeByScore(key, min, max);
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).zrevrangeByScore(key, min, max);
		}else{
			throw new RedisOperationException("not support command [zrevrangeByScore] in " + sources.getClass().getName());
		}
	}

	@Override
	public Set<String> zrangeByScore(String key, double min, double max, int offset, int count) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).zrangeByScore(key, min, max, offset, count);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).zrangeByScore(key, min, max, offset, count);
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).zrangeByScore(key, min, max, offset, count);
		}else{
			throw new RedisOperationException("not support command [zrangeByScore] in " + sources.getClass().getName());
		}
	}

	@Override
	public Set<String> zrevrangeByScore(String key, String max, String min) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).zrevrangeByScore(key, min, max);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).zrevrangeByScore(key, min, max);
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).zrevrangeByScore(key, min, max);
		}else{
			throw new RedisOperationException("not support command [zrevrangeByScore] in " + sources.getClass().getName());
		}
	}

	@Override
	public Set<String> zrangeByScore(String key, String min, String max, int offset, int count) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).zrangeByScore(key, min, max, offset, count);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).zrangeByScore(key, min, max, offset, count);
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).zrangeByScore(key, min, max, offset, count);
		}else{
			throw new RedisOperationException("not support command [zrangeByScore] in " + sources.getClass().getName());
		}
	}

	@Override
	public Set<String> zrevrangeByScore(String key, double max, double min, int offset, int count) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).zrevrangeByScore(key, min, max, offset, count);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).zrevrangeByScore(key, min, max, offset, count);
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).zrevrangeByScore(key, min, max, offset, count);
		}else{
			throw new RedisOperationException("not support command [zrevrangeByScore] in " + sources.getClass().getName());
		}
	}

	@Override
	public Set<Map<String, Double>> zrangeByScoreWithScores(String key, double min, double max) throws RedisOperationException {
		if(sources instanceof Jedis){
			return zScoresResult(((Jedis)sources).zrangeByScoreWithScores(key, min, max));
		}else if(sources instanceof JedisCluster){
			return zScoresResult(((JedisCluster)sources).zrangeByScoreWithScores(key, min, max));
		}else if(sources instanceof ShardedJedis){
			return zScoresResult(((ShardedJedis)sources).zrangeByScoreWithScores(key, min, max));
		}else{
			throw new RedisOperationException("not support command [zrangeByScoreWithScores] in " + sources.getClass().getName());
		}
	}

	@Override
	public Set<Map<String, Double>> zrevrangeByScoreWithScores(String key, double max, double min) throws RedisOperationException {
		if(sources instanceof Jedis){
			return zScoresResult(((Jedis)sources).zrevrangeByScoreWithScores(key, min, max));
		}else if(sources instanceof JedisCluster){
			return zScoresResult(((JedisCluster)sources).zrevrangeByScoreWithScores(key, min, max));
		}else if(sources instanceof ShardedJedis){
			return zScoresResult(((ShardedJedis)sources).zrevrangeByScoreWithScores(key, min, max));
		}else{
			throw new RedisOperationException("not support command [zrevrangeByScoreWithScores] in " + sources.getClass().getName());
		}
	}

	@Override
	public Set<Map<String, Double>> zrangeByScoreWithScores(String key, double min, double max, int offset, int count) throws RedisOperationException {
		if(sources instanceof Jedis){
			return zScoresResult(((Jedis)sources).zrangeByScoreWithScores(key, min, max, offset, count));
		}else if(sources instanceof JedisCluster){
			return zScoresResult(((JedisCluster)sources).zrangeByScoreWithScores(key, min, max, offset, count));
		}else if(sources instanceof ShardedJedis){
			return zScoresResult(((ShardedJedis)sources).zrangeByScoreWithScores(key, min, max, offset, count));
		}else{
			throw new RedisOperationException("not support command [zrangeByScoreWithScores] in " + sources.getClass().getName());
		}
	}

	@Override
	public Set<String> zrevrangeByScore(String key, String max, String min, int offset, int count) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).zrevrangeByScore(key, min, max, offset, count);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).zrevrangeByScore(key, min, max, offset, count);
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).zrevrangeByScore(key, min, max, offset, count);
		}else{
			throw new RedisOperationException("not support command [zrevrangeByScore] in " + sources.getClass().getName());
		}
	}

	@Override
	public Set<Map<String, Double>> zrangeByScoreWithScores(String key, String min, String max) throws RedisOperationException {
		if(sources instanceof Jedis){
			return zScoresResult(((Jedis)sources).zrangeByScoreWithScores(key, min, max));
		}else if(sources instanceof JedisCluster){
			return zScoresResult(((JedisCluster)sources).zrangeByScoreWithScores(key, min, max));
		}else if(sources instanceof ShardedJedis){
			return zScoresResult(((ShardedJedis)sources).zrangeByScoreWithScores(key, min, max));
		}else{
			throw new RedisOperationException("not support command [zrangeByScoreWithScores] in " + sources.getClass().getName());
		}
	}

	@Override
	public Set<Map<String, Double>> zrevrangeByScoreWithScores(String key, String max, String min) throws RedisOperationException {
		if(sources instanceof Jedis){
			return zScoresResult(((Jedis)sources).zrevrangeByScoreWithScores(key, min, max));
		}else if(sources instanceof JedisCluster){
			return zScoresResult(((JedisCluster)sources).zrevrangeByScoreWithScores(key, min, max));
		}else if(sources instanceof ShardedJedis){
			return zScoresResult(((ShardedJedis)sources).zrevrangeByScoreWithScores(key, min, max));
		}else{
			throw new RedisOperationException("not support command [zrevrangeByScoreWithScores] in " + sources.getClass().getName());
		}
	}

	@Override
	public Set<Map<String, Double>> zrangeByScoreWithScores(String key, String min, String max, int offset, int count) throws RedisOperationException {
		if(sources instanceof Jedis){
			return zScoresResult(((Jedis)sources).zrangeByScoreWithScores(key, min, max, offset, count));
		}else if(sources instanceof JedisCluster){
			return zScoresResult(((JedisCluster)sources).zrangeByScoreWithScores(key, min, max, offset, count));
		}else if(sources instanceof ShardedJedis){
			return zScoresResult(((ShardedJedis)sources).zrangeByScoreWithScores(key, min, max, offset, count));
		}else{
			throw new RedisOperationException("not support command [zrangeByScoreWithScores] in " + sources.getClass().getName());
		}
	}

	@Override
	public Set<Map<String, Double>> zrevrangeByScoreWithScores(String key, double max, double min, int offset,
			int count) throws RedisOperationException {
		if(sources instanceof Jedis){
			return zScoresResult(((Jedis)sources).zrevrangeByScoreWithScores(key, min, max, offset, count));
		}else if(sources instanceof JedisCluster){
			return zScoresResult(((JedisCluster)sources).zrevrangeByScoreWithScores(key, min, max, offset, count));
		}else if(sources instanceof ShardedJedis){
			return zScoresResult(((ShardedJedis)sources).zrevrangeByScoreWithScores(key, min, max, offset, count));
		}else{
			throw new RedisOperationException("not support command [zrevrangeByScoreWithScores] in " + sources.getClass().getName());
		}
	}

	@Override
	public Set<Map<String, Double>> zrevrangeByScoreWithScores(String key, String max, String min, int offset,
			int count) throws RedisOperationException {
		if(sources instanceof Jedis){
			return zScoresResult(((Jedis)sources).zrevrangeByScoreWithScores(key, min, max, offset, count));
		}else if(sources instanceof JedisCluster){
			return zScoresResult(((JedisCluster)sources).zrevrangeByScoreWithScores(key, min, max, offset, count));
		}else if(sources instanceof ShardedJedis){
			return zScoresResult(((ShardedJedis)sources).zrevrangeByScoreWithScores(key, min, max, offset, count));
		}else{
			throw new RedisOperationException("not support command [zrevrangeByScoreWithScores] in " + sources.getClass().getName());
		}
	}

	@Override
	public Long zremrangeByRank(String key, long start, long end) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).zremrangeByRank(key, start, end);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).zremrangeByRank(key, start, end);
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).zremrangeByRank(key, start, end);
		}else{
			throw new RedisOperationException("not support command [zremrangeByRank] in " + sources.getClass().getName());
		}
	}

	@Override
	public Long zremrangeByScore(String key, double start, double end) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).zremrangeByScore(key, start, end);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).zremrangeByScore(key, start, end);
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).zremrangeByScore(key, start, end);
		}else{
			throw new RedisOperationException("not support command [zremrangeByScore] in " + sources.getClass().getName());
		}
	}

	@Override
	public Long zremrangeByScore(String key, String start, String end) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).zremrangeByScore(key, start, end);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).zremrangeByScore(key, start, end);
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).zremrangeByScore(key, start, end);
		}else{
			throw new RedisOperationException("not support command [zremrangeByScore] in " + sources.getClass().getName());
		}
	}

	@Override
	public Long zlexcount(String key, String min, String max) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).zlexcount(key, min, max);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).zlexcount(key, min, max);
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).zlexcount(key, min, max);
		}else{
			throw new RedisOperationException("not support command [zlexcount] in " + sources.getClass().getName());
		}
	}

	@Override
	public Set<String> zrangeByLex(String key, String min, String max) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).zrangeByLex(key, min, max);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).zrangeByLex(key, min, max);
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).zrangeByLex(key, min, max);
		}else{
			throw new RedisOperationException("not support command [zrangeByLex] in " + sources.getClass().getName());
		}
	}

	@Override
	public Set<String> zrangeByLex(String key, String min, String max, int offset, int count) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).zrangeByLex(key, min, max, offset, count);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).zrangeByLex(key, min, max, offset, count);
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).zrangeByLex(key, min, max, offset, count);
		}else{
			throw new RedisOperationException("not support command [zrangeByLex] in " + sources.getClass().getName());
		}
	}

	@Override
	public Set<String> zrevrangeByLex(String key, String max, String min) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).zrevrangeByLex(key, min, max);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).zrevrangeByLex(key, min, max);
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).zrevrangeByLex(key, min, max);
		}else{
			throw new RedisOperationException("not support command [zrevrangeByLex] in " + sources.getClass().getName());
		}
	}

	@Override
	public Set<String> zrevrangeByLex(String key, String max, String min, int offset, int count) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).zrevrangeByLex(key, min, max, offset, count);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).zrevrangeByLex(key, min, max, offset, count);
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).zrevrangeByLex(key, min, max, offset, count);
		}else{
			throw new RedisOperationException("not support command [zrevrangeByLex] in " + sources.getClass().getName());
		}
	}

	@Override
	public Long zremrangeByLex(String key, String min, String max) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).zremrangeByLex(key, min, max);
		}else if(sources instanceof JedisCluster){
			return ((JedisCluster)sources).zremrangeByLex(key, min, max);
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).zremrangeByLex(key, min, max);
		}else{
			throw new RedisOperationException("not support command [zremrangeByLex] in " + sources.getClass().getName());
		}
	}

	@Override
	public Long hdelByRename(String routeKey, String dataKey, String... fields) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).hdel(dataKey, fields);
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).getShard(routeKey).hdel(dataKey, fields);
		}else{
			throw new RedisOperationException("not support command [hdelByRename] in " + sources.getClass().getName());
		}
	}

	@Override
	public String hgetByRename(String routeKey, String dataKey, String field) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).hget(dataKey, field);
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).getShard(routeKey).hget(dataKey, field);
		}else{
			throw new RedisOperationException("not support command [hgetByRename] in " + sources.getClass().getName());
		}
	}

	@Override
	public Set<String> hkeysByRename(String routeKey, String dataKey) throws RedisOperationException {
		if(sources instanceof Jedis){
			return ((Jedis)sources).hkeys(dataKey);
		}else if(sources instanceof ShardedJedis){
			return ((ShardedJedis)sources).getShard(routeKey).hkeys(dataKey);
		}else{
			throw new RedisOperationException("not support command [hkeysByRename] in " + sources.getClass().getName());
		}
	}

}
