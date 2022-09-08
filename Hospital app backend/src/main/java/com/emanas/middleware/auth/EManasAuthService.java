package com.emanas.middleware.auth;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;
import java.util.UUID;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.emanas.middleware.config.ConfigurationUrl;
import com.emanas.middleware.redis.model.SendOTP;
import com.emanas.middleware.redis.model.SendOTPDao;
import com.emanas.middleware.utility.LoadConfig;
import com.emanas.middleware.utility.cache.redis.Cache;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Primary
@Service("EMANAS")

public class EManasAuthService implements AuthService {

	Logger logger = LoggerFactory.getLogger(EManasAuthService.class);

	@Autowired
	ConfigurationUrl configUrl;

	@Autowired
	SendOTPDao sendOtpDao;

	static HashMap<String, Object> map1 = new HashMap<String, Object>();

	@Autowired
	Cache cacheServ;

	@Override
	public String sendOTPRequest(SendOTP otpData) {
		URL url;
		try {

			url = new URL(LoadConfig.getConfigValue("DEV_EMANAS_USER_API_URL") + "/sendOTP");

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Accept", "application/json");
			conn.setRequestProperty("User-Agent",
					"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.114 Safari/537.36");

			JSONObject input = new JSONObject();

			input.put("contactType", otpData.getContactType());
			input.put("contactVal", otpData.getContactVal());
			input.put("purpose", otpData.getPurpose());

			OutputStream os = conn.getOutputStream();
			os.write(input.toString().getBytes());
			os.flush();

			if (conn.getResponseCode() != 200) {
				logger.info("Connection Failed : HTTP error code : " + conn.getResponseCode());
				return conn.getResponseMessage();
			}

			Scanner sc = new Scanner(conn.getInputStream());
			String output = "";
			while (sc.hasNext()) {
				output += sc.nextLine();
			}

			JSONObject jObject = new JSONObject(output);

			ObjectMapper mapper = new ObjectMapper();
			SendOTP result = mapper.readValue(jObject.toString(), SendOTP.class);

			if (result.getStatus().equalsIgnoreCase("Generated")) {

				String id = UUID.randomUUID().toString();
				result.setTransactionID(id);

				sendOtpDao.saveSendOTP(result);

				conn.disconnect();
				sc.close();

				System.out.println(jObject.getString("contactVal"));
				logger.info("otp send to user at " + jObject.getString("contactVal") + " otp -> "
						+ jObject.getString("otp"));

				return id;
			} else {
				return "error-" + jObject.getString("message");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}



	@Override
	public String verifyAuthRequest(SendOTP otpData) {
		URL url;
		try {

			SendOTP sOTP = new SendOTP();

			try {

				sOTP = sendOtpDao.getOneSendOTP(otpData.getTransactionID());
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				return "E1001";
			}

			if (sOTP != null && sOTP.getTransactionID().equalsIgnoreCase(otpData.getTransactionID())
					&& sOTP.getOtp().equalsIgnoreCase(otpData.getOtp())) {

				System.out.println("otpData.getOtp()" + otpData.getOtp());

				url = new URL(LoadConfig.getConfigValue("DEV_EMANAS_USER_API_URL") + "/verifyOTP/");

				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setDoOutput(true);
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Content-Type", "application/json");
				conn.setRequestProperty("Accept", "application/json");
				conn.setRequestProperty("User-Agent",
						"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.114 Safari/537.36");

				JSONObject input = new JSONObject();
				input.put("tokenID", sOTP.getTokenID());
				input.put("contactType", otpData.getContactType());
				input.put("contactVal", otpData.getContactVal());
				input.put("purpose", otpData.getPurpose());
				input.put("otp", otpData.getOtp());
				OutputStream os = conn.getOutputStream();
				os.write(input.toString().getBytes());
				os.flush();

				if (conn.getResponseCode() != 200) {
					logger.info("Connection Failed : HTTP error code : " + conn.getResponseCode());
					return "E1200";
				}

				Scanner sc = new Scanner(conn.getInputStream());
				String output = "";
				while (sc.hasNext()) {
					output += sc.nextLine();
				}
				JSONObject jObject = new JSONObject(output);

				if (jObject.getInt("status") == 200) {
					return jObject.getString("message");
				} else {
					return "error-" + jObject.getString("message");
				}
			} else {
				return "E1007";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
