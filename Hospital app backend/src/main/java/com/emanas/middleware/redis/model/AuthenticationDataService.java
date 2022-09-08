package com.emanas.middleware.redis.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

//@Service
public class AuthenticationDataService {

	private final AuthenticationDataDao authenticationData;

	@Autowired
	public AuthenticationDataService(@Qualifier("AuthData") AuthenticationDataDao authenticationData) {
		this.authenticationData = authenticationData;
	}

	public void saveAuth(String demogTransID, AuthenticationData ad) {
		authenticationData.saveAuthenticationData(demogTransID, ad);
	}

	public AuthenticationData getOneAuthenticationData(String demogTransID) {
		return authenticationData.getOneAuthenticationData(demogTransID);
	}
}
