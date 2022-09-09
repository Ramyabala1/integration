package com.app.patient.consent;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsentRepo extends JpaRepository<Consent, Integer> {

	@Override
	Consent save(Consent c);

	Consent save(String response);

}
