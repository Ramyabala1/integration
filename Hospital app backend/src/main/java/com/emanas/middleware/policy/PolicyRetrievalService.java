package com.emanas.middleware.policy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PolicyRetrievalService {

	@Autowired
	PolicySet policySet;

	public String getAccessPolicy(String activity, String token) {

		String policyStr = policySet.findPolicy(activity, token);
		return policyStr;
	}

}
