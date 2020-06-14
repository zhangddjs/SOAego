package com.ego.redis.dao;

import com.ego.redis.dao.impl.JedisDaoImpl;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import redis.clients.jedis.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class redisTest {
    private JedisCluster jedisClients;
    private JedisPool jedisPool;
    private ShardedJedis shardedJedis;
    private ShardedJedisPool shardedJedisPool;
    @Value("${redis.bigpic.key}")
    private String key;

    public void setupCluster(){
        Set<HostAndPort> clusterNodes = new HashSet<>();
        clusterNodes.add(new HostAndPort("10.100.13.173", 36379));
        clusterNodes.add(new HostAndPort("10.100.13.173", 36380));
        clusterNodes.add(new HostAndPort("10.100.13.173", 36381));
        clusterNodes.add(new HostAndPort("10.100.13.173", 36382));
        clusterNodes.add(new HostAndPort("10.100.13.173", 36383));
        clusterNodes.add(new HostAndPort("10.100.13.173", 36384));

        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(50);//最大连接个数
        jedisPoolConfig.setMaxIdle(10);//最大空闲连接个数
        jedisPoolConfig.setMaxWaitMillis(10000);//获取连接时的最大等待毫秒数，若超时则抛异常。-1代表不确定的毫秒数
        jedisPoolConfig.setTestOnBorrow(true);//获取连接时检测其有效性
/*        JedisCluster jedisCluster = new JedisCluster(clusterNodes,15000,100,
                jedisPoolConfig);//第二个参数：超时时间     第三个参数：最大尝试重连次数*/
//        JedisCluster jc = new JedisCluster(clusterNodes);
//        this.jedisClients = jc;
        jedisClients = new JedisCluster(clusterNodes,10000, 6, jedisPoolConfig);

    }

    public void setupShards(){

        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(50);//最大连接个数

        List<JedisShardInfo> shards = new ArrayList<>();
        shards.add(new JedisShardInfo("10.100.13.173", 36379));
        shards.add(new JedisShardInfo("10.100.13.173", 36380));
        shards.add(new JedisShardInfo("10.100.13.173", 36381));
        shards.add(new JedisShardInfo("10.100.13.173", 36382));
        shards.add(new JedisShardInfo("10.100.13.173", 36383));
        shards.add(new JedisShardInfo("10.100.13.173", 36384));
        shardedJedisPool = new ShardedJedisPool(jedisPoolConfig, shards);
    }

    @Test
    @Ignore
    public void testSingle() throws InterruptedException {
        Jedis jedis = new Jedis("mats01.webex.com", 36380,1000,1000);
        jedis.exists("test");
        System.out.println(jedis.get("test"));
        jedis.set("test", "ttt");
        System.out.println(jedis.get("test"));
        Thread.sleep(2000);
        List<Object> slots = jedis.clusterSlots();
        System.out.println(slots.size());
        jedis.close();

    }

    @Test
    @Ignore
    public void testPool(){
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(128);
        JedisPool jedisPool = new JedisPool(poolConfig, "mats01.webex.com", 36380);
        Jedis jedis = jedisPool.getResource();
        System.out.println(jedis.get("test"));

    }

    @Test
    @Ignore
    public void testJedisCluster() throws IOException {
        this.setupCluster();
        jedisClients.set("hello", "100");

        String result = jedisClients.get("hello");

        // 第三步：打印结果

        System.out.println(result);

        jedisClients.exists("test");

        jedisClients.close();
    }

    @Test
    @Ignore
    public void testShardedJedisCluster() throws IOException {
        this.setupShards();
        shardedJedis = shardedJedisPool.getResource();
        shardedJedis.set("hello", "100");

        String result = shardedJedis.get("hello");

        // 第三步：打印结果

        System.out.println(result);


        shardedJedis.close();
        shardedJedisPool.close();
    }


}
