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

    @Test
    @Ignore
    public void testt(){
        Solution s = new Solution();
        s.nthUglyNumber(1389);
    }

    class Solution {
        public int nthUglyNumber(int n) {
            if(n <= 6) return n;
            int count = 6;
            int Ugly = 6;
            PreUgly preUgly = new PreUgly();
            List<Integer> L1 = preUgly.L1;
            List<Integer> L2 = preUgly.L2;
            List<Integer> L3 = preUgly.L3;
            List<List<Integer>> L = preUgly.L;
            int[] i = preUgly.i;
            int[] j = preUgly.j;
            while(count++ != n){
                if(count == 1388){
                    System.out.println(1388);
                }
                Ugly = getMin(L1.get(L1.size() - 1), L2.get(L2.size() - 1), L3.get(L3.size() - 1));
                if(L1.get(L1.size() - 1) == Ugly){
                    int z1 = L1.get(i[0]) * 2;
                    int z2 = L2.get(i[1]) * 2;
                    int z3 = i[2] >= L3.size() ? Integer.MAX_VALUE - 1 : L3.get(i[2]) * 2;
                    L1.add(getMin(z1, z2, z3));
                    updateIndex(i, L, L1, 2);
                } else if(L2.get(L2.size() - 1) == Ugly){
                    int z1 = L2.get(j[0]) * 3;
                    int z2 = L3.get(j[1]) * 3;
                    L2.add(Math.min(z1, z2));
                    updateIndex(j, L, L2, 3);
                } else if(L3.get(L3.size() - 1) == Ugly){
                    L3.add(L3.get(L3.size() - 1) * 5);
                }
            }
            return Ugly;
        }

        public int getMin(int a, int b, int c){
            return Math.min(a, Math.min(b, c));
        }

        public void updateIndex(int[] index, List<List<Integer>> L, List<Integer> obj, int factor){
            int flag = index.length == 3 ? 0 : 1;
            for(int i = 0; i < index.length; i++){
                if(index[i] < L.get(i + flag).size()) index[i] = obj.get(obj.size() - 1) == L.get(i + flag).get(index[i]) * factor ? index[i] + 1 : index[i];
            }
        }

        class PreUgly{
            List<Integer> L1;
            List<Integer> L2;
            List<Integer> L3;
            List<List<Integer>> L;
            int [] i;
            int [] j;
            public PreUgly(){
                L1 = new ArrayList<Integer>();
                L2 = new ArrayList<Integer>();
                L3 = new ArrayList<Integer>();
                L = new ArrayList<List<Integer>>();
                i = new int[3];
                j = new int[2];
                init();
            }

            public void init(){
                L1.add(2);
                L1.add(4);
                L1.add(6);
                L1.add(8);
                L2.add(3);
                L2.add(9);
                L3.add(5);
                L3.add(25);
                L.add(L1);
                L.add(L2);
                L.add(L3);
                i[0] = 2;
                i[1] = 1;
                i[2] = 0;
                j[0] = 1;
                j[1] = 0;
            }
        }
    }

    @Test
    @Ignore
    public void testarray( int[] arr, int target){

        int low = 0, high = arr.length - 1, mid, protate;
        while(low < high) {
            mid = low + (high - low) / 2;
            if(arr[mid] < arr[low]) high = mid;
            else if(arr[mid] > arr[high]) low = mid + 1;
        }
        protate = high;


    }


}
