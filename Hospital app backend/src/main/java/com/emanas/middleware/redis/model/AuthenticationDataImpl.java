//package com.emanas.middleware.redis.model;
//
//import java.util.concurrent.TimeUnit;
//
//import javax.annotation.Resource;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.data.redis.core.HashOperations;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Repository;
//
//import com.emanas.middleware.utility.cache.redis.Cache;
//
//@Repository()
//public class AuthenticationDataImpl implements AuthenticationDataDao{
//
////	@Autowired
////	@Qualifier("cache") Cache cacheServ;
//
//	private final String hashReference= "AuthData";
//
////	@Resource(name="redisTemplate")          // 'redisTemplate' is defined as a Bean in AppConfig.java
////    private HashOperations<String, Integer, AuthenticationData> hashOperations;
////
////	private Cache cacheServ ;
////
////
////
////	public AuthenticationDataImpl(Cache cacheServ) {
////	super();
////	this.cacheServ = cacheServ;
////}
//
//
//	private RedisTemplate<String, Object> redisTemplate;
//
//    private HashOperations hashOperations;
//
//
//    public AuthenticationDataImpl(RedisTemplate<String, Object> redisTemplate) {
//        this.redisTemplate = redisTemplate;
//
//        hashOperations = redisTemplate.opsForHash();
//    }
//
//
//
//	@Override
//	public void saveAuthenticationData(String demogTransID, AuthenticationData ad) {
//
////		 hashOperations.putIfAbsent(hashReference,Integer.valueOf(demogTransID), ad);
//
//		redisTemplate.opsForHash().put(hashReference,demogTransID,ad);
//
//		//cacheServ.saveCache(hashReference,demogTransID,ad);
//		redisTemplate.expire(hashReference, 10, TimeUnit.SECONDS);
//
//
//
//
//	}
//
//	@Override
//	public void deleteAuthenticationData(String demogTransID) {
//
//		redisTemplate.opsForHash().delete(hashReference, demogTransID);
//	}
//
//
//	@Override
//	public AuthenticationData getOneAuthenticationData(String demogTransID) {
//		// TODO Auto-generated method stub
//
//			// TODO Auto-generated method stub
//		return (AuthenticationData) redisTemplate.opsForHash().get(hashReference, demogTransID);
//
//	}
//}
