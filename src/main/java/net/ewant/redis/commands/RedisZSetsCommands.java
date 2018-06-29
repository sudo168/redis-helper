package net.ewant.redis.commands;

import java.util.Map;
import java.util.Set;

public interface RedisZSetsCommands {

	  Long zadd(String key, double score, String member) throws Exception;
	  Long zaddXx(String key, double score, String member) throws Exception;
	  Long zaddNx(String key, double score, String member) throws Exception;
	  Long zaddCh(String key, double score, String member) throws Exception;

	  Long zadd(String key, Map<String, Double> scoreMembers) throws Exception;
	  Long zaddXx(String key, Map<String, Double> scoreMembers) throws Exception;
	  Long zaddNx(String key, Map<String, Double> scoreMembers) throws Exception;
	  Long zaddCh(String key, Map<String, Double> scoreMembers) throws Exception;

	  Set<String> zrange(String key, long start, long end) throws Exception;

	  Long zrem(String key, String... members) throws Exception;

	  Double zincrby(String key, double score, String member) throws Exception;
	  Double zincrbyNx(String key, double score, String member) throws Exception;
	  Double zincrbyXx(String key, double score, String member) throws Exception;

	  Long zrank(String key, String member) throws Exception;

	  Long zrevrank(String key, String member) throws Exception;

	  Set<String> zrevrange(String key, long start, long end) throws Exception;

	  Set<Map<String, Double>> zrangeWithScores(String key, long start, long end) throws Exception;

	  Set<Map<String, Double>> zrevrangeWithScores(String key, long start, long end) throws Exception;

	  Long zcard(String key) throws Exception;

	  Double zscore(String key, String member) throws Exception;
	  
	  Long zcount(String key, double min, double max) throws Exception;

	  Long zcount(String key, String min, String max) throws Exception;

	  Set<String> zrangeByScore(String key, double min, double max) throws Exception;

	  Set<String> zrangeByScore(String key, String min, String max) throws Exception;

	  Set<String> zrevrangeByScore(String key, double max, double min) throws Exception;

	  Set<String> zrangeByScore(String key, double min, double max, int offset, int count) throws Exception;

	  Set<String> zrevrangeByScore(String key, String max, String min) throws Exception;

	  Set<String> zrangeByScore(String key, String min, String max, int offset, int count) throws Exception;

	  Set<String> zrevrangeByScore(String key, double max, double min, int offset, int count) throws Exception;

	  Set<Map<String, Double>> zrangeByScoreWithScores(String key, double min, double max) throws Exception;

	  Set<Map<String, Double>> zrevrangeByScoreWithScores(String key, double max, double min) throws Exception;

	  Set<Map<String, Double>> zrangeByScoreWithScores(String key, double min, double max, int offset, int count) throws Exception;

	  Set<String> zrevrangeByScore(String key, String max, String min, int offset, int count) throws Exception;

	  Set<Map<String, Double>> zrangeByScoreWithScores(String key, String min, String max) throws Exception;

	  Set<Map<String, Double>> zrevrangeByScoreWithScores(String key, String max, String min) throws Exception;

	  Set<Map<String, Double>> zrangeByScoreWithScores(String key, String min, String max, int offset, int count) throws Exception;

	  Set<Map<String, Double>> zrevrangeByScoreWithScores(String key, double max, double min, int offset, int count) throws Exception;

	  Set<Map<String, Double>> zrevrangeByScoreWithScores(String key, String max, String min, int offset, int count) throws Exception;

	  Long zremrangeByRank(String key, long start, long end) throws Exception;

	  Long zremrangeByScore(String key, double start, double end) throws Exception;

	  Long zremrangeByScore(String key, String start, String end) throws Exception;

	  Long zlexcount(final String key, final String min, final String max) throws Exception;

	  Set<String> zrangeByLex(final String key, final String min, final String max) throws Exception;

	  Set<String> zrangeByLex(final String key, final String min, final String max, final int offset,
                              final int count) throws Exception;

	  Set<String> zrevrangeByLex(final String key, final String max, final String min) throws Exception;

	  Set<String> zrevrangeByLex(final String key, final String max, final String min,
                                 final int offset, final int count) throws Exception;

	  Long zremrangeByLex(final String key, final String min, final String max) throws Exception;
}
