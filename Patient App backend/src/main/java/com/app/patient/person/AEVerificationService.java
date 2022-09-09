package com.app.patient.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.patient.user.Person;
import com.app.patient.user.UserRepository;

@Service("authorized_entity")
public class AEVerificationService implements PersonVerificationService {

	@Autowired
	UserRepository userRepo;

	@Override
	public Boolean checkIfUserExists(String aeID, String login, String password) {

		Person u = userRepo.findByaeID(aeID);

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
