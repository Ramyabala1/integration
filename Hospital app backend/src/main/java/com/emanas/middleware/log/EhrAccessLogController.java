package com.emanas.middleware.log;

import java.util.List;

import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.emanas.middleware.models.ResponseConfig;



@RestController
public class EhrAccessLogController {
	
	@Autowired 
	  RestTemplate restTemplate;
	
	@Autowired 
	  EhrAccessLogService ehrLogServ;
	
	
	@CrossOrigin(origins = "*",allowedHeaders = "*")
	@RequestMapping(value = "/log/addLog", method = RequestMethod.POST)
	public ResponseConfig addLog(@RequestBody EhrAccessLog logData) {		
		
		Boolean res = ehrLogServ.saveLog(logData);
		ResponseConfig responses = new ResponseConfig();
	
		if(res!=null && res)
		{			
			responses.setCode(Response.Status.OK.getStatusCode());				
			responses.setMessage("Logs Added");
			
		
		}else
		{
			
			responses.setCode(Response.Status.BAD_REQUEST.getStatusCode());	
			responses.setErrorcode("E1050");
			responses.setMessage("Errors adding logs");
			
		}
		
		return responses;
	
	}
	
	
	
	@CrossOrigin(origins = "*",allowedHeaders = "*")
	@RequestMapping(value = "/log/getAccessLog/{patientId}", method = RequestMethod.POST)
	public ResponseConfig getAccessLog(@PathVariable(value="patientId") String patientId) {		
		
		List<EhrAccessLog> res = ehrLogServ.getAccessLog(patientId);
		JSONArray arr = new JSONArray(res);
		ResponseConfig responses = new ResponseConfig();
		
		if(res!=null && res.size()>0)
		{			
			responses.setCode(Response.Status.OK.getStatusCode());				
			responses.setResponse(arr.toString());
			
		
		}else
		{
			
			responses.setCode(Response.Status.BAD_REQUEST.getStatusCode());
			responses.setErrorcode("E1025");
			responses.setErrors("No Record found");
			
			
		}
		
		return responses;
	
	}

}
