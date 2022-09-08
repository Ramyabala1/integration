package com.emanas.middleware.Demog;

import com.emanas.middleware.auth.AuthService;
import com.emanas.middleware.person.PersonValidationService;

@SuppressWarnings("unused")
public interface Factory {
	public DemogService getService(String servicename);

	public AuthService getAuthService(String servicename);

	public PersonValidationService getPersonService(String servicename);

}
