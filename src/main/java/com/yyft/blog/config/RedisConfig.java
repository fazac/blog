package com.yyft.blog.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.yyft.blog.entity.Constants;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;


@Configuration
public class RedisConfig extends CachingConfigurerSupport {

    /**
     * 往容器中添加RedisTemplate对象，设置序列化方式
     *
     * @param redisConnectionFactory factory
     * @return redisTemplate
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(valueSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(valueSerializer());
        template.afterPropertiesSet();
        return template;
    }

    /**
     * 往容器中添加RedisCacheManager容器，并设置序列化方式
     *
     * @param redisConnectionFactory factory
     * @return redisCacheManager
     */
    @Bean
    public RedisCacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory);
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofHours(Constants.CACHE_EXPIRE_DAY))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(valueSerializer()))
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()));
        return new RedisCacheManager(redisCacheWriter, redisCacheConfiguration);
    }


//    /**
//     * 往容器中添加org.springframework.data.redis.cache.RedisCacheConfiguration 对象
//     * 目的是为了向默认的RedisCacheManager中设置属性，当然包括序列化
//     * 如果仅仅是为了设置序列化方式可以和上面的配置二选一
//     * 在RedisCacheManager内部使用org.springframework.data.redis.cache.RedisCacheConfiguration去保存相关配置信息
//     */
//     @Bean
//     public org.springframework.data.redis.cache.RedisCacheConfiguration determineConfiguration() {
//               CacheProperties.Redis redisProperties = this.cacheProperties.getRedis();
//             org.springframework.data.redis.cache.RedisCacheConfiguration config = org.springframework.data.redis.cache.RedisCacheConfiguration
//                     .defaultCacheConfig();
//              config = config.serializeValuesWith(RedisSerializationContext.SerializationPair
//                                .fromSerializer(valueSerializer()));
//                if (redisProperties.getTimeToLive() != null) {
//                     config = config.entryTtl(redisProperties.getTimeToLive());
//                }
//                if (redisProperties.getKeyPrefix() != null) {
//                       config = config.prefixKeysWith(redisProperties.getKeyPrefix());
//                }if (!redisProperties.isCacheNullValues()) {
//                        config = config.disableCachingNullValues();
//                   }
//                if (!redisProperties.isUseKeyPrefix()) {
//                         config = config.disableKeyPrefix();
//                    }
//               return config;
//           }

    /**
     * 使用Jackson序列化器
     *
     * @return redisSerializer
     */
    private RedisSerializer<Object> valueSerializer() {
        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
//         * 这一句必须要，作用是序列化时将对象全类名一起保存下来
//         * 设置之后的序列化结果如下：
//         *  [
//         *   "com.dxy.cache.pojo.Dept",
//         *   {
//         *     "pid": 1,
//         *     "code": "11",
//         *     "name": "财务部1"
//         *   }
//         * ]
//         *
//         * 不设置的话，序列化结果如下，将无法反序列化
//         *
//         *  {
//         *     "pid": 1,
//         *     "code": "11",
//         *     "name": "财务部1"
//         *   }
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//                objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);
        serializer.setObjectMapper(objectMapper);
        return serializer;
    }


//    @Bean
//    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
//
//        RedisTemplate<String, Object> template = new RedisTemplate<>();
//        // 配置连接工厂
//        template.setConnectionFactory(factory);
//
//        //使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值（默认使用JDK的序列化方式）
//        Jackson2JsonRedisSerializer<Object> jacksonSerial = new Jackson2JsonRedisSerializer<>(Object.class);
//
//        ObjectMapper om = new ObjectMapper();
//        // 指定要序列化的域，field,get和set,以及修饰符范围，ANY是都有包括private和public
//        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        // 指定序列化输入的类型，类必须是非final修饰的，final修饰的类，比如String,Integer等会跑出异常
//        // om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL); 方法已失效
//        om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);
//        jacksonSerial.setObjectMapper(om);
//
//        // 值采用json序列化
//        template.setValueSerializer(jacksonSerial);
//        //使用StringRedisSerializer来序列化和反序列化redis的key值
//        template.setKeySerializer(new StringRedisSerializer());
//
//        // 设置hash key 和value序列化模式
//        template.setHashKeySerializer(new StringRedisSerializer());
//        template.setHashValueSerializer(jacksonSerial);
//        template.afterPropertiesSet();
//
//        return template;
//    }
}
