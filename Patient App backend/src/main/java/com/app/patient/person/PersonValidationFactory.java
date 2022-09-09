package com.app.patient.person;

@SuppressWarnings("unused")
public interface PersonValidationFactory {

	PersonVerificationService getPersonVerificationService(String servicename);

}
