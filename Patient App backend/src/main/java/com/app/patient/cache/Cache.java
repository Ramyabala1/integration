package com.app.patient.cache;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class Cache {

	private HashOperations<String, String, Object> hashOperations;
	private RedisTemplate<String, Object> redisTemplate;

	public Cache(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
		hashOperations = redisTemplate.opsForHash();
	}

	public void saveCache(String hashReference, String key, Object obj) {

		hashOperations.put(hashReference, key, obj);
	}

	public void clearCache(String hashReference, String otp) {
		hashOperations.delete(hashReference, otp);
	}

	public Object getCache(String hashReference, String key) {
		return hashOperations.get(hashReference, key);
	}

	public void setExpire(String hashReference, int time, TimeUnit Unit) {
		redisTemplate.expire(hashReference, time, Unit);
	}

}
