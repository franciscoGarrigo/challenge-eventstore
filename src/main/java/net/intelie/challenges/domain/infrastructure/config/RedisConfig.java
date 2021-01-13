package net.intelie.challenges.domain.infrastructure.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

import net.intelie.challenges.domain.model.Event;

@EnableRedisRepositories
@EnableCaching
@Configuration
public class RedisConfig {	

    @Bean
    public RedisTemplate<String, Event> redisTemplate(final LettuceConnectionFactory connectionFactory) {
        RedisTemplate<String, Event> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        template.setKeySerializer(RedisSerializer.string());
        template.setValueSerializer(RedisSerializer.json());
        template.setHashKeySerializer(RedisSerializer.string());
        template.setHashValueSerializer(RedisSerializer.json());

        return template;
    }

    @Bean
    public CacheManager cacheManager(LettuceConnectionFactory connectionFactory) {
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()// using the default configurations of cache because a single instance of redis was enough
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.string()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.json()));// the values are cached in json format to facilitate the conversion to Event object
        redisCacheConfiguration.usePrefix();

        return RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(connectionFactory)
                .cacheDefaults(redisCacheConfiguration).build();
    }
}
