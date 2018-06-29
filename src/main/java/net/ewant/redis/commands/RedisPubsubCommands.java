package net.ewant.redis.commands;

import java.util.List;
import java.util.Map;

public interface RedisPubsubCommands {

	void psubscribe(final String... patterns);
	
	void punsubscribe(final String... patterns);
	
	/**
	 * @param channel
	 * @param message
	 * @return 收到消息的客户端数量。
	 */
	Long publish(final String channel, final String message);
	
	/**
	 * 根据表达式获取所有发布订阅的频道
	 * @param pattern
	 * @return  a list of active channels, optionally matching the specified pattern.
	 */
	List<String> pubsubChannels(final String pattern);

	/**
	 * 获取通过 punsubscribe 订阅的频道模式（表达式pattern）数量
	 * @return
	 */
	Long pubsubNumPat();

	/**
	 * 获取指定频道的订阅数
	 * @param channels
	 * @return
	 */
	Map<String, String> pubsubNumSub(final String... channels);
	
	void subscribe(final String... channels);
	
	void unsubscribe(final String... channels);
}
