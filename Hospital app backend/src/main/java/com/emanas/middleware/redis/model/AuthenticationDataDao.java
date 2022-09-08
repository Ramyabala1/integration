package com.emanas.middleware.redis.model;

public interface AuthenticationDataDao {

	void saveAuthenticationData(String demogTransID, AuthenticationData ad);

	AuthenticationData getOneAuthenticationData(String demogTransID);

	void deleteAuthenticationData(String demogTransID);
}
