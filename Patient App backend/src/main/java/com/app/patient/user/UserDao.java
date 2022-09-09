package com.app.patient.user;

public interface UserDao {

	Person saveuser(Person u);

	Person findUser(String eManasId);

	Person fetch(String login, String password);

	Person findlogin(String login);

}
