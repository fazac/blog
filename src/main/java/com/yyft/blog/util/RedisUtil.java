package com.yyft.blog.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {
    @Autowired
    private RedisCacheManager cacheManager;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 添加缓存
     *
     * @param key   键
     * @param value 值
     */
    public void add(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 根据key获取value
     *
     * @param key key
     * @return value
     */
    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 设置失效时间
     *
     * @param key  key
     * @param time 失效时间
     * @return true|false
     */
    public boolean expire(String key, long time) {
        return redisTemplate.expire(key, time, TimeUnit.SECONDS);
    }

    /**
     * 获取失效时间
     *
     * @param key key
     * @return 剩余时间
     */
    public long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 删除key
     *
     * @param key key
     * @return true|false
     */
    public boolean delKey(String key) {
        return redisTemplate.delete(key);
    }

    /**
     * 加入多keyMap
     *
     * @param key   key
     * @param hash  hashKey
     * @param value hashValue
     * @return true|false
     */
    public boolean add(String key, String hash, String value) {
        return redisTemplate.opsForHash().putIfAbsent(key, hash, value);
    }

    /**
     * 删除 key
     *
     * @param key  key
     * @param hash hashKey
     */
    public void delKey(String key, String hash) {
        redisTemplate.opsForHash().delete(key, hash);
    }

    /**
     * 缓存添加
     *
     * @param name  缓存名
     * @param key   key
     * @param value value
     */
    public void addCache(String name, String key, String value) {
        cacheManager.getCache(name).putIfAbsent(key, value); // 已有不添加
    }

    /**
     * 获取缓存值
     *
     * @param name 缓存名
     * @param key  key
     * @return value
     */
    public String getCache(String name, String key) {
        return cacheManager.getCache(name).get(key, String.class);
    }

    /**
     * 删除缓存
     *
     * @param name name
     */
    public void clearCache(String name) {
        cacheManager.getCache(name).clear();
    }

    /**
     * 删除key缓存
     *
     * @param name 缓存名
     * @param key  key
     */
    public void delCache(String name, String key) {
        cacheManager.getCache(name).evictIfPresent(key);
    }
}
