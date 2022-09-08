package com.emanas.middleware.policy;

import java.util.HashMap;

public interface PolicyEnforcementService {

	public HashMap<String, String> validateAccessRequest(String activity, String token, Boolean consentValidate);

}
