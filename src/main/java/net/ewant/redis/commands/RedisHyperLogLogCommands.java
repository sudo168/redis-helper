package net.ewant.redis.commands;

public interface RedisHyperLogLogCommands {

	String pfmerge(final String destkey, final String... sourcekeys);

	long pfcount(final String... keys);
	
	Long pfadd(final String key, final String... elements);

}
