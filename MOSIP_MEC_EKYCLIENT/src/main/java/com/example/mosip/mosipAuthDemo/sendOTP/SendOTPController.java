package com.example.mosip.mosipAuthDemo.sendOTP;



import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.xml.bind.PropertyException;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.mosip.mosipAuthDemo.authentication.AuthController;
import com.example.mosip.mosipAuthDemo.helper.signHelper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class SendOTPController {
	@Autowired
	private Environment env;
	
	 @Autowired
	   RestTemplate restTemplate;
	 
	 @Autowired
	 AuthController authController ; 
	 
	 @CrossOrigin(origins = "*")
	   @RequestMapping(value = "/mosip/sendOTP" , method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	   public String sendOTP(@RequestParam(value="mosipID") String mosipid) throws IOException, PropertyException {
		   
		 ObjectMapper mapper = new ObjectMapper();
		 String token = authController.createAuthToken();
		 //String vin = "6471974360";
		// String vin = "8950483749"; 
		 String vin =mosipid;
		 String url =  env.getProperty("urlBase") + "idauthentication/v1/otp/"+env.getProperty("mispLicenceKey")+"/"+
				 env.getProperty("partnerId")+"/"+env.getProperty("partnerAPIKey");
		
		 RestTemplate restTemplate = new RestTemplate();		
		
		 SendOtpRequest otpPayload = new SendOtpRequest();
		 
		 otpPayload.setVersion("1.0");
		 otpPayload.setIndividualId(vin);
		 otpPayload.setIndividualIdType("UIN");
		 otpPayload.setId("mosip.identity.otp");
		 otpPayload.setTransactionID(RandomStringUtils.random(10, false, true));
		 List<String> otpList = new ArrayList<String>();
		 otpList.add("EMAIL");
		 otpList.add("PHONE");
		
		 otpPayload.setOtpChannel(otpList);
		 
		 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
		 formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
				 		 
		 otpPayload.setRequestTime(formatter.format(new Date()));
		 
		 String strAuthPayload = mapper.writeValueAsString(otpPayload);
		 
		 String encodedPayloadString  = Base64.getEncoder().encodeToString(strAuthPayload.getBytes());
		
		// String signature = jwtEncode(token,encodedPayloadString);
		 
		 String signature = new signHelper().generateSignatureWithRequest(strAuthPayload,  env.getProperty("partnerId"));
		
		 HttpHeaders headers = new HttpHeaders();
		 headers.add("content-Type","application/json");
	     headers.add("signature",signature);
	     headers.add("Authorization",token);
	   
	    
	     HttpEntity<String> entity = new HttpEntity<>(strAuthPayload,headers);

	     
	     HttpEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
	     HttpHeaders authHeaders = response.getHeaders();
	
	     String res = response.getBody();
	     System.out.println("Executed Now sendOTP : "+ System.currentTimeMillis());
	     System.out.println(res);
	     return res;
	     
//	     JsonNode resp = mapper.readValue(response.getBody(), JsonNode.class);
//	     if(resp.has("response"))
//	     {
//	    	return  response.getBody();
//	     }
//	     
//	     
	     
	     
//	     
//	     
//	     String statusMgs = resp.get("response").asText();
//        if(statusMgs.equalsIgnoreCase("null"))
//	     {
//	    	 return res;
//	     }else
//	     {
//	    	 return  resp.get("errors").asText();
//	     }
//	   
	    	
		 
	 } 
	 
	 
//		/*
//		 * @CrossOrigin(origins = "*")
//		 * 
//		 * @RequestMapping(value = "/mosip/sendOTP" , method = RequestMethod.POST,
//		 * produces = MediaType.APPLICATION_JSON_VALUE) public String sendOTPHttp()
//		 * throws IOException, PropertyException {
//		 * 
//		 * ObjectMapper mapper = new ObjectMapper(); String token =
//		 * authController.createAuthToken(); String vin = "6471974360"; String url =
//		 * env.getProperty("urlBase") +
//		 * "idauthentication/v1/otp/"+env.getProperty("mispLicenceKey")+"/"+
//		 * env.getProperty("partnerId")+"/"+env.getProperty("partnerAPIKey");
//		 * 
//		 * 
//		 * 
//		 * RestTemplate restTemplate = new RestTemplate();
//		 * 
//		 * SendOtpRequest otpPayload = new SendOtpRequest();
//		 * 
//		 * otpPayload.setVersion("1.0"); otpPayload.setIndividualId(vin);
//		 * otpPayload.setIndividualIdType("UIN");
//		 * otpPayload.setId("mosip.identity.otp");
//		 * otpPayload.setTransactionID("1234567890"); List<String> otpList = new
//		 * ArrayList<String>(); otpList.add("EMAIL"); otpList.add("PHONE");
//		 * 
//		 * otpPayload.setenv("Developer");
//		 * otpPayload.setDomainUri(env.getProperty("urlBase"));
//		 * otpPayload.setOtpChannel(otpList);
//		 * 
//		 * SimpleDateFormat formatter = new
//		 * SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
//		 * formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
//		 * 
//		 * 
//		 * otpPayload.setRequesttime(formatter.format(new Date()));
//		 * 
//		 * String strAuthPayload = mapper.writeValueAsString(otpPayload); String
//		 * encodedPayloadString =
//		 * Base64.getEncoder().encodeToString(strAuthPayload.getBytes());
//		 * 
//		 * String signature = jwtEncode(token,encodedPayloadString);
//		 * 
//		 * 
//		 * // URL obj = new URL(url); // HttpURLConnection con = (HttpURLConnection)
//		 * obj.openConnection(); // // // Setting basic post request //
//		 * con.setRequestMethod("POST"); // // con.setRequestProperty("Accept-Language",
//		 * "en-US,en;q=0.5"); //
//		 * con.setRequestProperty("Content-Type","application/json"); //
//		 * con.setRequestProperty("signature", signature); //
//		 * con.setRequestProperty("Authorization", token); // // con.setDoOutput(true);
//		 * // // System.out.println(con.getRequestProperties()); // // DataOutputStream
//		 * wr = new DataOutputStream(con.getOutputStream()); //
//		 * wr.writeBytes(strAuthPayload); // wr.flush(); // wr.close(); // // int
//		 * responseCode = con.getResponseCode(); // // BufferedReader in = new
//		 * BufferedReader( // new InputStreamReader(con.getInputStream())); // String
//		 * output; // StringBuffer response = new StringBuffer(); // // while ((output =
//		 * in.readLine()) != null) { // response.append(output); // } // in.close(); //
//		 * // //printing result from response //
//		 * System.out.println(response.toString()); // // return response.toString(); //
//		 * }
//		 */		
		  
		  
		  
//		 HttpHeaders headers = new HttpHeaders();
//		 headers.add("content-Type","application/json");
//	     headers.add("signature",signature);
//	     headers.add("Authorization", token);
//	    
//	     System.out.println("signature"+signature);
//	     System.out.println("Authorization"+token);
//	     System.out.println(headers);
//	    
//	     HttpEntity<String> entity = new HttpEntity<>(strAuthPayload,headers);
//
//	     System.out.println(entity.getHeaders());
//	     
//	     HttpEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
//	     HttpHeaders authHeaders = response.getHeaders();
//	     
//	     String res = response.getBody();
//	     
//	     JsonNode resp = mapper.readValue(response.getBody(), JsonNode.class);
//	     String statusMgs = resp.get("response").get("status").asText();
//	     if(statusMgs.equalsIgnoreCase("success"))
//	     {
//	    	 return authHeaders.getFirst(authHeaders.AUTHORIZATION);
//	     }else
//	     {
//	    	 return  resp.get("errors").asText();
//	     }
//	 }
//		    
		 
	 
	 
	 
	 
		 
	     public String jwtEncode(String token,String dataToSign) throws IOException
	     {
	    	 String url =  env.getProperty("urlBase") + "idauthentication/v1/internal/jwtSign";
	    	 
	    	 JwtEncodeRequest jwthReq = new JwtEncodeRequest();
	    	 jwthReq.setApplicationId("IDA");
	    	 jwthReq.setCertificateUrl("string");
			 
	    	 jwthReq.setIncludeCertHash(true);
			 jwthReq.setIncludeCertificate(true);
			 jwthReq.setIncludePayload(false);
			 jwthReq.setDataToSign(dataToSign);
			 jwthReq.setReferenceId("SIGN");
			
			 
			 JwtEncodePayload jwtPayload = new JwtEncodePayload();
			 jwtPayload.setRequest(jwthReq);
			 jwtPayload.setVersion("v1");
			
			 ObjectMapper mapper = new ObjectMapper();

			 jwtPayload.setMetadata( mapper.createObjectNode());
			 jwtPayload.setId("string");
			
			 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
			 formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
			 jwtPayload.setRequesttime(formatter.format(new Date()));
			 	
			 String strAuthPayload = mapper.writeValueAsString(jwtPayload);
			 HttpHeaders headers = new HttpHeaders();
			 headers.add(HttpHeaders.COOKIE, "Authorization="+ token);
		    
		     headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
		       
		     HttpEntity<String> entity = new HttpEntity<String>(strAuthPayload,headers);
//		     String res  ; 
//		     res =  restTemplate.postForObject(
//		    		  url,  entity, String.class);
		     
		     HttpEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
		     JsonNode resp = mapper.readValue(response.getBody(), JsonNode.class);
		     if(resp !=null && resp.get("response")!=null) {
		    
		    	 return resp.get("response").get("jwtSignedData").asText();
		     
		     }else
		     {
		    	 return null;
		     }
	    	 
	    	 
	
}

}
