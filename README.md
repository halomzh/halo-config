# halo-config
## 介绍

halo-config是以redis作为配置中心应用于spring的分布式配置框架。

TODO：动态刷新

## 原理





## 开始

### 基本配置

```yaml
server:
  port: 8899
spring:
  application:
    name: example-a
  redis:
    host: 127.0.0.1
    port: 6379
    #    password:
    timeout: 60000
halo:
  config:
    enable: true
    default-name-space: example
```

### 额外配置

halo-conf-redisson.yml（文件名称固定）需要在resource目录下额外配置

```yaml
#Redisson配置
singleServerConfig:
  address: "redis://127.0.0.1:6379"
  password: null
  clientName: null
  database: 0 #选择使用哪个数据库0~15
#  idleConnectionTimeout: 10000
#  pingTimeout: 1000
#  connectTimeout: 10000
#  timeout: 3000
#  retryAttempts: 3
#  retryInterval: 1500
#  reconnectionTimeout: 3000
#  failedAttempts: 3
#  subscriptionsPerConnection: 5
#  subscriptionConnectionMinimumIdleSize: 1
#  subscriptionConnectionPoolSize: 50
#  connectionMinimumIdleSize: 32
#  connectionPoolSize: 64
#  dnsMonitoringInterval: 5000
  #dnsMonitoring: false

threads: 0
nettyThreads: 0
#codec:
#  class: "org.redisson.codec.JsonJacksonCodec"
transportMode: "NIO"
```

### 编写逻辑

```java
@SpringBootApplication
@RestController
@RequestMapping("/example")
@Slf4j
public class ExampleApp {

   @Autowired
   private RedissonClient redissonClient;

   @Value("${halo.example.name}")
   private String name;

   public static void main(String[] args) {
      SpringApplication.run(ExampleApp.class, args);
   }

   // @PostConstruct
   public void test001() {
      String propertiesValue = "halo.example.name=\"zhangsan\"\n" +
            "halo.example.age=11\n" +
            "halo.example.type=\"user\"";
      redissonClient.getBucket(HaloConfigConstant.CONFIG_KEY_PREFIX + "example" + ":" + HaloConfigConstant.PROPERTIES + ":application-example.properties").set(propertiesValue);
   }

   @GetMapping("/get")
   public String get() {
      return name;
   }
}
```

halo.example.name并不存在于application.yml中，是从redis中取出的。

![image-20210326180543322](https://raw.githubusercontent.com/halomzh/pic/master/20210326180551.png)

### 测试能否成功获取配置

![image-20210326180813574](https://raw.githubusercontent.com/halomzh/pic/master/20210326180815.png)