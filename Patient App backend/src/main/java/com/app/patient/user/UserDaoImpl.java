package com.app.patient.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDaoImpl implements UserDao {

	@Autowired
	UserRepository userRepo;

	@Override
	public Person saveuser(Person u) {
		return userRepo.save(u);
	}

	@Override
	public Person findUser(String eManasId) {
		return userRepo.findByEmanas(eManasId);
	}

	@Override
	public Person fetch(String login, String password) {
		return userRepo.findByLoginAndPassword(login, password);
	}

	@Override
	public Person findlogin(String login) {
		return userRepo.findByLogin(login);
	}

}
