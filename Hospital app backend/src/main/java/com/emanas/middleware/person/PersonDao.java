package com.emanas.middleware.person;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface PersonDao {

	void savePerson(Person person);

	Person getPerson(String personID) throws JsonMappingException, JsonProcessingException;

	void deletePerson(String personID);
}
