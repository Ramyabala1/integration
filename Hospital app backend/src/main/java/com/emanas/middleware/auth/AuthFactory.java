package com.emanas.middleware.auth;

import org.springframework.stereotype.Component;

import com.emanas.middleware.Demog.DemogServiceType;

@Component
public class AuthFactory {
	public AuthFactory() {
	}
	
	public static AuthService selectAuthService(DemogServiceType service) {
		AuthService authService=null;
		switch (service) {
			case DEFAULT:
				authService= new EManasAuthService();
				break;
			case ABARK:
				authService= new EManasAuthService();
				break;
			case EHOSPITAL:
				authService= new EManasAuthService();
				break;
			case MOSIP:	
				 authService=new MosipAuthService();
		}
		
	return authService;
	}
	public static void getAuthServices() {
		for (AuthServiceType serType : AuthServiceType.values()) {
			  System.out.println(serType);
			}
	}
}