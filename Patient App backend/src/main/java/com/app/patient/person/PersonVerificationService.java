package com.app.patient.person;

import java.io.Serializable;

public interface PersonVerificationService extends Serializable {

	public Boolean checkIfUserExists(String eManasID, String login, String password);

}
