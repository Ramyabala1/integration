package com.example.mosip.mosipAuthDemo.createOtpEKycRequest;

import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.jose4j.json.internal.json_simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.example.mosip.mosipAuthDemo.helper.signHelper;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.mosip.kernel.core.util.CryptoUtil;
@Service
public class CreateOtpEkycRequestService {
	
	private static final String PARTNER_P12_FILE_NAME = "-partner.p12";
	private static final String PARTNER_CER_FILE_NAME = "-partner.cer";
	private ObjectMapper objMapper = new ObjectMapper();
	 String trId = RandomStringUtils.random(10, false, true);
	 
		@Autowired
		private Environment env ;
	
	public HashMap<String, String> createOTPekycRequest(String trId, String uin,String partnerId, String otp,Boolean isKyc) throws Exception {
		
		signHelper h = new signHelper();
		 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
		 formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
		HashMap<String, Object >payload = new HashMap<String,Object>();
		payload.put("otp", otp);
		payload.put("transactionId",trId);
		payload.put("timestamp",formatter.format(new Date()));

		EncryptionRequestDto encryptionRequestDto = new EncryptionRequestDto();
		
		encryptionRequestDto.setIdentityRequest(payload);
		
		EncryptionResponseDto encryptionResponse = h.kernelEncrypt(encryptionRequestDto, "ida");

		JSONObject jsonReqAuth = new JSONObject();
		jsonReqAuth.put("bio", false);
		jsonReqAuth.put("demo", false);
		jsonReqAuth.put("pin", false);
		jsonReqAuth.put("otp", true);
		
		HashMap<String, Object > reqValues = new HashMap<String,Object>();
		reqValues.put("requestHMAC", encryptionResponse.getRequestHMAC());
		reqValues.put("requestSessionKey", encryptionResponse.getEncryptedSessionKey());
		reqValues.put("request", encryptionResponse.getEncryptedIdentity());
		 
		reqValues.put("requestTime", formatter.format(new Date()));
		reqValues.put("requestedAuth",jsonReqAuth);
				//"{\"bio\":false,\"demo\":false,\"otp\":true,\"pin\":false}");
		reqValues.put("transactionID", trId);
		reqValues.put("version", "1.0");
		reqValues.put("domainUri", env.getProperty("urlBase"));
		reqValues.put("env", env.getProperty("urlBase"));
		reqValues.put("specVersion", "1.0");
		reqValues.put("consentObtained", "true");
		if(isKyc) {
		reqValues.put("id", "mosip.identity.kyc");
		}else
		{
			reqValues.put("id", "mosip.identity.auth");
		}
		reqValues.put("individualId",uin);
		reqValues.put("individualIdType","UIN");
	

		X509Certificate cert =  h.getCertificateEntry(h.getKeysDirPath(),"ida");
		byte[] encCert = DigestUtils.sha256(cert.getEncoded());

		reqValues.put("thumbprint",CryptoUtil.encodeBase64(encCert));

		
		String jsonStr = new ObjectMapper().writeValueAsString(reqValues);

		String signature = h.generateSignatureWithRequest(jsonStr, partnerId);
		
			
		HashMap<String, String> retValues = new HashMap<String,String>();
		retValues.put("reqStr", jsonStr);
		retValues.put("signature", signature);
		retValues.put("sessionKey", encryptionResponse.getEncryptedSessionKey());

		return retValues;
	}

	public HashMap<String, String> createOTPekycRequest(String trId2, String mosipid, String property, String otp,
			boolean b) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	
	
	
}
