package com.emanas.middleware.auth;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.emanas.middleware.redis.model.SendOTP;
import com.emanas.middleware.redis.model.SendOTPDao;
import com.emanas.middleware.utility.LoadConfig;
import com.emanas.middleware.utility.cache.redis.Cache;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service("MOS")
public class MosipAuthService implements AuthService {

	Logger logger = LoggerFactory.getLogger(EManasAuthService.class);

	public String sendAuthRequest(Pid pid) {
		return "Create and send request for Mosip Authentication";
	}

	@Autowired
	SendOTPDao sendOtpDao;

	@Autowired
	Cache cacheServ;

	@Override
	public String sendOTPRequest(SendOTP otpData) {
		// TODO Auto-generated method stub

		URL url;
		try {

			url = new URL(LoadConfig.getConfigValue("MOSIP_API_URL") + "/sendOTP");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Accept", "application/json");
			conn.setRequestProperty("User-Agent",
					"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.114 Safari/537.36");

			JSONObject input = new JSONObject();
			input.put("mosipID", otpData.getContactVal());
			input.put("otpChannel", otpData.getOtpChannel());
			OutputStream os = conn.getOutputStream();
			os.write(input.toString().getBytes());
			os.flush();
			if (conn.getResponseCode() != 200) {
				logger.info("Connection Failed : HTTP error code : " + conn.getResponseCode());
				return null;
			}

			Scanner sc = new Scanner(conn.getInputStream());
			String output = "";
			while (sc.hasNext()) {
				output += sc.nextLine();
			}

			JSONObject jObject = new JSONObject(output);
			conn.disconnect();
			sc.close();
			SendOTP result = new SendOTP();

			if (jObject.isNull("errors")) {
				String id = UUID.randomUUID().toString();
				result.setTransactionID(id);
				result.setTokenID(jObject.getString("transactionID"));
				sendOtpDao.saveSendOTP(result);

				return id;
			} else {

				JSONArray jArray = jObject.getJSONArray("errors");
				JSONObject jerror = jArray.getJSONObject(0);
				result.setStatus("error:" + jerror.getString("errorMessage"));
				return "error-" + result.getStatus();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public String verifyAuthRequest(SendOTP otpData) {
		try {
			if (otpData.getPurpose() == null) {

				SendOTP sOTP = new SendOTP();

				sOTP = sendOtpDao.getOneSendOTP(otpData.getTransactionID());

				if (sOTP != null && sOTP.getTransactionID().equalsIgnoreCase(otpData.getTransactionID())) {
					return "verified";
				} else {
					return "error";
				}

			} else {
				SendOTP sOTP = new SendOTP();
				sOTP = sendOtpDao.getOneSendOTP(otpData.getTransactionID());
				ObjectMapper mapper = new ObjectMapper();
				RestTemplate restTemplate = new RestTemplate();
				HttpHeaders headers = new HttpHeaders();
				String uri = LoadConfig.getConfigValue("MOSIP_API_URL") + "/sendOTPAuthRequest";
				String request = "{\"mosipID\": " + otpData.getContactVal() + ", \"transactionID\": "
						+ sOTP.getTokenID() + ", \"otp\": " + otpData.getOtp() + "}";

				headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
				HttpEntity<String> entity = new HttpEntity<>(request, headers);
				ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, entity, String.class);
				String res = response.getBody();

				JSONObject resp = new JSONObject(res);

				if (resp.has("entity")) {
					String jEntity = resp.getString("entity");
					JSONObject jsonEntity = new JSONObject(jEntity);
					if (!jsonEntity.isNull("errors")) {
						JSONArray jsonError = jsonEntity.getJSONArray("errors");
						if (jsonError.length() > 0) {
							JSONObject errorNode = jsonError.getJSONObject(0);
							return "error-" + errorNode.getString("errorMessage");

						}

					} else if (!jsonEntity.isNull("response")) {
						JSONObject jsonResponse = jsonEntity.getJSONObject("response");
						if (jsonResponse.getBoolean("authStatus")) {
							return jsonResponse.getString("authToken");
						}
					}

				} else {
					return null;
				}

			}

		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			return null;
		}
		return null;
	}
}
