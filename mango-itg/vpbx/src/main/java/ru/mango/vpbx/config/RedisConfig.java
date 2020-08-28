package ru.mango.vpbx.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfig {

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
    //    configuration.setHostName("vpbx-app-redis-master");
        configuration.setHostName(System.getenv().get("REDIS_HOST"));
        configuration.setPort(Integer.parseInt(System.getenv().get("REDIS_PORT")));
        configuration.setDatabase(Integer.parseInt(System.getenv().get("REDIS_DATABASE")));
        return new LettuceConnectionFactory(configuration);
    }

    @Bean
    public RedisTemplate redisTemplate(LettuceConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setHashValueSerializer(new JsonRedisSerializer());
        template.setValueSerializer(new JsonRedisSerializer());
        return template;
    }

}
