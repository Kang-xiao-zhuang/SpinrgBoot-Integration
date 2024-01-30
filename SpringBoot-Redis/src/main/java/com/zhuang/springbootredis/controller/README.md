# 固定时间窗口算法

- 固定时间窗口算法也可以叫做简单计数算法。网上有很多都将计数算法单独抽离出来。但是笔者认为计数算法是一种思想，而固定时间窗口算法是他的一种实现
- 包括下面滑动时间窗口算法也是计数算法的一种实现。因为计数如果不和时间进行绑定的话那么失去了限流的本质了。就变成了拒绝了

![在这里插入图片描述](https://img-blog.csdnimg.cn/direct/d30bfcea06dc4249bc0637b254c95631.png)

## 优点

- 在固定的时间内出现流量溢出可以立即做出限流。每个时间窗口不会相互影响
- 在时间单元内保障系统的稳定。保障的时间单元内系统的吞吐量上限

## 缺点

- 正如图示一样，他的最大问题就是临界状态。在临界状态最坏情况会受到两倍流量请求
- 除了临界的情况，还有一种是在一个单元时间窗内前期如果很快的消耗完请求阈值。那么剩下的时间将会无法请求。这样就会因为一瞬间的流量导致一段时间内系统不可用。这在互联网高可用的系统中是不能接受的。

## 实现

```java
	@Autowired
    private StringRedisTemplate redisTemplate;

    // 固定时间窗口算法
    @GetMapping("/start")
    public Map<String, Object> start(@RequestParam Map<String, Object> paramMap) {
        //根据前端传递的qps上线
        int times = 100;
        if (paramMap.containsKey("times")) {
            times = Integer.parseInt(paramMap.get("times").toString());
        }
        String redisKey = "redisQps";
        RedisAtomicInteger redisAtomicInteger = new RedisAtomicInteger(redisKey, Objects.requireNonNull(redisTemplate.getConnectionFactory()));
        int no = redisAtomicInteger.getAndIncrement();
        //设置时间固定时间窗口长度 1S
        if (no == 0) {
            redisAtomicInteger.expire(1, TimeUnit.SECONDS);
        }
        //判断是否超限  time=2 表示qps=3
        log.info("no值->{}",no);
        if (no > times) {
            throw new RuntimeException("qps refuse request");
        }
        log.info("times值->{}",times);
        //返回成功告知
        Map<String, Object> map = new HashMap<>();
        map.put("success", "success");
        return map;
    }
```

## 测试

![在这里插入图片描述](https://img-blog.csdnimg.cn/direct/a16e122ae1c74460a6f48f53e13b685a.png)

![在这里插入图片描述](https://img-blog.csdnimg.cn/direct/8f8da262a3be4dd98459362c9375b02c.png)

![在这里插入图片描述](https://img-blog.csdnimg.cn/direct/52e3bde829004b60859c706fc859aff5.png)

# 滑动时间窗口算法

滑动时间窗口算法是一种限流算法，其原理是记录一段时间内的请求数量，并根据时间窗口的滑动来判断是否接受新的请求。该算法通过维护一个滑动窗口来记录时间窗口内的请求数量，并根据窗口的大小和限流阈值来决定是否接受新的请求。

滑动时间窗口算法的实现方式相对复杂一些，但相对于固定时间窗口算法更加灵活和精确。在滑动时间窗口算法中，时间窗口是不断滑动的，每个窗口的大小和起点可以根据实际需求进行配置。同时，该算法也支持动态调整限流阈值和流量控制粒度，能够更好地应对流量波动和突发请求的情况。

滑动时间窗口算法的核心思想是通过维护一个时间窗口内的请求计数器，并根据时间窗口的滑动来判断是否接受新的请求。具体来说，当一个新的请求到达时，算法会根据当前时间点判断该请求属于哪个时间窗口，并更新对应窗口的计数器。如果计数器已经达到了限流阈值，则拒绝该请求；否则，接受该请求。随着时间的推移，时间窗口会不断滑动，并更新计数器的值。

滑动时间窗口算法相对于固定时间窗口算法更加灵活和精确，能够更好地应对流量波动和突发请求的情况。同时，该算法也支持动态调整限流阈值和流量控制粒度，能够更好地满足实际需求。在实际应用中，需要根据具体情况选择适合的限流算法来控制流量。

## 优点

滑动时间窗口算法的优点主要包括以下几点：

1. 灵活性和可配置性强：滑动时间窗口算法的时间窗口大小和起点可以根据实际需求进行配置，同时限流阈值也可以动态调整，这使得算法能够更好地应对流量波动和突发请求的情况。
2. 精度高：滑动时间窗口算法相对于固定时间窗口算法更加精确，因为它考虑了时间窗口内的请求数量和时间点，能够更好地反映请求的实际情况。
3. 支持多维度限流：滑动时间窗口算法可以支持多维度的限流，例如根据IP地址、用户ID、接口等不同维度进行限流，这使得算法能够更加精细地控制流量。

## 缺点

然而，滑动时间窗口算法也存在一些缺点：

1. 实现复杂度高：滑动时间窗口算法的实现相对复杂，需要维护一个滑动窗口来记录时间窗口内的请求数量，并不断更新窗口的值，这需要耗费更多的计算和存储资源。
2. 内存占用大：由于滑动时间窗口算法需要记录每个时间窗口内的请求数量和时间点，因此需要占用更多的内存资源。
3. 无法平滑地实现请求流量的控制：滑动时间窗口算法只能通过控制时间段来控制请求总量，无法平滑地实现请求流量的控制，这可能会影响到用户体验和系统的稳定性。

## 实现

- 滑动时间窗口是将时间更加细化，上面我们是通过redis#setnx实现的。这里我们就无法通过他统一记录了。我们应该加上更小的时间单元存储到一个集合汇总。然后根据集合的总量计算限流。redis的zsett数据结构就和符合我们的需求。
- 为什么选择zset呢，因为redis的zset中除了值以外还有一个权重。会根据这个权重进行排序。如果我们将我们的时间单元及时间戳作为我们的权重，那么我们获取统计的时候只需要按照一个时间戳范围就可以了。
- 因为zset内元素是唯一的，所以我们的值采用uuid或者雪花算法一类的id生成器

```java
// 滑动时间窗口算法
    @GetMapping("/startList")
    public Map<String, Object> startList(@RequestParam Map<String, Object> paramMap) {
        String redisKey = "qpsZset";
        Integer times = 100;
        if (paramMap.containsKey("times")) {
            times = Integer.valueOf(paramMap.get("times").toString());
        }
        long currentTimeMillis = System.currentTimeMillis();
        long interMills = 10000L;
        Long count = redisTemplate.opsForZSet().count(redisKey, currentTimeMillis - interMills, currentTimeMillis);
        // 检查QPS（Queries Per Second）是否超过限制
        /**
         * 使用System.currentTimeMillis()获取当前时间戳。
         * 设置一个时间间隔interMills为1000毫秒（即1秒）。
         * 使用redisTemplate.opsForZSet().count(redisKey, currentTimeMillis - interMills, currentTimeMillis)查询过去1秒内Redis ZSet中指定键（redisKey）的数量。这个数量会被赋值给count变量。
         * 如果count的值大于times，则抛出一个运行时异常，表示QPS超过限制
         */
        if (count > times) {
            throw new RuntimeException("qps refuse request");
        }
        redisTemplate.opsForZSet().add(redisKey, UUID.randomUUID().toString(), currentTimeMillis);
        Map<String, Object> map = new HashMap<>();
        map.put("success", "success");
        return map;
    }
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/direct/b462a71a9a5f4c188d43256a4ed183a2.png)

# 漏桶算法

漏桶算法是一种常用于流量控制和限流的算法，其核心思想是将突发流量整形以便为网络提供一个稳定的流量。

漏桶算法可以看作是一个带有常量服务时间的单服务器队列，如果漏桶（包缓存）溢出，那么数据包会被丢弃。在网络中，漏桶算法可以控制端口的流量输出速率，平滑网络上的突发流量，实现流量整形，从而为网络提供一个稳定的流量。

漏桶算法的实现相对简单，不需要复杂的数据结构或计算，易于理解和部署。其优点包括平滑处理流入系统的请求，以恒定的速率将请求处理释放，从而避免了突发请求对系统的冲击；控制精度高，可以通过调整漏桶的出水速率来精确地控制请求的处理速度；适用于需要平滑处理流量和避免突发请求的场景，例如网络流量控制、防止DDOS攻击等。

然而，漏桶算法也存在一些缺点，例如请求延迟，可能导致某些请求的响应时间变长；不适用于实时性要求高的场景，因为请求需要按照恒定速率进行处理，无法满足即时性要求；无法应对突发流量，因为漏桶的出水速率是固定的，无法根据流量的变化进行动态调整。

![在这里插入图片描述](https://img-blog.csdnimg.cn/direct/52370732840749c3bb49266cadaf3e89.png)

## 优点

- 面对限流更加的柔性，不在粗暴的拒绝。
- 增加了接口的接收性
- 保证下流服务接收的稳定性。均匀下发

## 实现

```java
    // 漏桶算法
    @GetMapping("/startLoutong")
    public Map<String, Object> startLoutong(@RequestParam Map<String, Object> paramMap) {
        String redisKey = "qpsList";
        Integer times = 100;
        if (paramMap.containsKey("times")) {
            times = Integer.valueOf(paramMap.get("times").toString());
        }
        Long size = redisTemplate.opsForList().size(redisKey);
        // 检查队列长度是否超过限制
        if (size >= times) {
            throw new RuntimeException("qps refuse request");
        }
        // 添加请求到队列Redis列表的右侧。
        Long aLong = redisTemplate.opsForList().rightPush(redisKey, String.valueOf(paramMap));
        if (aLong > times) {
            //为了防止并发场景。这里添加完成之后也要验证。  即使这样本段代码在高并发也有问题。此处演示作用
            // 修剪Redis列表，使其长度为times
            redisTemplate.opsForList().trim(redisKey, 0, times - 1);
            throw new RuntimeException("qps refuse request");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("success", "success");
        return map;
    }
```

模拟消费

```java
@Component
@Slf4j
public class SchedulerTask {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Scheduled(cron = "*/5 * * * * ?")
    private void process() {
        //一次性消费两个
        log.info("正在消费。。。。。。");
        String redisKey = "qpsList";
        redisTemplate.opsForList().trim(redisKey, 2, -1);
    }

}
```

## 测试

![在这里插入图片描述](https://img-blog.csdnimg.cn/direct/8e231d57ede04d8ea81baa1a8889be76.png)

# 令牌桶算法

- 令牌桶和漏桶法是一样的。只不过将桶的作用方向改变了一下。
- 漏桶的出水速度是恒定的，如果流量突然增加的话我们就只能拒绝入池
- 但是令牌桶是将令牌放入桶中，我们知道正常情况下令牌就是一串字符当桶满了就拒绝令牌的入池，但是面对高流量的时候正常加上我们的超时时间就留下足够长的时间生产及消费令牌了。这样就尽可能的不会造成请求的拒绝
- 最后，不论是对于令牌桶拿不到令牌被拒绝，还是漏桶的水满了溢出，都是为了保证大部分流量的正常使用，而牺牲掉了少部分流量

## 实现

```java
    // 令牌桶算法
    @GetMapping("/startLingpaitong")
    public Map<String, Object> startLingpaitong(Map<String, Object> paramMap) {
        String redisKey = "lingpaitong";
        String token = redisTemplate.opsForList().leftPop(redisKey);
        //正常情况需要验证是否合法，防止篡改
        if (StringUtils.isEmpty(token)) {
            throw new RuntimeException("令牌桶拒绝");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("success", "success");
        return map;
    }
```

