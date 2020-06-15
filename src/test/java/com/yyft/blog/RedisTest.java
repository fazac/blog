package com.yyft.blog;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = BlogApplication.class)
@WebAppConfiguration
public class RedisTest {
    @Autowired
    private RedisCacheManager redisCacheManager;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    public void testPut() {
        redisTemplate.opsForValue().set("aa", "aaaa");
        redisTemplate.opsForHash().putIfAbsent("testhash", "aa", "bb");
        System.out.println(redisTemplate.opsForValue().get("aa"));
        System.out.println(redisTemplate.opsForHash().get("testhash", "aa"));
        Cache cache = redisCacheManager.getCache("vv");
        cache.put("bb", "cc");
        cache.put("xx", "cc");
        System.out.println(redisCacheManager.getCache("vv").get("bb", String.class));
        redisTemplate.delete("aa");
        cache.evictIfPresent("bb");
        redisTemplate.opsForHash().delete("testhash", "aa");
//        cache.clear();
    }

}
