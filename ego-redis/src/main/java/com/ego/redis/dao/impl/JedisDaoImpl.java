package com.ego.redis.dao.impl;

import com.ego.redis.dao.JedisDao;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.JedisCluster;

import javax.annotation.Resource;

@Repository
public class JedisDaoImpl implements JedisDao {
    @Resource
    private JedisCluster jedisClients;      //从配置文件中定义的
    @Override
    public Boolean exists(String key) {
        return jedisClients.exists(key);
    }

    @Override
    public Long del(String key) {
        return jedisClients.del(key);
    }

    @Override
    public String set(String key, String value) {
        return jedisClients.set(key, value);
    }

    @Override
    public String get(String key) {
        return jedisClients.get(key);
    }
}
