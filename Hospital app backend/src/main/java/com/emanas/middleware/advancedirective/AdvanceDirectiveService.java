package com.emanas.middleware.advancedirective;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import javax.xml.bind.PropertyException;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.emanas.middleware.config.ConfigurationUrl;
import com.emanas.middleware.models.HiuUser;
import com.emanas.middleware.policy.PolicyEnforcementService;
import com.emanas.middleware.utility.LoadConfig;
import com.emanas.middleware.utility.security.SecurityService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
@Service
public class AdvanceDirectiveService {

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	ConfigurationUrl config;

	@Autowired
	PolicyEnforcementService policyEnfServ;

	@Autowired
	SecurityService secServ;

		public @ResponseBody byte[] downloadPdfFile( String fileUUID, String type,
			   String filetype) throws IOException, PropertyException {
		   String FILE_URL=null;
		   FILE_URL = LoadConfig.getConfigValue("DOWNLOADPDF")  + fileUUID;
		    InputStream in = getClass().getResourceAsStream(FILE_URL);
			URL url=new URL(FILE_URL);
			return IOUtils.toByteArray(url.openStream());
		}


 	   public String downloadFile(@PathVariable(value= "fileuuid") String fileUUID,@PathVariable(value= "type") String type,
				@PathVariable(value = "filetype") String filetype) throws IOException, PropertyException {

			if (fileUUID != null && type.equalsIgnoreCase("view")) {
				if (filetype.equalsIgnoreCase("pdf")) {
					try {

						String url = LoadConfig.getConfigValue("DOWNLOADPDF") + fileUUID;

						String file = restTemplate.getForObject(url, String.class);

						return file;

					} catch (RestClientException e) {
						// TODO Auto-generated catch block
						return "E1036";
					}

				} else {
					return LoadConfig.getConfigValue("DOWNLOAD") + fileUUID;
				}

			}
			return filetype;
		}



	  public String viewAdvanceDirective(String request) throws IOException, PropertyException, ParseException {

			HiuUser hiu = secServ.getHIU();
			ObjectMapper objStringMapper = new ObjectMapper();
			String error = null;
			List<String> recordType = new ArrayList<String>();
			JsonNode node = objStringMapper.readValue(request, JsonNode.class);
			String patientId = node.get("patientId").asText();

			String role = node.get("hiu").get("individualRole").asText();
			JsonNode hitypesRequest = node.get("purpose");
			if ( role == null ) {
				return "E1008";
			}

			boolean isTypePresent = true;
			for (final JsonNode reqType : hitypesRequest) {
				recordType.add(reqType.asText());
			}
			if (!isTypePresent && recordType.contains("ADVANCEDIRECTIVE")) {
				return "E1009";
			}

			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
			format.setTimeZone(TimeZone.getTimeZone("UTC"));

			 HashMap<String, String> validateAccessRequest = policyEnfServ.validateAccessRequest(request,hiu.getMheAccessToken(),true);

			 if(validateAccessRequest.containsKey("error"))
			 {
				 return validateAccessRequest.get("error");
			 }

			ObjectMapper mapper = new ObjectMapper();
			if(validateAccessRequest.get("result").equalsIgnoreCase("Valid"))
			{
					String hiuStr = mapper.writeValueAsString(hiu);
					HttpHeaders headers = secServ.checkSecurity();
					HttpEntity<String> entity = new HttpEntity<String>(hiuStr, headers);

					ResponseEntity<String> res = restTemplate.exchange(
							LoadConfig.getConfigValue("GETLATESTAD")+ patientId, HttpMethod.GET,
							entity, String.class);
					String resAddAdHistory = null;
					if (!res.getBody() .equalsIgnoreCase("[]")) {


						HttpEntity<String> entity1 = new HttpEntity<String>(res.getBody(), headers);

						resAddAdHistory = restTemplate
								.postForObject(LoadConfig.getConfigValue("ACCESSHISTORYAD")
										+ hiu.getMheUserName() + "/" + hiu.getMheOrgname() + "/"
										+ hiu.getUuid(), entity1, String.class);
						if (resAddAdHistory != null) {

							return res.getBody();
						}else
						{
							return "E1034";
						}
					}


			}else
			{
				error =  "E1010";
			}
			return error;

			}

}
