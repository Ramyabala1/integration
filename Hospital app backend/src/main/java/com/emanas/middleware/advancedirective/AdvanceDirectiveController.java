package com.emanas.middleware.advancedirective;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import javax.ws.rs.core.Response;
import javax.xml.bind.PropertyException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.emanas.middleware.consent.ConsentController;
import com.emanas.middleware.models.ResponseConfig;
import com.emanas.middleware.utility.LoadConfig;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@Api(value = "Swagger API", description = "Advance Directive")
@RestController
@Slf4j
public class AdvanceDirectiveController {
	

@Autowired
AdvanceDirectiveService adServ;
	
	 @CrossOrigin(origins = "*")
	 @RequestMapping(value = "/ad/ViewAd" , method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	   public ResponseConfig viewAD(@RequestBody String details  ) throws IOException, PropertyException, ParseException {
		 ObjectMapper objStringMapper = new ObjectMapper();
			JsonNode node = objStringMapper.readValue(details, JsonNode.class);
			String transactionID = null ; 
			if(node.has("transactionID"))
			{
				transactionID= node.get("transactionID").asText();
			}
			String api = node.get("api").asText();
			String version = node.get("version").asText();
			
		 String response = adServ.viewAdvanceDirective(details);
		 ResponseConfig responses = new ResponseConfig();
		 responses.setTransactionId(transactionID);
			responses.setTimeStamp(new Date());	
			responses.setApi(api);
			responses.setVersion(version);
			if(response!=null && response.length()!=5 && !response.substring(0,1).contains("E"))
			{
				
				responses.setCode(Response.Status.OK.getStatusCode());
				responses.setResponse(response);
				responses.setMessage("View Advance Directive successfull");
				
			} else if(response.contains("error") && response.contains("-"))
			{
				responses.setCode(Response.Status.BAD_REQUEST.getStatusCode());
				responses.setErrorcode("E2000");
				if(response.contains("-")) {
				responses.setMessage(response.split("-")[1]);
				}
				responses.setAction("Please Try again");
			}else if(response.length()==5 && response.substring(0,1).contains("E"))
			{
				responses.setCode(Response.Status.BAD_REQUEST.getStatusCode());
				responses.setErrorcode(response);
				String message = LoadConfig.getConfigValue(response);
				if(message!=null)
				{
					responses.setMessage(message.split("-")[0]);
					responses.setAction(message.split("-")[1]);
				}
			}
			return responses;
		
	}
	 
	 
	  @CrossOrigin(origins = "*")
		 @RequestMapping(value = "/ad/downloadFile/{fileuuid}/{type}/{filetype}" , method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
		   public ResponseConfig downloadFile(@PathVariable(value= "fileuuid") String fileUUID,@PathVariable(value= "type") String type,
				   @PathVariable(value= "filetype") String filetype) throws IOException, PropertyException {
		   String response = adServ.downloadFile(fileUUID,type,filetype);
		  
		   ResponseConfig responses = new ResponseConfig();
			
		   if(response!=null && response.length()!=5 && !response.substring(0,1).contains("E"))
			{
				
				responses.setCode(Response.Status.OK.getStatusCode());
				responses.setResponse(response);
				responses.setMessage("View Advance Directive successfull");
				
			} else if(response.contains("error") && response.contains("-"))
			{
				responses.setCode(Response.Status.BAD_REQUEST.getStatusCode());
				responses.setErrorcode("E2000");
				if(response.contains("-")) {
				responses.setMessage(response.split("-")[1]);
				}
				responses.setAction("Please Try again");
			}else if(response.length()==5 && response.substring(0,1).contains("E"))
			{
				responses.setCode(Response.Status.BAD_REQUEST.getStatusCode());
				responses.setErrorcode(response);
				String message = LoadConfig.getConfigValue(response);
				if(message!=null)
				{
					responses.setMessage(message.split("-")[0]);
					responses.setAction(message.split("-")[1]);
				}
			}
			return responses;
		
	}
}
