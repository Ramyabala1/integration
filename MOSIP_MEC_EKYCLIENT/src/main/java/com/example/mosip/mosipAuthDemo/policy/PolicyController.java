package com.example.mosip.mosipAuthDemo.policy;



import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.xml.bind.PropertyException;

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
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class PolicyController {
	@Autowired
	private Environment env;
	
	 @Autowired
	   RestTemplate restTemplate;
	 
	 @Autowired
	 AuthController authController ; 
	 
	 @CrossOrigin(origins = "*")
	   @RequestMapping(value = "/mosip/updatepolicy" , method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	   public String updatepolicy(@RequestParam(value="policyID") String policyID ) throws IOException, PropertyException {
		   
		 ObjectMapper mapper = new ObjectMapper();
		 String token = authController.createAuthToken();
		
		 String url =  env.getProperty("urlBase") + "v1/policymanager/policies/"+policyID;
		
		 RestTemplate restTemplate = new RestTemplate();	
		 
		 
		 PolicyPayload policyPayload = new PolicyPayload();
		 policyPayload.setId(policyID);
		 
		 PolicyRequest policyRequest = new PolicyRequest();
		 
		 policyRequest.setVersion("1.0");
//		 policyRequest.setPolicyType("Auth");
		 policyRequest.setPolicyGroupName("policyGroupBanking");
		 policyRequest.setDesc("Account Opeing Policy  for Auth & KYC");
//		 policyRequest.setPolicyType("Auth");
		 policyRequest.setName("KYC_Banks_Acc");
		 
		 
		 Policy pol = new Policy();
		 pol.setAuthTokenType("policy");
		 
		 AllowedKycAttribute aKYCAttribute1 = new AllowedKycAttribute();
		 AllowedKycAttribute aKYCAttribute2 = new AllowedKycAttribute();
		 AllowedKycAttribute aKYCAttribute3 = new AllowedKycAttribute();
		 AllowedKycAttribute aKYCAttribute4 = new AllowedKycAttribute();
		 AllowedKycAttribute aKYCAttribute5 = new AllowedKycAttribute();
		 AllowedKycAttribute aKYCAttribute6 = new AllowedKycAttribute();
		 AllowedKycAttribute aKYCAttribute7 = new AllowedKycAttribute();
	
		 aKYCAttribute1.setAttributeName("fullName");
		 aKYCAttribute2.setAttributeName("gender");
		 aKYCAttribute3.setAttributeName("unmaskedEmail");
		 aKYCAttribute4.setAttributeName("unmaskedMobile");
		 aKYCAttribute5.setAttributeName("fullAddress");
		 aKYCAttribute6.setAttributeName("dateOfBirth");
		 aKYCAttribute7.setAttributeName("residenceStatus");
		 
		 List<AllowedKycAttribute> kycList = new ArrayList<AllowedKycAttribute>();
		 kycList.add(aKYCAttribute1);
		 kycList.add(aKYCAttribute2);
		 kycList.add(aKYCAttribute3);
		 kycList.add(aKYCAttribute4);
		 kycList.add(aKYCAttribute5);
		 kycList.add(aKYCAttribute6);
		 kycList.add(aKYCAttribute7);
		 pol.setAllowedKycAttributes(kycList);
		 
		 AllowedAuthType auth1 = new AllowedAuthType();
		 AllowedAuthType auth2 = new AllowedAuthType();
		 AllowedAuthType auth3 = new AllowedAuthType();
		 AllowedAuthType auth4 = new AllowedAuthType();
		 AllowedAuthType auth5 = new AllowedAuthType();
		 
		 auth1.setAuthSubType("IRIS");
		 auth1.setAuthType("bio");
		 auth1.setMandatory(false);
		 
		 auth2.setAuthSubType("FACE");
		 auth2.setAuthType("bio");
		 auth2.setMandatory(false);
		 
		 auth3.setAuthSubType("");
		 auth3.setAuthType("otp");
		 auth3.setMandatory(false);
		
		 auth4.setAuthSubType("");
		 auth4.setAuthType("otp-request");
		 auth4.setMandatory(false);
		 
		 auth5.setAuthSubType("");
		 auth5.setAuthType("kyc");
		 auth5.setMandatory(false);
		 
		 auth5.setAuthSubType("");
		 auth5.setAuthType("demo");
		 auth5.setMandatory(false);
		 
		 List<AllowedAuthType> authList = new ArrayList<AllowedAuthType>();
		 
		 authList.add(auth1);
		 authList.add(auth2);
		 authList.add(auth3);
		 authList.add(auth4);
		 authList.add(auth5);
		 
		 
		 pol.setAllowedAuthTypes(authList);
		 
		 policyRequest.setPolicies(pol);
		 
		 policyPayload.setRequest(policyRequest);
		 
		 
		 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
		 formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
				 		 
		 policyPayload.setRequestTime(formatter.format(new Date()));
		 
		 String strPolicyPayload = mapper.writeValueAsString(policyPayload);
		 
		
		 
		
		 HttpHeaders headers = new HttpHeaders();
		 headers.add("content-Type","application/json");	   
	     headers.add("Cookie","Authorization="+token);
	   
	    
	     HttpEntity<String> entity = new HttpEntity<>(strPolicyPayload,headers);

	     
	     HttpEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);
	    
	     
	     String res = response.getBody();
	     System.out.println(res);
	     

	   
	    	 return res;
		 
	 } 
	 
	 @CrossOrigin(origins = "*")
	   @RequestMapping(value = "/mosip/getPolicy" , method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	   public String getPolicy() throws IOException, PropertyException {
	
		 
		 String token = authController.createAuthToken();
		
		 String url =  env.getProperty("urlBase") + "v1/policymanager/policies";
		
		 RestTemplate restTemplate = new RestTemplate();	
		 
		 PolicyPayload policyPayload = new PolicyPayload();
		 
		 ObjectMapper objMap = new ObjectMapper();
		 String reqObjMap = objMap.writeValueAsString(policyPayload);
		 
		 HttpHeaders headers = new HttpHeaders();
		 headers.add("content-Type","application/json");	   
		 headers.add("Cookie","Authorization="+token);
	   
	    
	     HttpEntity<String> entity = new HttpEntity<>(reqObjMap,headers);

	    
	     HttpEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
	    
	     String res = response.getBody();
	     System.out.println(res);
	     

	   
	    	 return res;
	 

}
	 
	 
	 @CrossOrigin(origins = "*")
	   @RequestMapping(value = "/mosip/getPolicyGroup" , method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	   public String getPolicyGroup(@RequestParam(value="policyGroupName") String policyGroupName) throws IOException, PropertyException {
	
		 
		 String token = authController.createAuthToken();		
		 String url =  env.getProperty("urlBase") + "v1/policymanager/policies/group/all";		
		 RestTemplate restTemplate = new RestTemplate();		 
		 
		 HttpHeaders headers = new HttpHeaders();
		 headers.add("content-Type","application/json");	   
		 headers.add("Cookie","Authorization="+token);	   
	    
	     HttpEntity<String> entity = new HttpEntity<>("",headers);	     
	     HttpEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
	    
	     String res = response.getBody();
	     System.out.println(res);
	     ObjectMapper objStringMapper = new ObjectMapper();
	     JsonNode node = objStringMapper.readValue(res, JsonNode.class).get("response");
	    
		if (node!=null && node.isArray()) {
		    for (final JsonNode objNode : node) {
		    
		   JsonNode policyGroupNode = objNode.get("policyGroup");
		    if(policyGroupNode.get("name").asText().equalsIgnoreCase(policyGroupName))
		    {
		    	return policyGroupNode.get("id").asText();
		    }
		    	
		    	
		    }

	   
	 

}
		
		return null;
		
	 }
	 
	 
	 @CrossOrigin(origins = "*")
	   @RequestMapping(value = "/mosip/definePolicy" , method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	   public String definepolicy(@RequestParam(value="policyGroup") String policyGroup ,@RequestParam(value="policyName") String policyName ) throws IOException, PropertyException {
		   
		 ObjectMapper mapper = new ObjectMapper();
		 String token = authController.createAuthToken();
		
		 String url =  env.getProperty("urlBase") + "v1/policymanager/policies";
		
		 RestTemplate restTemplate = new RestTemplate();
		 
		 PolicyPayload policyPayload = new PolicyPayload();
		 policyPayload.setId("");
		 
		 
		
		 PolicyRequest policyRequest = new PolicyRequest();
		 
		 policyRequest.setVersion("1.0");
		 policyRequest.setPolicyType("Auth");
		 policyRequest.setPolicyGroupName(policyGroup);
		 policyRequest.setDesc("Policy group for Emanas");
		 policyRequest.setPolicyType("Auth");
		 policyRequest.setName(policyName);
		 
		 Policy pol = new Policy();
		 pol.setAuthTokenType("policy");
		 
		 AllowedKycAttribute aKYCAttribute1 = new AllowedKycAttribute();
		 AllowedKycAttribute aKYCAttribute2 = new AllowedKycAttribute();
		 AllowedKycAttribute aKYCAttribute3 = new AllowedKycAttribute();
		 AllowedKycAttribute aKYCAttribute4 = new AllowedKycAttribute();
		 AllowedKycAttribute aKYCAttribute5 = new AllowedKycAttribute();
		 AllowedKycAttribute aKYCAttribute6 = new AllowedKycAttribute();
		 AllowedKycAttribute aKYCAttribute7 = new AllowedKycAttribute();
	
		 aKYCAttribute1.setAttributeName("fullName");
		 aKYCAttribute2.setAttributeName("gender");
		 aKYCAttribute3.setAttributeName("unmaskedEmail");
		 aKYCAttribute4.setAttributeName("unmaskedMobile");
		 aKYCAttribute5.setAttributeName("fullAddress");
		 aKYCAttribute6.setAttributeName("dateOfBirth");
		 aKYCAttribute7.setAttributeName("residenceStatus");
		 
		 List<AllowedKycAttribute> kycList = new ArrayList<AllowedKycAttribute>();
		 kycList.add(aKYCAttribute1);
		 kycList.add(aKYCAttribute2);
		 kycList.add(aKYCAttribute3);
		 kycList.add(aKYCAttribute4);
		 kycList.add(aKYCAttribute5);
		 kycList.add(aKYCAttribute6);
		 kycList.add(aKYCAttribute7);
		 pol.setAllowedKycAttributes(kycList);
		 
		 AllowedAuthType auth1 = new AllowedAuthType();
		 AllowedAuthType auth2 = new AllowedAuthType();
		 AllowedAuthType auth3 = new AllowedAuthType();
		 AllowedAuthType auth4 = new AllowedAuthType();
		 AllowedAuthType auth5 = new AllowedAuthType();
		 
		 auth1.setAuthSubType("IRIS");
		 auth1.setAuthType("bio");
		 auth1.setMandatory(false);
		 
		 auth2.setAuthSubType("FACE");
		 auth2.setAuthType("bio");
		 auth2.setMandatory(false);
		 
		 auth3.setAuthSubType("");
		 auth3.setAuthType("otp");
		 auth3.setMandatory(false);
		
		 auth4.setAuthSubType("");
		 auth4.setAuthType("otp-request");
		 auth4.setMandatory(false);
		 
		 auth5.setAuthSubType("");
		 auth5.setAuthType("kyc");
		 auth5.setMandatory(false);
		 
		 auth5.setAuthSubType("");
		 auth5.setAuthType("demo");
		 auth5.setMandatory(false);
		 
		 List<AllowedAuthType> authList = new ArrayList<AllowedAuthType>();
		 
		 authList.add(auth1);
		 authList.add(auth2);
		 authList.add(auth3);
		 authList.add(auth4);
		 authList.add(auth5);
		 
		 
		 pol.setAllowedAuthTypes(authList);
		 policyRequest.setPolicies(pol);
		 
		
		 policyPayload.setRequest(policyRequest);
		 
		 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
		 formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
				 		 
		 policyPayload.setRequestTime(formatter.format(new Date()));
		 
		 String strPolicyPayload = mapper.writeValueAsString(policyPayload);
		 	
		
		 HttpHeaders headers = new HttpHeaders();
		 headers.add("content-Type","application/json");	   
		 headers.add("Cookie","Authorization="+token);
	   
	    
	     HttpEntity<String> entity = new HttpEntity<>(strPolicyPayload,headers);

	     
	     HttpEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
	    
	     
	     String res = response.getBody();
	     ObjectMapper obj = new ObjectMapper();
	     JsonNode node = obj.readValue(res, JsonNode.class).get("response");
		    
			if (node!=null && node.get("id").asText()!=null) {
			 
              return node.get("id").asText();
		   
		 

	}else
	{
	     return obj.readValue(res, JsonNode.class).get("errors").asText();
		 
	 } 
	 }
	 
	 @CrossOrigin(origins = "*")
	   @RequestMapping(value = "/mosip/createPolicyGroup" , method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	   public String createPolicyGroup(@RequestParam(value="policyName") String policyName ,@RequestParam(value="desc") String desc ) throws IOException, PropertyException {
		   
		 ObjectMapper mapper = new ObjectMapper();
		 String token = authController.createAuthToken();
		
		 String url =  env.getProperty("urlBase") + "v1/policymanager/policies/group/new";
		
		 RestTemplate restTemplate = new RestTemplate();
		 
		 PolicyPayload policyPayload = new PolicyPayload();
		 policyPayload.setId("");
		 policyPayload.setVersion("v1.0");
		 
		
		 PolicyRequest policyRequest = new PolicyRequest();
		 
		 
		 policyRequest.setDesc(desc);
		
		 policyRequest.setName(policyName);
		 
		 policyPayload.setRequest(policyRequest);
		 
		 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
		 formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
				 		 
		 policyPayload.setRequestTime(formatter.format(new Date()));		 
		 String strPolicyPayload = mapper.writeValueAsString(policyPayload);		 			
		 HttpHeaders headers = new HttpHeaders();
		 headers.add("content-Type","application/json");	   
		 headers.add("Cookie","Authorization="+token);	   
	     HttpEntity<String> entity = new HttpEntity<>(strPolicyPayload,headers);   
	     HttpEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);   
	     
	     String res = response.getBody();
	     System.out.println(res);
	     return res;
		 
	 } 
	 
	 @CrossOrigin(origins = "*")
	   @RequestMapping(value = "/mosip/publishPolicy" , method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	  
	 public String publishPolicy(@RequestParam(value="policyID") String policyID ,@RequestParam(value="groupId") String groupId ) throws IOException, PropertyException {
		   
		 ObjectMapper mapper = new ObjectMapper();
		 String token = authController.createAuthToken();
		
		 String url =  env.getProperty("urlBase") + "v1/policymanager/policies/"+ policyID + "/group/" + groupId +"/publish";
		 RestTemplate restTemplate = new RestTemplate();		 
		 PolicyPayload policyPayload = new PolicyPayload();
		 String strPolicyPayload = mapper.writeValueAsString(policyPayload);
		 HttpHeaders headers = new HttpHeaders();
		 headers.add("content-Type","application/json");	   
		  headers.add("Cookie","Authorization="+token);
	     HttpEntity<String> entity = new HttpEntity<>(strPolicyPayload,headers);    
	     HttpEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);    
	     String res = response.getBody();
	     System.out.println(res);
	     

	   
	    	 return res;
		 
	 } 
	 
}
