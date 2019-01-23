# redis-helper

基于jedis封装的redis操作客户端工具包。支持单点、哨兵、cluster。配置简单，使用方便

api操作最大限度保留了redis原生命令，容易上手


# 配置使用

## 1. 导入jar包

在springboot工程pom中引入依赖: （可能需要自己下载源码打包）

        <dependency>
            <groupId>net.ewant</groupId>
            <artifactId>redis-helper</artifactId>
            <version>1.0.0-SNAPSHOT</version>
        </dependency>

## 2. application.yml 或者 properties中配置

以redis为根节点

    redis:
        # 连接模式：0 单点模式  1 哨兵模式（多个master时支持分片）  2 cluster模式
        mode: 1
        # redis 服务连接密码
        password: 123456
        # redis 连接超时时间 ms
        timeout: 10000
        # 使用第几个库，不配置默认0（只对单点模式、哨兵模式有效）
        dbIndex: 0
        # 连接池配置（Apache 对象连接池）
        pool:
            testOnBorrow: false
            testOnReturn: false
            testWhileIdle: true
            maxTotal: 50
            maxIdle: 30
        # 单节点模式的 ip 端口配置
        single:
            host: 10.41.13.141
            port: 6379
        # 哨兵模式的 ip 端口配置 列表
        sentinel:
            - master: master1
              nodes:
                - host: 192.168.1.101
                  port: 26379
            - master: master1
              nodes:
                - host: 192.168.1.102
                  port: 26379
        # 集群模式配置
        cluster:
            # 连接失败重试次数
            maxRetry: 5
            nodes:
                - host: 192.168.1.101
                  port: 6379
                - host: 192.168.1.102
                  port: 6379

# 【demo】

@Controller

public class DemoController {
    
    @Autowired
    RedisHelper redisHelper;

    @RequestMapping(value = "/demo")
    @ResponseBody
    public String demo(HttpServletRequest request){
    
        // cmd 方法没传参默认操作 配置dbIndex 那个库（参看上面配置）
        redisHelper.cmd().set("name", "lisi");
        
        System.out.println(redisHelper.cmd().get("name"));// echo  lisi
        
        redisHelper.cmd().del("name");
        
        // 显式声明操作 3 库
        redisHelper.cmd(3).hset("a", "b", "ccc");
        
        System.out.println(redisHelper.cmd(3).hget("a", "b"));// echo  ccc
        
        redisHelper.cmd(3).hdel("a", "b");

        return "demo";
    }
}
