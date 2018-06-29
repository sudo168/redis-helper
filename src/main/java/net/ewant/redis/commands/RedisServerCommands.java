package net.ewant.redis.commands;

public interface RedisServerCommands {
	
	  String bgsave();

	  String bgrewriteaof();

	  /**
	   * @param host
	   * @param port
	   * @return OK if the connection exists and has been closed
	   */
	  boolean clientKill(final String host, final int port);
	  
	  /**
	   * @param filterValue 
	   * 1. CLIENT KILL ADDR ip:port
	   * 2. CLIENT KILL ID client-id
	   * 3. CLIENT KILL TYPE type, 这里的 type 可以是 normal, slave, pubsub
	   * 4. CLIENT KILL SKIPME yes/no. By default this option is set to yes
	   * @return the number of clients killed
	   */
	  Long clientKillWithFilter(final String filterValue);
	  
	  /**
	   * id=12328 addr=192.168.203.2:7816 fd=16 name= age=3086 idle=5 flags=N db=10 sub=0 psub=0 multi=-1 qbuf=0 qbuf-free=0 obl=0 oll=0 omem=0 events=r cmd=hgetall
	   * @return 
	   * 每个已连接客户端对应一行（以 LF 分割）
	   *   每行字符串由一系列 属性=值（property=value） 形式的域组成，每个域之间以空格分开
	   */
	  String clientList();
	  
	  String save();

	  Long lastsave();

	  String shutdown();

	  String info();

	  String info(final String section);

	  String slaveof(final String host, final int port);

	  String slaveofNoOne();

	  Long time();
	  
}
