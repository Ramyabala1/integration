package com.app.patient.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.app.patient.user.Person;
import com.app.patient.user.UserRepository;

@Primary
@Service("patient")
public class PatientVerificationService implements PersonVerificationService {

	@Autowired
	UserRepository userRepo;

	@Override
	public Boolean checkIfUserExists(String eManasID, String login, String password) {

		Person u = userRepo.findByEmanas(eManasID);
		if (u != null) {
			return true;
		}
		u = userRepo.findByLoginAndPassword(login, password);
		if (u != null) {
			return true;
		}
		return false;
	}

}
