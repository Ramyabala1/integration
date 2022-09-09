package com.app.patient.person;

import javax.transaction.Transactional;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.app.patient.config.LoadConfig;
import com.app.patient.model.ResponseConfig;
import com.app.patient.user.Person;
import com.app.patient.user.UserDaoImpl;

@RestController
public class PersonController {

	@Autowired
	PersonService personServ;

	@Autowired
	UserDaoImpl service;

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@RequestMapping(value = "/patient/initRegistration", method = RequestMethod.POST)
	public ResponseConfig initRegistration(@RequestBody String personData) {

		ResponseConfig res = personServ.initRegistration(personData);
		return res;
//		ResponseConfig responses = new ResponseConfig();
//		
//		System.out.println("res"+res);
//
//		if (res != null && res.length() != 5 && !res.substring(0, 1).contains("E")) {
//			responses.setCode(Response.Status.OK.getStatusCode());
//			responses.setResponse(res);
//
//			responses.setTransactionId(res);
//		} else if (res != null && res.length() == 5 && res.substring(0, 1).contains("E")) {
//			responses.setCode(Response.Status.BAD_REQUEST.getStatusCode());
//			responses.setErrorcode(res);
//			String message = LoadConfig.getConfigValue(res);
//			if (message != null) {
//				responses.setMessage(message.split("-")[0]);
//				responses.setAction(message.split("-")[1]);
//			}
//		} else {
//			responses.setCode(Response.Status.BAD_REQUEST.getStatusCode());
//			responses.setMessage(res);
//			responses.setAction("Please Try again");
//		}
//
//		return responses;

	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@RequestMapping(value = "/patient/registerPatient", method = RequestMethod.POST)
	public ResponseConfig registerPatient(@RequestBody String personData) {

		String res = personServ.registerPatient(personData);
		ResponseConfig responses = new ResponseConfig();

		if (res != null && res.length() != 5 && !res.substring(0, 1).contains("E")) {
			responses.setCode(Response.Status.OK.getStatusCode());
			responses.setResponse(res);

			responses.setTransactionId(res);
		} else if (res != null && res.length() == 5 && res.substring(0, 1).contains("E")) {
			responses.setCode(Response.Status.BAD_REQUEST.getStatusCode());
			responses.setErrorcode(res);
			String message = LoadConfig.getConfigValue(res);
			if (message != null) {
				responses.setMessage(message.split("-")[0]);
				responses.setAction(message.split("-")[1]);
			}
		} else {
			responses.setCode(Response.Status.BAD_REQUEST.getStatusCode());
			responses.setMessage(res);
			responses.setAction("Please Try again");
		}

		return responses;

	}

	@Transactional
	@CrossOrigin
	@RequestMapping(value = "/patient/login", method = RequestMethod.POST)
	public Person loginuser(@RequestBody Person user) throws Exception {
		String templogin = user.getLogin();
		String temppass = user.getPassword();
		Person userobj = null;
		if (templogin != null && temppass != null) {
			userobj = service.fetch(templogin, temppass);

		}
		if (userobj == null) {
			throw new Exception("bad Credentials");
		}
		return userobj;
	}

	@Transactional
	@CrossOrigin
	@RequestMapping(value = "/patient/find", method = RequestMethod.POST)
	public ResponseConfig checkEmanas(@RequestBody Person user) throws Exception {
		String tempId = user.getEmanas();
		ResponseConfig responses = new ResponseConfig();
		Person userobj = null;
		if (tempId != null && !"".equals(tempId)) {
			userobj = service.findUser(tempId);
			if (userobj == null) {
				responses.setCode(Response.Status.OK.getStatusCode());
				responses.setMessage("user does not exists");

			} else {
				responses.setCode(Response.Status.BAD_REQUEST.getStatusCode());
				responses.setMessage("user exist");

			}
		} else {
			responses.setCode(Response.Status.BAD_REQUEST.getStatusCode());
			responses.setMessage("user exist");
		}

		return responses;

	}

	@Transactional
	@CrossOrigin
	@RequestMapping(value = "/patient/findlogin", method = RequestMethod.POST)
	public ResponseConfig checkLogin(@RequestBody Person user) throws Exception {

		String tempId = user.getLogin();
		Person userobj = null;
		ResponseConfig responses = new ResponseConfig();

		if (tempId != null && !"".equals(tempId)) {
			userobj = service.findlogin(tempId);
			if (userobj == null) {
				responses.setCode(Response.Status.OK.getStatusCode());
				responses.setMessage("user available for registration");

			} else {
				responses.setCode(Response.Status.BAD_REQUEST.getStatusCode());
				responses.setMessage("user already exists");

			}
		} else {
			responses.setCode(Response.Status.BAD_REQUEST.getStatusCode());
			responses.setMessage("user does not exist");
		}

		return responses;
	}

}
