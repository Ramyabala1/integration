package com.example.mosip.mosipAuthDemo.sendOTPekycRequest;

import java.util.Arrays;
import java.util.HashMap;
import javax.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.mosip.mosipAuthDemo.authentication.AuthController;
//import com.example.mosip.mosipAuthDemo.createOtpEKycRequest.CreateOtpEkycRequestService;

import com.example.mosip.mosipAuthDemo.createOtpEKycRequest.CreateOtpEkycRequestController;
import com.example.mosip.mosipAuthDemo.createOtpEKycRequest.CreateOtpEkycRequestService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.mosip.kernel.core.util.CryptoUtil;
@RestController
public class SendOTPEkycRequestController {
	
	
	@Autowired
	private Environment env;
	
	 @Autowired
	   RestTemplate restTemplate;
	 
	 @Autowired
	 CreateOtpEkycRequestController createOtpEkyc;
	 
	 @Autowired 
	 AuthController auth;
	 
	 @Autowired
	 CreateOtpEkycRequestService ekycServ;
	
	@CrossOrigin(origins = "*")
	   @RequestMapping(value = "/mosip/sendOTPekycRequest" , method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	   public Response sendOTPEkycRequestController(@RequestBody String jsonReq) throws Exception {
		  Response objResponse = null;
		String token = auth.createAuthToken();
		
		ObjectMapper obj = new ObjectMapper();
		JsonNode jsonObj = obj.readTree(jsonReq);
		String mosipid = jsonObj.get("mosipID").asText();
		String otp = jsonObj.get("otp").asText();
		String trId = jsonObj.get("transactionID").asText();
		
//	List<String> createOtpList = createOtpEkyc.createOTPekycRequest(mosipid,otp);
//		
//		String reqPayload = null;
//	 String signature = null;
//		
//		if(createOtpList!=null && createOtpList.size()>0)
//	{
//			 reqPayload = createOtpList.get(0);
//			  signature = createOtpList.get(1);
//		}else
//		{  return Response.status(Response.Status.BAD_REQUEST).entity("create OTP eKyc Request failed").build();
//			 
//		}
		
		HashMap<String, String> req = ekycServ.createOTPekycRequest(trId,mosipid, env.getProperty("partnerId"), otp, true);
		System.out.println(req.get("reqStr"));
		System.out.println(req.get("signature"));
		
	   String url = env.getProperty("urlBase") +"idauthentication/v1/kyc/" +env.getProperty("mispLicenceKey") + "/" +env.getProperty("partnerId")  + "/" + env.getProperty("partnerAPIKey");
	   
	   
		 HttpHeaders headers = new HttpHeaders();
	     
	     headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);

	     headers.set("signature", req.get("signature"));
	     headers.set("Authorization", token);
	     HttpEntity<String> entity = new HttpEntity<String>(req.get("reqStr"),headers);
	    
	     ObjectMapper mapper = new ObjectMapper();
	     HttpEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
	     String res = response.getBody();
	     JsonNode actualObj = mapper.readTree(res);
	     Decryptkyv dec = new Decryptkyv();
	     String decRes = null;
	     JsonNode respObj = actualObj.get("response");
	 
	     if(!respObj.isNull()) {
	     HashMap<String,String> retVal = new HashMap<String,String>();
			retVal.put("authToken", respObj.get("authToken").toString());

			//Check new version or older logic
			if(respObj.has("sessionKey")) {
			
				retVal.put("sessionKey", respObj.get("sessionKey").toString());
				retVal.put("identity", respObj.get("identity").toString());
						
			}
			else {
				String[] ret =splitResponse(respObj.get("identity").toString());
				retVal.put("sessionKey", ret[0]);
				
				retVal.put("identity", ret[1]);
				
			}
			
			decRes  = dec.decrypt(retVal.get("identity"),retVal.get("sessionKey"),env.getProperty("partnerId"));
			
		
			
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			 
			   Identity objFromJson = mapper.readValue(decRes,Identity.class);
		       System.out.println(objFromJson); 
		       if(decRes!=null) {
		       objResponse = Response.status(Response.Status.FOUND).entity(objFromJson).build();
		       }
	     }
		       else
		       {
		    	   objResponse = Response.status(Response.Status.BAD_REQUEST).entity("OTP does not match").build();
		    	      
		       }
				
				
		       return objResponse;
			

		}
		

	     
	     
	     
	     
	     
	     
	     
	     
	     
	     
	     
	     
	     
	     
	     
	     
	    
	
	



String[] splitResponse(String encodedResponse) {
	String [] retVal  = new String[2];
	
	String keysplitter = "#KEY_SPLITTER#";
	
	byte[] byteResponse = CryptoUtil.decodeBase64(encodedResponse);
	int pos = indexOf(byteResponse,0,keysplitter.getBytes(), 0,0);
	System.out.println(pos);
	byte[] encKey = Arrays.copyOf(byteResponse,pos);
	int remLen = byteResponse.length ;
	byte[] encIdentity = Arrays.copyOfRange( byteResponse, pos + keysplitter.length(),remLen);

	retVal[0] = CryptoUtil.encodeBase64String(encKey);
	retVal[1] = CryptoUtil.encodeBase64String(encIdentity);
	
	
	return retVal;
}

static int indexOf(byte[] source, int sourceOffset, byte[] target, int targetOffset, int fromIndex) {
	  int sourceCount = source.length;
	  int targetCount = target.length;
	  
		if (fromIndex >= sourceCount) {
	        return (targetCount == 0 ? sourceCount : -1);
	    }
	    if (fromIndex < 0) {
	        fromIndex = 0;
	    }
	    if (targetCount == 0) {
	        return fromIndex;
	    }

	    byte first = target[targetOffset];
	    int max = sourceOffset + (sourceCount - targetCount);

	    for (int i = sourceOffset + fromIndex; i <= max; i++) {
	        /* Look for first character. */
	        if (source[i] != first) {
	            while (++i <= max && source[i] != first)
	                ;
	        }

	        /* Found first character, now look at the rest of v2 */
	        if (i <= max) {
	            int j = i + 1;
	            int end = j + targetCount - 1;
	            for (int k = targetOffset + 1; j < end && source[j] == target[k]; j++, k++)
	                ;

	            if (j == end) {
	                /* Found whole string. */
	                return i - sourceOffset;
	            }
	        }
	    }
	    return -1;
	}
 
 
 
 
 
 
	
}
