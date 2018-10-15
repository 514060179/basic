package com.simon.basics.componet.service.impl;

import com.simon.basics.componet.service.JedisService;
import com.simon.basics.util.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.Map;

/**
 * @author fengtianying
 * @date 2018/9/6 15:36
 */
@Service
public class JedisServiceImpl implements JedisService {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private JedisPool jedisPool;

    @Override
    public Jedis getJedis() {
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
        }catch (Exception e){
            logger.error("获取redis连接失败！",e);
            e.printStackTrace();
        }finally {
            if (jedis == null) {
                logger.info("获取redis连接失败,重试一次{}");
                jedis = jedisPool.getResource();
            }
        }
        return jedis;
    }

    @Override
    public void returnResource(Jedis jedis) {
        if (jedis!=null&&jedisPool!=null){
            jedis.close();
        }
    }

    @Override
    public void put(String key, Object value) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.set(key, JSONUtil.objectToJson(value));
        } finally {
            returnResource(jedis);
        }
    }

    @Override
    public void put(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.set(key, value);
        } finally {
            returnResource(jedis);
        }
    }

    @Override
    public void put(String key, Object value, int expritime) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.set(key, JSONUtil.objectToJson(value));
            if (-1 != expritime) {
                jedis.expire(key, expritime);
            }
        } finally {
            returnResource(jedis);
        }
    }

    @Override
    public <T> T getObject(String key, Class<T> c) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            if (jedis.exists(key)) {
                String value = jedis.get(key);
                if (null != value) {
                    return JSONUtil.jsonToObject(value, c);
                } else {
                    return null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            returnResource(jedis);
        }
        return null;
    }

    @Override
    public String getString(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            if (jedis.exists(key)) {
                return jedis.get(key);
            }
        } finally {
            returnResource(jedis);
        }
        return null;
    }

    @Override
    public List<Object> getList(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            if (jedis.exists(key)) {
                return JSONUtil.jsonToList(getString(key));
            }
        } finally {
            returnResource(jedis);
        }
        return null;
    }

    @Override
    public boolean exists(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.exists(key);
        } finally {
            returnResource(jedis);
        }
    }

    @Override
    public String mapPut(String key, Map<String,String> map) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.hmset(key,map);
        } finally {
            returnResource(jedis);
        }
    }
    @Override
    public Long mapSet(String key, String field,String value) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.hset(key,field,value);
        } finally {
            returnResource(jedis);
        }
    }
    @Override
    public boolean mapExists(String key,String field) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.hexists(key,field);
        } finally {
            returnResource(jedis);
        }
    }

    @Override
    public String mapGet(String key,String field) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.hget(key,field);
        } finally {
            returnResource(jedis);
        }
    }
}
