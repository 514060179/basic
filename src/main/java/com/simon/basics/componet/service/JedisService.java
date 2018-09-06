package com.simon.basics.componet.service;

import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * @author fengtianying
 * @date 2018/9/6 15:36
 */
public interface JedisService {

    Jedis getJedis();

    void returnResource(final Jedis jedis);

    void put(String key, Object value);

    void put(String key, String value);

    void put(String key, Object value, int expritime);

    <T> T getObject(String key, Class<T> c);

    String getString(String key);

    List<Object> getList(String key);
}
