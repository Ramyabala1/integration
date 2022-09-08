package com.emanas.middleware.policy;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emanas.middleware.consent.ConsentService;

@Service
public class PolicyDecisionService implements PolicyEnforcementService {

	@Autowired
	PolicyRetrievalService policyRetrieval;

	@Autowired
	ConsentService consentServ;

	@Override
	public HashMap<String, String> validateAccessRequest(String activity, String token, Boolean consentValidate) {
		HashMap<String, String> map = new HashMap<String, String>();
		if (policyRetrieval.getAccessPolicy(activity, token).equalsIgnoreCase("consent valid") && consentValidate) {
			return consentServ.findConsent(activity, token);

		} else if (policyRetrieval.getAccessPolicy(activity, token).equalsIgnoreCase("policy valid")) {

			map.put("result", "Valid");
		} else if (policyRetrieval.getAccessPolicy(activity, token).equalsIgnoreCase("Policy invalid")) {
			map.put("error", "E1010");
		}

		return map;

	}

}
