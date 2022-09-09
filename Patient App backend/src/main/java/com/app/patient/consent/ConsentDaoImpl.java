package com.app.patient.consent;

import org.springframework.beans.factory.annotation.Autowired;

public class ConsentDaoImpl implements ConsentDao {

	@Autowired
	ConsentRepo repo;

	@Override
	public Consent saveConsent(Consent c) {
		return repo.save(c);
	}

}
