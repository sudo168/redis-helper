package net.ewant.redis.connect;

import net.ewant.redis.factory.RedisConnectionFactory;
import net.ewant.redis.RedisOperationException;
import redis.clients.jedis.BinaryClient.LIST_POSITION;
import redis.clients.jedis.*;
import redis.clients.jedis.params.sortedset.ZAddParams;
import redis.clients.jedis.params.sortedset.ZIncrByParams;

import java.util.*;

public class DefaultRedisSingleConnection extends AbstractRedisConnection implements RedisSingleConnection{
	
	private Jedis sources;
	
	private volatile Pipeline pipeline;

	public DefaultRedisSingleConnection(RedisConnectionFactory factory, Jedis jedis) {
		super(factory, jedis);
		this.sources = jedis;
	}

	@Override
	public Jedis getNativeConnection() {
		return this.sources;
	}
	
	@Override
	public void close() {
		sources.close();
	}
	
	@Override
	public boolean isClosed() {
		return sources == null || !sources.isConnected();
	}

	@Override
	public boolean isQueueing() {
		return sources != null && sources.getClient().isInMulti();
	}

	@Override
	public boolean isPipelined() {
		return pipeline != null;
	}

	@Override
	public void openPipeline() {
		if(pipeline == null){
			pipeline = sources.pipelined();
		}
	}

	@Override
	public List<Object> closePipeline() {
		return null;
	}

	@Override
	public String ping() throws RedisOperationException {
		return sources.ping();
	}

	@Override
	public String quit() throws RedisOperationException {
		return sources.quit();
	}

	@Override
	public String select(int index) throws RedisOperationException {
		return sources.select(index);
	}

	@Override
	public String auth(String password) throws RedisOperationException {
		return sources.auth(password);
	}

	@Override
	public String echo(String string) throws RedisOperationException {
		return sources.echo(string);
	}

	@Override
	public String flushDB() throws RedisOperationException {
		return sources.flushDB();
	}

	@Override
	public Long dbSize() throws RedisOperationException {
		return sources.dbSize();
	}

	@Override
	public String flushAll() throws RedisOperationException {
		return sources.flushAll();
	}

	@Override
	public Long getDB() throws RedisOperationException {
		return sources.getDB();
	}

	@Override
	public String psetex(String key, long milliseconds, String value) throws RedisOperationException {
		return sources.psetex(key, milliseconds, value);
	}

	@Override
	public Long del(String... key) throws RedisOperationException {
		return sources.del(key);
	}

	@Override
	public byte[] dump(String key) throws RedisOperationException {
		return sources.dump(key);
	}

	@Override
	public Boolean exists(String key) throws RedisOperationException {
		return sources.exists(key);
	}

	@Override
	public Long expire(String key, int seconds) throws RedisOperationException {
		return sources.expire(key,seconds);
	}

	@Override
	public Long expireAt(String key, long unixTime) throws RedisOperationException {
		return sources.expireAt(key,unixTime);
	}

	@Override
	public Set<String> keys(String pattern) throws RedisOperationException {
		return sources.keys(pattern);
	}

	@Override
	public boolean migrate(String host, int port, String key, int destinationDb, int timeout) throws RedisOperationException {
		return "OK".equals(sources.migrate(host, port, key, destinationDb, timeout));
	}

	@Override
	public Long move(String key, int dbIndex) throws RedisOperationException {
		return sources.move(key,dbIndex);
	}

	@Override
	public Long persist(String key) throws RedisOperationException {
		return sources.persist(key);
	}

	@Override
	public Long pexpire(String key, long seconds) throws RedisOperationException {
		return sources.pexpire(key,seconds);
	}

	@Override
	public Long pexpireAt(String key, long millisecondsTimestamp) throws RedisOperationException {
		return sources.pexpireAt(key,millisecondsTimestamp);
	}

	@Override
	public Long pttl(String key) throws RedisOperationException {
		return sources.pttl(key);
	}

	@Override
	public boolean rename(String key, String newKey) throws RedisOperationException {
		return "OK".equals(sources.rename(key,newKey));
	}

	@Override
	public boolean renameNx(String key, String newKey) throws RedisOperationException {
		return sources.renamenx(key,newKey) == 1;
	}

	@Override
	public String restore(String key, int ttl, byte[] serializedValue) throws RedisOperationException {
		return sources.restore(key,ttl,serializedValue);
	}

	@Override
	public Map<String, List<String>> scan(String cursor) throws RedisOperationException {
		Map<String, List<String>> res = new HashMap<>(1,2);
		ScanResult<String> scan = sources.scan(cursor);
		if(scan != null){
			res.put(scan.getStringCursor(), scan.getResult());
		}
		return res;
	}

	@Override
	public Map<String, List<String>> scan(String cursor, String matchPattern) throws RedisOperationException {
		Map<String, List<String>> res = new HashMap<>(1,2);
		ScanResult<String> scan = sources.scan(cursor, new ScanParams().match(matchPattern));
		if(scan != null){
			res.put(scan.getStringCursor(), scan.getResult());
		}
		return res;
	}

	@Override
	public Map<String, List<String>> scan(String cursor, String matchPattern, int count) throws RedisOperationException {
		Map<String, List<String>> res = new HashMap<>(1,2);
		ScanResult<String> scan = sources.scan(cursor, new ScanParams().match(matchPattern).count(count));
		if(scan != null){
			res.put(scan.getStringCursor(), scan.getResult());
		}
		return res;
	}

	@Override
	public Long ttl(String key) throws RedisOperationException {
		return sources.ttl(key);
	}

	@Override
	public Long append(String key, String value) throws RedisOperationException {
		return sources.append(key,value);
	}

	@Override
	public Long bitcount(String key) throws RedisOperationException {
		return sources.bitcount(key);
	}

	@Override
	public Long bitcount(String key, long start, long end) throws RedisOperationException {
		return sources.bitcount(key,start,end);
	}

	@Override
	public Long bitopAnd(String destKey, String... srcKeys) throws RedisOperationException {
		return sources.bitop(BitOP.AND, destKey, srcKeys);
	}
	
	@Override
	public Long bitopOr(String destKey, String... srcKeys) throws RedisOperationException {
		return sources.bitop(BitOP.OR, destKey, srcKeys);
	}
	
	@Override
	public Long bitopXor(String destKey, String... srcKeys) throws RedisOperationException {
		return sources.bitop(BitOP.XOR, destKey, srcKeys);
	}
	
	@Override
	public Long bitopNot(String destKey, String... srcKeys) throws RedisOperationException {
		return sources.bitop(BitOP.NOT, destKey, srcKeys);
	}

	@Override
	public Long bitpos(String key, boolean value) throws RedisOperationException {
		return sources.bitpos(key,value);
	}

	@Override
	public Long bitpos(String key, boolean value, long start) throws RedisOperationException {
		return sources.bitpos(key,value,new BitPosParams(start));
	}

	@Override
	public Long bitpos(String key, boolean value, long start, long end) throws RedisOperationException {
		return sources.bitpos(key,value,new BitPosParams(start,end));
	}

	@Override
	public Long decr(String key) throws RedisOperationException {
		return sources.decr(key);
	}

	@Override
	public Long decrBy(String key, long decrement) throws RedisOperationException {
		return sources.decrBy(key,decrement);
	}

	@Override
	public String get(String key) throws RedisOperationException {
		return sources.get(key);
	}

	@Override
	public Boolean getbit(String key, long offset) throws RedisOperationException {
		return sources.getbit(key,offset);
	}

	@Override
	public String getRange(String key, long startOffset, long endOffset) throws RedisOperationException {
		return sources.getrange(key,startOffset,endOffset);
	}

	@Override
	public String getSet(String key, String value) throws RedisOperationException {
		return sources.getSet(key,value);
	}

	@Override
	public Long incr(String key) throws RedisOperationException {
		return sources.incr(key);
	}

	@Override
	public Long incrBy(String key, long increment) throws RedisOperationException {
		return sources.incrBy(key,increment);
	}

	@Override
	public Double incrByFloat(String key, double increment) throws RedisOperationException {
		return sources.incrByFloat(key,increment);
	}

	@Override
	public List<String> mget(String... keys) throws RedisOperationException {
		return sources.mget(keys);
	}

	@Override
	public String mset(String... keysvalues) throws RedisOperationException {
		return sources.mset(keysvalues);
	}

	@Override
	public Long msetnx(String... keysvalues) throws RedisOperationException {
		return sources.msetnx(keysvalues);
	}

	@Override
	public String set(String key, String value) throws RedisOperationException {
		return sources.set(key,value);
	}

	@Override
	public String set(String key, String value, String nxxx) throws RedisOperationException {
		return sources.set(key,value,nxxx);
	}

	@Override
	public Long setNx(String key, String value) throws RedisOperationException {
		return sources.setnx(key,value);
	}

	@Override
	public String setEx(String key, int seconds, String value) throws RedisOperationException {
		return sources.setex(key, seconds, value);
	}

	@Override
	public String set(String key, String value, String nxxx, String expx, long time) throws RedisOperationException {
		return sources.set(key, value, nxxx, expx, time);
	}

	@Override
	public Boolean setbit(String key, long offset, boolean value) throws RedisOperationException {
		return sources.setbit(key, offset, value);
	}

	@Override
	public Long setRange(String key, long offset, String value) throws RedisOperationException {
		return sources.setrange(key, offset, value);
	}

	@Override
	public Long strlen(String key) throws RedisOperationException {
		return sources.strlen(key);
	}

	@Override
	public Long hset(String key, String field, String value) throws RedisOperationException {
		return sources.hset(key, field, value);
	}

	@Override
	public String hget(String key, String field) throws RedisOperationException {
		return sources.hget(key, field);
	}

	@Override
	public Long hsetnx(String key, String field, String value) throws RedisOperationException {
		return sources.hsetnx(key, field, value);
	}

	@Override
	public String hmset(String key, Map<String, String> hash) throws RedisOperationException {
		return sources.hmset(key, hash);
	}

	@Override
	public List<String> hmget(String key, String... fields) throws RedisOperationException {
		return sources.hmget(key, fields);
	}

	@Override
	public Long hincrBy(String key, String field, long value) throws RedisOperationException {
		return sources.hincrBy(key, field, value);
	}

	@Override
	public Double hincrByFloat(String key, String field, double value) throws RedisOperationException {
		return sources.hincrByFloat(key, field, value);
	}

	@Override
	public Boolean hexists(String key, String field) throws RedisOperationException {
		return sources.hexists(key, field);
	}

	@Override
	public Long hdel(String key, String... fields) throws RedisOperationException {
		return sources.hdel(key, fields);
	}

	@Override
	public Long hlen(String key) throws RedisOperationException {
		return sources.hlen(key);
	}

	@Override
	public Set<String> hkeys(String key) throws RedisOperationException {
		return sources.hkeys(key);
	}

	@Override
	public List<String> hvals(String key) throws RedisOperationException {
		return sources.hvals(key);
	}

	@Override
	public Map<String, String> hgetAll(String key) throws RedisOperationException {
		return sources.hgetAll(key);
	}

	@Override
	public Long lpush(String key, String... values) throws RedisOperationException {
		return sources.lpush(key, values);
	}

	@Override
	public Long llen(String key) throws RedisOperationException {
		return sources.llen(key);
	}

	@Override
	public List<String> lrange(String key, long start, long end) throws RedisOperationException {
		return sources.lrange(key, start, end);
	}

	@Override
	public String ltrim(String key, long start, long end) throws RedisOperationException {
		return sources.ltrim(key, start, end);
	}

	@Override
	public String lindex(String key, long index) throws RedisOperationException {
		return sources.lindex(key, index);
	}

	@Override
	public String lset(String key, long index, String value) throws RedisOperationException {
		return sources.lset(key, index, value);
	}

	@Override
	public Long lrem(String key, long count, String value) throws RedisOperationException {
		return sources.lrem(key, count, value);
	}

	@Override
	public String lpop(String key) throws RedisOperationException {
		return sources.lpop(key);
	}

	@Override
	public Long lpushx(String key, String... values) throws RedisOperationException {
		return sources.lpushx(key, values);
	}

	@Override
	public List<String> blpop(int timeout, String key) throws RedisOperationException {
		return sources.blpop(timeout, key);
	}

	@Override
	public List<String> brpop(int timeout, String key) throws RedisOperationException {
		return sources.brpop(timeout, key);
	}

	@Override
	public String brpoplpush(String source, String destination, int timeout) throws RedisOperationException {
		return sources.brpoplpush(source, destination, timeout);
	}

	@Override
	public String rpoplpush(String srckey, String dstkey) throws RedisOperationException {
		return sources.rpoplpush(srckey, dstkey);
	}

	@Override
	public String rpop(String key) throws RedisOperationException {
		return sources.rpop(key);
	}

	@Override
	public Long rpushx(String key, String... values) throws RedisOperationException {
		return sources.rpushx(key, values);
	}

	@Override
	public Long rpush(String key, String... values) throws RedisOperationException {
		return sources.rpushx(key, values);
	}

	@Override
	public Long linsertBefore(String key, String pivot, String value) throws RedisOperationException {
		return sources.linsert(key, LIST_POSITION.BEFORE, pivot, value);
	}

	@Override
	public Long linsertAfter(String key, String pivot, String value) throws RedisOperationException {
		return sources.linsert(key, LIST_POSITION.AFTER, pivot, value);
	}

	@Override
	public Long sadd(String key, String... members) throws RedisOperationException {
		return sources.sadd(key, members);
	}

	@Override
	public Set<String> smembers(String key) throws RedisOperationException {
		return sources.smembers(key);
	}

	@Override
	public Long srem(String key, String... members) throws RedisOperationException {
		return sources.srem(key, members);
	}

	@Override
	public String spop(String key) throws RedisOperationException {
		return sources.spop(key);
	}

	@Override
	public Set<String> spop(String key, long count) throws RedisOperationException {
		return sources.spop(key, count);
	}

	@Override
	public Long scard(String key) throws RedisOperationException {
		return sources.scard(key);
	}

	@Override
	public Boolean sismember(String key, String member) throws RedisOperationException {
		return sources.sismember(key, member);
	}

	@Override
	public String srandmember(String key) throws RedisOperationException {
		return sources.srandmember(key);
	}

	@Override
	public List<String> srandmember(String key, int count) throws RedisOperationException {
		return sources.srandmember(key, count);
	}

	@Override
	public Set<String> sdiff(String... keys) throws RedisOperationException {
		return sources.sdiff(keys);
	}

	@Override
	public Long sdiffstore(String dstkey, String... keys) throws RedisOperationException {
		return sources.sdiffstore(dstkey, keys);
	}

	@Override
	public Set<String> sinter(String... keys) throws RedisOperationException {
		return sources.sinter(keys);
	}

	@Override
	public Long sinterstore(String dstkey, String... keys) throws RedisOperationException {
		return sources.sinterstore(dstkey, keys);
	}

	@Override
	public Long smove(String srckey, String dstkey, String member) throws RedisOperationException {
		return sources.smove(srckey, dstkey, member);
	}

	@Override
	public Set<String> sunion(String... keys) throws RedisOperationException {
		return sources.sunion(keys);
	}

	@Override
	public Long sunionstore(String dstkey, String... keys) throws RedisOperationException {
		return sources.sunionstore(dstkey, keys);
	}

	@Override
	public Long zadd(String key, double score, String member) throws RedisOperationException {
		return sources.zadd(key, score, member);
	}

	@Override
	public Long zaddXx(String key, double score, String member) throws RedisOperationException {
		return sources.zadd(key, score, member, ZAddParams.zAddParams().xx());
	}
	
	@Override
	public Long zaddNx(String key, double score, String member) throws RedisOperationException {
		return sources.zadd(key, score, member, ZAddParams.zAddParams().nx());
	}
	
	@Override
	public Long zaddCh(String key, double score, String member) throws RedisOperationException {
		return sources.zadd(key, score, member, ZAddParams.zAddParams().ch());
	}

	@Override
	public Long zadd(String key, Map<String, Double> scoreMembers) throws RedisOperationException {
		return sources.zadd(key, scoreMembers);
	}

	@Override
	public Long zaddXx(String key, Map<String, Double> scoreMembers) throws RedisOperationException {
		return sources.zadd(key, scoreMembers, ZAddParams.zAddParams().xx());
	}
	
	@Override
	public Long zaddNx(String key, Map<String, Double> scoreMembers) throws RedisOperationException {
		return sources.zadd(key, scoreMembers, ZAddParams.zAddParams().nx());
	}
	
	@Override
	public Long zaddCh(String key, Map<String, Double> scoreMembers) throws RedisOperationException {
		return sources.zadd(key, scoreMembers, ZAddParams.zAddParams().ch());
	}

	@Override
	public Set<String> zrange(String key, long start, long end) throws RedisOperationException {
		return sources.zrange(key, start, end);
	}

	@Override
	public Long zrem(String key, String... members) throws RedisOperationException {
		return sources.zrem(key, members);
	}

	@Override
	public Double zincrby(String key, double score, String member) throws RedisOperationException {
		return sources.zincrby(key, score, member);
	}

	@Override
	public Double zincrbyNx(String key, double score, String member) throws RedisOperationException {
		return sources.zincrby(key, score, member, ZIncrByParams.zIncrByParams().nx());
	}
	
	@Override
	public Double zincrbyXx(String key, double score, String member) throws RedisOperationException {
		return sources.zincrby(key, score, member, ZIncrByParams.zIncrByParams().xx());
	}

	@Override
	public Long zrank(String key, String member) throws RedisOperationException {
		return sources.zrank(key, member);
	}

	@Override
	public Long zrevrank(String key, String member) throws RedisOperationException {
		return sources.zrevrank(key, member);
	}

	@Override
	public Set<String> zrevrange(String key, long start, long end) throws RedisOperationException {
		return sources.zrevrange(key, start, end);
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
		return zScoresResult(sources.zrangeWithScores(key, start, end));
	}

	@Override
	public Set<Map<String, Double>> zrevrangeWithScores(String key, long start, long end) throws RedisOperationException {
		return zScoresResult(sources.zrevrangeWithScores(key, start, end));
	}

	@Override
	public Long zcard(String key) throws RedisOperationException {
		return sources.zcard(key);
	}

	@Override
	public Double zscore(String key, String member) throws RedisOperationException {
		return sources.zscore(key, member);
	}

	@Override
	public Long zcount(String key, double min, double max) throws RedisOperationException {
		return sources.zcount(key, min, max);
	}

	@Override
	public Long zcount(String key, String min, String max) throws RedisOperationException {
		return sources.zcount(key, min, max);
	}

	@Override
	public Set<String> zrangeByScore(String key, double min, double max) throws RedisOperationException {
		return sources.zrangeByScore(key, min, max);
	}

	@Override
	public Set<String> zrangeByScore(String key, String min, String max) throws RedisOperationException {
		return sources.zrangeByScore(key, min, max);
	}

	@Override
	public Set<String> zrevrangeByScore(String key, double max, double min) throws RedisOperationException {
		return sources.zrevrangeByScore(key, min, max);
	}

	@Override
	public Set<String> zrangeByScore(String key, double min, double max, int offset, int count) throws RedisOperationException {
		return sources.zrangeByScore(key, min, max, offset, count);
	}

	@Override
	public Set<String> zrevrangeByScore(String key, String max, String min) throws RedisOperationException {
		return sources.zrevrangeByScore(key, min, max);
	}

	@Override
	public Set<String> zrangeByScore(String key, String min, String max, int offset, int count) throws RedisOperationException {
		return sources.zrangeByScore(key, min, max, offset, count);
	}

	@Override
	public Set<String> zrevrangeByScore(String key, double max, double min, int offset, int count) throws RedisOperationException {
		return sources.zrevrangeByScore(key, min, max, offset, count);
	}

	@Override
	public Set<Map<String, Double>> zrangeByScoreWithScores(String key, double min, double max) throws RedisOperationException {
		return zScoresResult(sources.zrangeByScoreWithScores(key, min, max));
	}

	@Override
	public Set<Map<String, Double>> zrevrangeByScoreWithScores(String key, double max, double min) throws RedisOperationException {
		return zScoresResult(sources.zrevrangeByScoreWithScores(key, min, max));
	}

	@Override
	public Set<Map<String, Double>> zrangeByScoreWithScores(String key, double min, double max, int offset, int count) throws RedisOperationException {
		return zScoresResult(sources.zrangeByScoreWithScores(key, min, max, offset, count));
	}

	@Override
	public Set<String> zrevrangeByScore(String key, String max, String min, int offset, int count) throws RedisOperationException {
		return sources.zrevrangeByScore(key, min, max, offset, count);
	}

	@Override
	public Set<Map<String, Double>> zrangeByScoreWithScores(String key, String min, String max) throws RedisOperationException {
		return zScoresResult(sources.zrangeByScoreWithScores(key, min, max));
	}

	@Override
	public Set<Map<String, Double>> zrevrangeByScoreWithScores(String key, String max, String min) throws RedisOperationException {
		return zScoresResult(sources.zrevrangeByScoreWithScores(key, min, max));
	}

	@Override
	public Set<Map<String, Double>> zrangeByScoreWithScores(String key, String min, String max, int offset, int count) throws RedisOperationException {
		return zScoresResult(sources.zrangeByScoreWithScores(key, min, max, offset, count));
	}

	@Override
	public Set<Map<String, Double>> zrevrangeByScoreWithScores(String key, double max, double min, int offset,
			int count) throws RedisOperationException {
		return zScoresResult(sources.zrevrangeByScoreWithScores(key, min, max, offset, count));
	}

	@Override
	public Set<Map<String, Double>> zrevrangeByScoreWithScores(String key, String max, String min, int offset,
			int count) throws RedisOperationException {
		return zScoresResult(sources.zrevrangeByScoreWithScores(key, min, max, offset, count));
	}

	@Override
	public Long zremrangeByRank(String key, long start, long end) throws RedisOperationException {
		return sources.zremrangeByRank(key, start, end);
	}

	@Override
	public Long zremrangeByScore(String key, double start, double end) throws RedisOperationException {
		return sources.zremrangeByScore(key, start, end);
	}

	@Override
	public Long zremrangeByScore(String key, String start, String end) throws RedisOperationException {
		return sources.zremrangeByScore(key, start, end);
	}

	@Override
	public Long zlexcount(String key, String min, String max) throws RedisOperationException {
		return sources.zlexcount(key, min, max);
	}

	@Override
	public Set<String> zrangeByLex(String key, String min, String max) throws RedisOperationException {
		return sources.zrangeByLex(key, min, max);
	}

	@Override
	public Set<String> zrangeByLex(String key, String min, String max, int offset, int count) throws RedisOperationException {
		return sources.zrangeByLex(key, min, max, offset, count);
	}

	@Override
	public Set<String> zrevrangeByLex(String key, String max, String min) throws RedisOperationException {
		return sources.zrevrangeByLex(key, min, max);
	}

	@Override
	public Set<String> zrevrangeByLex(String key, String max, String min, int offset, int count) throws RedisOperationException {
		return sources.zrevrangeByLex(key, min, max, offset, count);
	}

	@Override
	public Long zremrangeByLex(String key, String min, String max) throws RedisOperationException {
		return sources.zremrangeByLex(key, min, max);
	}
	
	@Override
	public Long hdelByRename(String routeKey, String dataKey, String... fields) throws RedisOperationException {
		return sources.hdel(dataKey, fields);
	}

	@Override
	public String hgetByRename(String routeKey, String dataKey, String field) throws RedisOperationException {
		return sources.hget(dataKey, field);
	}

	@Override
	public Set<String> hkeysByRename(String routeKey, String dataKey) throws RedisOperationException {
		return sources.hkeys(dataKey);
	}
}
