package net.ewant.redis.commands;

import java.util.List;

public interface RedisScriptingCommands {

	Object eval(String script);
	
	Object eval(String script, List<String> keys, List<String> args);
	
	Object evalsha(String sha1);
	
	Object evalsha(String sha1, List<String> keys, List<String> args);
	
	boolean scriptExists(String sha1);
	
	List<Boolean> scriptExists(String... sha1);
	
	/**
	 * 清除所有 Lua 脚本缓存
	 * @return 总是返回 OK
	 */
	boolean scriptFlush();
	
	/**
	 * 杀死当前正在运行的 Lua 脚本，当且仅当这个脚本没有执行过任何写操作时，这个命令才生效。
	 * @return 执行成功返回 OK ，否则返回一个错误。
	 */
	String scriptKill();
	
	/**
	 * @param script
	 * @return sha1 code
	 */
	String scriptLoad(String script);
}
