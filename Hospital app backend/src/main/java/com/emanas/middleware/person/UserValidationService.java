package com.emanas.middleware.person;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("USER")
@Qualifier("USER")
public class UserValidationService implements PersonValidationService {

	@Override
	public String personRecordExists() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String fetchPersonRecord(String eManasId) {
		// TODO Auto-generated method stub
		return null;
	}

}
