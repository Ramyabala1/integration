package com.emanas.middleware.person;

import java.io.Serializable;

public interface PersonValidationService extends Serializable {
	String fetchPersonRecord(String eManasId);
	String personRecordExists();

}
