package com.app.patient.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Person, Integer> {
	Person findByEmanas(String emanasId);

	Person findByaeID(String aeId);

	Person findByLoginAndPassword(String login, String password);

	Person findByLogin(String login);

}
