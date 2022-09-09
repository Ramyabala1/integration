package com.app.patient.consent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConsentVerificationSerice {

	@Autowired
	ConsentRepo repo;

	public Boolean saveConsent(Consent c) {
		Consent consent = repo.save(c);
		if (consent != null) {
			return true;
		}
		return false;
	}

	public void saveConsentid(String response) {
		Consent c = repo.save(response);

	}

}
