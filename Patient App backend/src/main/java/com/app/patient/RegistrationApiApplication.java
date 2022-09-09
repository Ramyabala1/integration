package com.app.patient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableCaching

public class RegistrationApiApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(RegistrationApiApplication.class, args);

	}

	@Bean
	JedisConnectionFactory jedisConnectionFactory(Environment env) {

		RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
		redisStandaloneConfiguration.setHostName("127.0.0.1");
		redisStandaloneConfiguration.setPort(6379);
		return new JedisConnectionFactory(redisStandaloneConfiguration);

	}

	@Bean
	RedisTemplate<String, Object> redisTemplate(Environment env) {
		JdkSerializationRedisSerializer jackson2JsonRedisSerializer = new JdkSerializationRedisSerializer();
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(jedisConnectionFactory(env));
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
		redisTemplate.setDefaultSerializer(jackson2JsonRedisSerializer);
		redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
		redisTemplate.setHashKeySerializer(new StringRedisSerializer());
		return redisTemplate;

	}

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

}
