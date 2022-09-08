package com.emanas.middleware.ehr;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.emanas.middleware.bmr.ConfigList;
import com.emanas.middleware.models.Assessment;
import com.emanas.middleware.models.ChildVirtualFolder;
import com.emanas.middleware.models.CompositionReq;
import com.emanas.middleware.models.HiuUser;
import com.emanas.middleware.models.IPBMR;
import com.emanas.middleware.models.MedRestraint;
import com.emanas.middleware.models.OPBMR;
import com.emanas.middleware.models.PeriodicMonitoring;
import com.emanas.middleware.models.Restraint;
import com.emanas.middleware.models.Theraphy;
import com.emanas.middleware.models.UiData;
import com.emanas.middleware.utility.LoadConfig;
import com.emanas.middleware.utility.security.SecurityService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CreateEhrService {

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	SecurityService secServ;

	public String createRestraintService(String ehrData, HiuUser hiu) throws JsonProcessingException {

		ObjectMapper objStringMapper = new ObjectMapper();
		Restraint restraint = null;
		String patientID = null;
		try {

			JsonNode node = objStringMapper.readValue(ehrData, JsonNode.class);

			patientID = node.get("patientId").asText();
			JsonNode ehrdata = node.get("ehrData");

			restraint = objStringMapper.readValue(ehrdata.toString(), Restraint.class);

		} catch (JsonMappingException e1) {
			// TODO Auto-generated catch block

			return "E1001";

		} catch (JsonProcessingException e1) {

			// TODO Auto-generated catch block
			return "E1001";
		}

		CompositionReq compreq = new CompositionReq();

		String composition = "{\n" + "\"ctx/language\": \"en\",\n"
				+ "\"mhms_-_restraint_monitoring.v1/territory|code\": \"IN\",\n"
				+ "\"mhms_-_restraint_monitoring.v1/territory|terminology\": \"ISO_3166-1\",\n"
				+ "\"mhms_-_restraint_monitoring.v1/language|code\": \"en\",\n"
				+ "\"mhms_-_restraint_monitoring.v1/language|terminology\": \"ISO_639-1\",\n" +

				"\"mhms_-_restraint_monitoring.v1/composer|id_scheme\": \"UUID\",\n"
				+ "\"mhms_-_restraint_monitoring.v1/composer|id_namespace\": \"EHR.NETWORK\",\n"
				+ "\"mhms_-_restraint_monitoring.v1/context/start_time\": \"" + restraint.getRestraintStartDate()
				+ "\",\n" + "\"mhms_-_restraint_monitoring.v1/context/end_time\": \"" + restraint.getRestraintEndDate()
				+ "\",\n" + "\"mhms_-_restraint_monitoring.v1/context/health_care_facility|id\": \""
				+ restraint.getOrgId() + "\",\n"
				+ "\"mhms_-_restraint_monitoring.v1/context/health_care_facility|id_scheme\": \"UUID\",\n"
				+ "\"mhms_-_restraint_monitoring.v1/context/health_care_facility|id_namespace\": \"EHR.NETWORK\",\n"
				+ "\"mhms_-_restraint_monitoring.v1/context/setting|code\": \"228\",\n"
				+ "\"mhms_-_restraint_monitoring.v1/context/setting|value\": \"primary medical care\",\n"
				+ "\"mhms_-_restraint_monitoring.v1/context/setting|terminology\": \"openehr\",\n"
				+ "\"mhms_-_restraint_monitoring.v1/context/ehrc_metadata:0/confidentiality_level\": \"0\",\n"
				+ "\"mhms_-_restraint_monitoring.v1/context/ehrc_metadata:0/program_details/program_name\": \"KMHMS_Restraint\",\n"
				+ "\"mhms_-_restraint_monitoring.v1/context/ehrc_metadata:0/program_details/program_id\": \"004\",\n";

		StringBuilder str = new StringBuilder(composition);
		String patientId = "";
		String orgName = "";
		new Date();

		if (!restraint.getDuration().matches("[0-9]+")) {

			return "E1018";

			// return "Duration should contain only numbers";
		}

		if (restraint.getPeriodicArray().size() == 0) {

			return "E1019";

		}

		if (restraint.getDurationType().equalsIgnoreCase("Hours")
				|| restraint.getDurationType().equalsIgnoreCase("Minutes")) {
			str.append(
					"\"mhms_-_restraint_monitoring.v1/physical_restraint/any_event:0/restraint_details/duration/value\":\"PT"
							+ restraint.getDuration() + restraint.getDurationType().substring(0, 1) + "\",\n");
		} else {
			str.append(
					"\"mhms_-_restraint_monitoring.v1/physical_restraint/any_event:0/restraint_details/duration/value\":\"P"
							+ restraint.getDuration() + restraint.getDurationType().substring(0, 1) + "\",\n");

		}

		if (restraint.getInformedNR().equalsIgnoreCase("Yes")) {
			restraint.setInformedNR("true");
		} else if (restraint.getInformedNR().equalsIgnoreCase("No")) {
			restraint.setInformedNR("false");
		}
		str.append("\"mhms_-_restraint_monitoring.v1/physical_restraint/nominated_representative/whether_informed\":\""
				+ restraint.getInformedNR() + "\",\n");
		str.append("\"mhms_-_restraint_monitoring.v1/physical_restraint/nominated_representative/name\":\""
				+ restraint.getNrName() + "\",\n");

		if (restraint.getInjuries().equalsIgnoreCase("Yes")) {
			str.append(
					"\"mhms_-_restraint_monitoring.v1/physical_restraint/any_event:0/complications/injuries\":true,\n");

		} else {
			str.append(
					"\"mhms_-_restraint_monitoring.v1/physical_restraint/any_event:0/complications/injuries\":false,\n");
		}

		if (restraint.getLimb().equalsIgnoreCase("Yes")) {
			str.append(
					"\"mhms_-_restraint_monitoring.v1/physical_restraint/any_event:0/complications/limb_ischemia\":true,\n");

		} else {
			str.append(
					"\"mhms_-_restraint_monitoring.v1/physical_restraint/any_event:0/complications/limb_ischemia\":false,\n");
		}

		str.append("\"mhms_-_restraint_monitoring.v1/physical_restraint/any_event:0/complications/others\":\""
				+ restraint.getOtherComplications() + "\",\n");
		for (int i = 0; i < restraint.getPeriodicArray().size(); i++) {

			PeriodicMonitoring p = restraint.getPeriodicArray().get(i);

			String baseTempl = "\"mhms_-_restraint_monitoring.v1/physical_restraint/any_event:0/periodic_monitoring:";
			str.append(baseTempl + i + "/monitoring_date_time\":\"" + p.getMonitoringDateTime() + "\",\n");
			str.append(baseTempl + i + "/pulse|magnitude\":\"" + p.getPulse() + "\",\n");
			str.append(baseTempl + i + "/pulse|unit\":\"/min\",\n");
			str.append(baseTempl + i + "/temperature|magnitude\":\"" + p.getTemperature() + "\",\n");
			str.append(baseTempl + i + "/temperature|unit\":\"°F\",\n");
			str.append(baseTempl + i + "/respiratory_rate|magnitude\":\"" + p.getRespiratoryRate() + "\",\n");
			str.append(baseTempl + i + "/respiratory_rate|unit\":\"/min\",\n");
			if (p.getBloodSupply().equalsIgnoreCase("Yes")) {
				str.append(baseTempl + i + "/blood_supply_to_limbs\":true,\n");

			} else {
				str.append(baseTempl + i + "/blood_supply_to_limbs\":false,\n");
			}
			if (p.getBreath().equalsIgnoreCase("Yes")) {
				str.append(baseTempl + i + "/breating_problems\":true,\n");

			} else {
				str.append(baseTempl + i + "/breating_problems\":false,\n");

			}
			str.append(baseTempl + i + "/injuries\":\"" + p.getInjury() + "\",\n");

		}

		for (int i = 0; i < restraint.getMedicationArr().size(); i++) {
			MedRestraint med = restraint.getMedicationArr().get(i);

			String baseTempl = "\"mhms_-_restraint_monitoring.v1/physical_restraint/any_event:0/medications:";

			str.append(baseTempl + i + "/medication_item\":\"" + med.getMedicine() + "\",\n");
			str.append(baseTempl + i + "/route\":\"" + med.getRoute() + "\",\n");
			str.append(baseTempl + i + "/dose_amount\":\"" + med.getDose() + "\",\n");
			str.append(baseTempl + i + "/frequency\":\"" + med.getTotalDose() + "\",\n");
			str.append(baseTempl + i + "/total_dose_amount\":\"" + med.getDose() + "\",\n");
			str.append(baseTempl + i + "/side_effects\":\"" + med.getSideEffect() + "\",\n");

		}

		if (restraint.getPsychiatricDiagnosis() != null) {
			str.append("\"mhms_-_restraint_monitoring.v1/physical_restraint/any_event:0/psychiatric_diagnosis:0\":\""
					+ restraint.getPsychiatricDiagnosis() + "\",\n");

		}

		str.append(
				"\"mhms_-_restraint_monitoring.v1/physical_restraint/any_event:0/restraint_details/start_date_time\":\""
						+ restraint.getRestraintStartDate() + "\",\n");
		str.append(
				"\"mhms_-_restraint_monitoring.v1/physical_restraint/any_event:0/restraint_details/end_date_time\":\""
						+ restraint.getRestraintEndDate() + "\",\n");
		str.append("\"mhms_-_restraint_monitoring.v1/physical_restraint/any_event:0/restraint_details/reason\":\""
				+ restraint.getReason() + "\",\n");
		str.append("\"mhms_-_restraint_monitoring.v1/physical_restraint/organisation/organisation_name\":\""
				+ restraint.getOrgName() + "\",\n");
		str.append("\"mhms_-_restraint_monitoring.v1/physical_restraint/person_name/unstructured_name\":\""
				+ restraint.getInchargePsychiatrist() + "\",\n");

		String pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, new Locale("fr", "FR"));
		simpleDateFormat.format(new Date());

		str.append("\"mhms_-_restraint_monitoring.v1/composer|id\": \"" + hiu.getUuid() + "\",\n");
		str.append("\"mhms_-_restraint_monitoring.v1/context/start_time\": \"" + restraint.getRestraintStartDate()
				+ "\",\n");
		str.append("\"mhms_-_restraint_monitoring.v1/context/health_care_facility|name\": \"" + restraint.getOrgName()
				+ "\",\n");
		str.append("\"mhms_-_restraint_monitoring.v1/physical_restraint/organisation/organisation_name\": \""
				+ restraint.getOrgName() + "\",\n");
		str.append(
				"\"mhms_-_restraint_monitoring.v1/context/ehrc_metadata:0/program_details/professional_registration_id\": \""
						+ restraint.getMhpId().toString() + "\",\n");
		str.append(
				"\"mhms_-_restraint_monitoring.v1/context/ehrc_metadata:0/program_details/establishment_registration_id\": \""
						+ restraint.getOrgId().toString() + "\",\n");
		str.append("\"mhms_-_restraint_monitoring.v1/physical_restraint/any_event:0/setting\": \""
				+ restraint.getSetting() + "\"\n");

		str.append("}\n");
		ObjectMapper mapper = new ObjectMapper();
		Object objComp;
		try {
			objComp = mapper.readValue(str.toString(), Object.class);
		} catch (Exception e) {
			return "E1021";

		}
		compreq.setPersonId(patientID);
		compreq.setFormat("FLAT");
		compreq.setTemplateId("MHMS - Restraint monitoring.v1");
		compreq.setComposition(objComp);
		compreq.setAuthorization(hiu.getMheSessionToken());

		String pvID = null;

		String compide = mapper.writeValueAsString(compreq);
		HttpHeaders headers = secServ.checkSecurity();
		// headers.add( "Authorization", "Bearer "+hiu.getMheAccessToken());
		// headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);

		HttpEntity<String> entity = new HttpEntity<String>(compide, headers);

		String compid = restTemplate.postForObject(LoadConfig.getConfigValue("CREATECOMPOSITION"), entity,
				String.class);

		if ((compid != null && !compid.equalsIgnoreCase("Treatment cannot be posted")
				&& !compid.equalsIgnoreCase("Patient Not Found"))) {
			ChildVirtualFolder cvf = new ChildVirtualFolder();
			cvf.setPatientId(compreq.getPersonId());
			cvf.setToken(compreq.getAuthorization());

			UiData ui = new UiData();
			ui.setTemplateId(compreq.getTemplateId());
			ui.setCompositionid(compid);
			DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);

			LocalDate restraintStartdate = LocalDate.parse(restraint.getRestraintStartDate(), f);
			LocalDate restraintEnddate = LocalDate.parse(restraint.getRestraintEndDate(), f);
			Object[] uidata = new Object[] { ui };
			cvf.setOrgUUID(hiu.getOrguuid());
			cvf.setUuid(hiu.getUuid());
			cvf.setUidata(uidata);

			cvf.setStartDate(restraintStartdate.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli());
			cvf.setEndDate(restraintEnddate.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli());
			String cvfStr = mapper.writeValueAsString(cvf);

			HttpEntity<String> entity1 = new HttpEntity<String>(cvfStr, headers);

			pvID = restTemplate.postForObject(
					LoadConfig.getConfigValue("SAVECOMPOSITION") + "Restraint" + " " + "Monitoring", entity1,
					String.class);

			if (pvID != null) {
				String jsonString = "{\"vfid\":\"" + pvID + "\"," + " \"PatientID\":\"" + patientId + "\","
						+ " \"enddate\":\"" + restraint.getRestraintEndDate() + "\"," + " \"startdate\":\""
						+ restraint.getRestraintStartDate() + "\"," + " \"mheOrgname\":\"" + hiu.getMheOrgname() + "\","
						+ " \"mheUserName\":\"" + hiu.getMheOrgname() + "\"," + " \"orguuid\":\"" + hiu.getOrguuid()
						+ "\"," + " \"Type\":\"RM\"," + "\"docId\":\"" + restraint.getMhpId() + "\""

						+ "}";

				HttpEntity<String> entityMap = new HttpEntity<String>(jsonString, headers);

				Boolean saveassesement = restTemplate.postForObject(LoadConfig.getConfigValue("SAVEBMRFORRM"),
						entityMap, Boolean.class);

				if (saveassesement) {

					return "Treatment created successfully.\n";

				} else {

					return "E1017";

				}

			} else {
				pvID = compid;
			}
		} else {
			pvID = compid;
		}

		return "errorE1111 - " + pvID;

	}


	public String createAssessmentService(String ehrData, HiuUser hiu) throws JsonProcessingException {

		ObjectMapper objStringMapper = new ObjectMapper();
		Assessment assess = null;

		String patientID = null;
		try {

			JsonNode node = objStringMapper.readValue(ehrData, JsonNode.class);

			patientID = node.get("patientId").asText();
			JsonNode ehrdata = node.get("ehrData");

			assess = objStringMapper.readValue(ehrdata.toString(), Assessment.class);


		} catch (JsonMappingException e1) {
			// TODO Auto-generated catch block
			return "E1001";

		} catch (JsonProcessingException e1) {
			// TODO Auto-generated catch block
			return "E1001";
		}

		CompositionReq compreq = new CompositionReq();

		String composition = "{\n" + "\"ctx/language\": \"en\",\n"
				+ "\"mhms_-_psychological_assessment.v0/territory|code\": \"IN\",\n"
				+ "\"mhms_-_psychological_assessment.v0/territory|terminology\": \"ISO_3166-1\",\n"
				+ "\"mhms_-_psychological_assessment.v0/language|code\": \"en\",\n"
				+ "\"mhms_-_psychological_assessment.v0/language|terminology\": \"ISO_639-1\",\n" +

				"\"mhms_-_psychological_assessment.v0/composer|id_scheme\": \"UUID\",\n"
				+ "\"mhms_-_psychological_assessment.v0/composer|id_namespace\": \"EHR.NETWORK\",\n"
				+ "\"mhms_-_psychological_assessment.v0/context/health_care_facility|id\": \"" + assess.getOrgId()
				+ "\",\n" + "\"mhms_-_psychological_assessment.v0/context/health_care_facility|id_scheme\": \"UUID\",\n"
				+ "\"mhms_-_psychological_assessment.v0/context/health_care_facility|id_namespace\": \"EHR.NETWORK\",\n"
				+ "\"mhms_-_psychological_assessment.v0/context/setting|code\": \"228\",\n"
				+ "\"mhms_-_psychological_assessment.v0/context/setting|value\": \"primary medical care\",\n"
				+ "\"mhms_-_psychological_assessment.v0/context/setting|terminology\": \"openehr\",\n"
				+ "\"mhms_-_psychological_assessment.v0/context/ehrc_metadata:0/confidentiality_level\": \"0\",\n"
				+ "\"mhms_-_psychological_assessment.v0/context/ehrc_metadata:0/program_details/program_name\": \"KMHMS_Assessment\",\n"
				+ "\"mhms_-_psychological_assessment.v0/context/ehrc_metadata:0/program_details/program_id\": \"002\",\n";

		StringBuilder str = new StringBuilder(composition);
		String patientId = "";
		String orgName = "";

		str.append(
				"\"mhms_-_psychological_assessment.v0/service_request/current_activity:0/service_name\": \"Referral\",\n");
		str.append("\"mhms_-_psychological_assessment.v0/service_request/current_activity:0/reason_for_request:0\": \""
				+ assess.getReferralReason() + "\",\n");
		str.append("\"mhms_-_psychological_assessment.v0/service_request/current_activity:0/reason_description\": \""
				+ assess.getReferralNote() + "\",\n");
		str.append(
				"\"mhms_-_psychological_assessment.v0/service_request/current_activity:0/timing\": \"DEFAULT_TIMING\",\n");
		str.append("\"mhms_-_psychological_assessment.v0/service_request/narrative\": \"DEFAULT_NARRATIVE\",\n");
		str.append("\"mhms_-_psychological_assessment.v0/service_request/person_name/unstructured_name\": \""
				+ assess.getReferredBy() + "\",\n");

		str.append("\"mhms_-_psychological_assessment.v0/psychological_assessment/assessed_by/assessor_name\": \""
				+ assess.getAssessor_name() + "\",\n");
		str.append(
				"\"mhms_-_psychological_assessment.v0/psychological_assessment/assessed_by/assessor_qualification\": \""
						+ assess.getAssessorQualification() + "\",\n");
		str.append("\"mhms_-_psychological_assessment.v0/education_summary/highest_level_completed\": \""
				+ assess.getEducation() + "\",\n");
		str.append("\"mhms_-_psychological_assessment.v0/occupation_summary/occupation_record/job_title_role\": \""
				+ assess.getOccupation() + "\",\n");

		str.append("\"mhms_-_psychological_assessment.v0/psychological_assessment/salient_behavioural_observation\": \""
				+ assess.getSilentBehaviouralObservation() + "\",\n");
		str.append("\"mhms_-_psychological_assessment.v0/psychological_assessment/background_information\": \""
				+ assess.getBackgroundInformation() + "\",\n");
		str.append(
				"\"mhms_-_psychological_assessment.v0/psychological_assessment/test_scale:0/test_scale_administered\": \""
						+ assess.getTestScales() + "\",\n");
		str.append("\"mhms_-_psychological_assessment.v0/psychological_assessment/test_scale:0/score\": \""
				+ assess.getTestScores().toString() + "\",\n");
		str.append("\"mhms_-_psychological_assessment.v0/psychological_assessment/impression\": \""
				+ assess.getImpression() + "\",\n");
		str.append("\"mhms_-_psychological_assessment.v0/psychological_assessment/recommendation\": \""
				+ assess.getRecommend() + "\",\n");
		str.append("\"mhms_-_psychological_assessment.v0/psychological_assessment/language_tested_in\": \""
				+ assess.getLanguageTestedIn() + "\",\n");
		str.append(
				"\"mhms_-_psychological_assessment.v0/psychological_assessment/information_received/reliability\": \""
						+ assess.getReliability() + "\",\n");
		str.append("\"mhms_-_psychological_assessment.v0/psychological_assessment/information_received/adequacy\": \""
				+ assess.getAdequacy() + "\",\n");

		if (assess.getSupervisor_name() != null || assess.getSupervisor_name() != "") {

			str.append(
					"\"mhms_-_psychological_assessment.v0/psychological_assessment/supervised_by/supervisor_name\": \""
							+ assess.getSupervisor_name() + "\",\n");
			str.append(
					"\"mhms_-_psychological_assessment.v0/psychological_assessment/supervised_by/supervisor_qualification\": \""
							+ assess.getSupervisorQualification() + "\",\n");

		}

		for (int i = 0; i < assess.getInformantArray().size(); i++) {

			str.append("\"mhms_-_psychological_assessment.v0/psychological_assessment/information_received/informant:"
					+ i + "/informant_name\": \"" + assess.getInformantArray().get(i).getInformant() + "\",\n");
			str.append("\"mhms_-_psychological_assessment.v0/psychological_assessment/information_received/informant:"
					+ i + "/relationship\": \"" + assess.getInformantArray().get(i).getRelationship() + "\",\n");

		}

		String pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, new Locale("fr", "FR"));
		String date = simpleDateFormat.format(new Date());

		str.append("\"mhms_-_psychological_assessment.v0/composer|id\": \"" + hiu.getUuid() + "\",\n");
		str.append("\"mhms_-_psychological_assessment.v0/context/health_care_facility|name\": \"" + assess.getOrgName()
				+ "\",\n");

		str.append("\"mhms_-_psychological_assessment.v0/context/start_time\": \"" + date + "\",\n");
		str.append("\"mhms_-_psychological_assessment.v0/context/health_care_facility|name\": \"" + assess.getOrgName()
				+ "\",\n");
		str.append(
				"\"mhms_-_psychological_assessment.v0/context/ehrc_metadata:0/program_details/professional_registration_id\": \""
						+ assess.getMhpId().toString() + "\",\n");
		str.append(
				"\"mhms_-_psychological_assessment.v0/context/ehrc_metadata:0/program_details/establishment_registration_id\": \""
						+ assess.getOrgId().toString() + "\"\n");

		str.append("}\n");

		ObjectMapper mapper = new ObjectMapper();
		Object objComp;
		try {
			objComp = mapper.readValue(str.toString(), Object.class);
		} catch (Exception e) {

			return "E1021";

		}
		compreq.setPersonId(patientID);
		compreq.setFormat("FLAT");
		compreq.setTemplateId("MHMS - Psychological assessment.v0");
		compreq.setComposition(objComp);
		compreq.setAuthorization(hiu.getMheSessionToken());

		String pvID = null;

		String compide = mapper.writeValueAsString(compreq);
		HttpHeaders headers = secServ.checkSecurity();
		// headers.add( "Authorization", "Bearer "+hiu.getMheAccessToken());
		// headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);

		HttpEntity<String> entity = new HttpEntity<String>(compide, headers);

		String compid = restTemplate.postForObject(LoadConfig.getConfigValue("CREATECOMPOSITION"), entity,
				String.class);

		if ((compid != null && !compid.equalsIgnoreCase("Treatment cannot be posted")
				&& !compid.equalsIgnoreCase("Patient Not Found"))) {
			ChildVirtualFolder cvf = new ChildVirtualFolder();
			cvf.setPatientId(compreq.getPersonId());
			cvf.setToken(compreq.getAuthorization());

			UiData ui = new UiData();
			ui.setTemplateId(compreq.getTemplateId());
			ui.setCompositionid(compid);

			Object[] uidata = new Object[] { ui };
			cvf.setOrgUUID(hiu.getOrguuid());
			cvf.setUuid(hiu.getUuid());
			cvf.setUidata(uidata);

			LocalDate assStartdate = LocalDate.now();
			LocalDate assEnddate = LocalDate.now();

			cvf.setStartDate(assStartdate.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli());
			cvf.setEndDate(assEnddate.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli());

			String cvfStr = mapper.writeValueAsString(cvf);

			HttpEntity<String> entity1 = new HttpEntity<String>(cvfStr, headers);

			pvID = restTemplate.postForObject(LoadConfig.getConfigValue("SAVECOMPOSITION") + "Assessment", entity1,
					String.class);

			if (pvID != null) {

				String jsonString = "{\"vfid\":\"" + pvID + "\"," + " \"PatientID\":\"" + patientId + "\","
						+ " \"mheOrgname\":\"" + hiu.getMheOrgname() + "\"," + " \"mheUserName\":\""
						+ hiu.getMheOrgname() + "\"," + " \"orguuid\":\"" + hiu.getOrguuid() + "\","
						+ " \"Type\":\"Assessment\"," + "\"docId\":\"" + assess.getMhpId() + "\"" + "}";

				HttpEntity<String> entityMap = new HttpEntity<String>(jsonString, headers);

				Boolean saveassesement = restTemplate.postForObject(LoadConfig.getConfigValue("SAVEBMRFORRM"),
						entityMap, Boolean.class);

				if (saveassesement) {

					return "Treatment created successfully.";

				} else {

					return "E1017";

				}

			} else {
				pvID = compid;
			}
		} else {
			pvID = compid;
		}

		return "error -" + pvID;

	}

	public String createTherapyService(String ehrData, HiuUser hiu) throws JsonProcessingException {

		ObjectMapper objStringMapper = new ObjectMapper();
		Theraphy ther = null;

		String patientID = null;
		try {

			JsonNode node = objStringMapper.readValue(ehrData, JsonNode.class);

			patientID = node.get("patientId").asText();
			JsonNode ehrdata = node.get("ehrData");

			ther = objStringMapper.readValue(ehrdata.toString(), Theraphy.class);

		} catch (JsonMappingException e1) {
			// TODO Auto-generated catch block
			return "E1001";

		} catch (JsonProcessingException e1) {
			// TODO Auto-generated catch block
			return "E1001";
		}

		CompositionReq compreq = new CompositionReq();

		if (!ther.getSessionNumber().matches("[0-9]+")) {

			return "E1016";

		}

		String composition = "{\n" + "\"ctx/language\": \"en\",\n"
				+ "\"mhms_-_therapy_reporting.v0/territory|code\": \"IN\",\n"
				+ "\"mhms_-_therapy_reporting.v0/territory|terminology\": \"ISO_3166-1\",\n"
				+ "\"mhms_-_therapy_reporting.v0/language|code\": \"en\",\n"
				+ "\"mhms_-_therapy_reporting.v0/language|terminology\": \"ISO_639-1\",\n" +

				"\"mhms_-_therapy_reporting.v0/composer|id_scheme\": \"UUID\",\n"
				+ "\"mhms_-_therapy_reporting.v0/composer|id_namespace\": \"EHR.NETWORK\",\n"
				+ "\"mhms_-_therapy_reporting.v0/context/health_care_facility|id\": \"" + ther.getOrgId() + "\",\n"
				+ "\"mhms_-_therapy_reporting.v0/context/health_care_facility|id_scheme\": \"UUID\",\n"
				+ "\"mhms_-_therapy_reporting.v0/context/health_care_facility|id_namespace\": \"EHR.NETWORK\",\n"
				+ "\"mhms_-_therapy_reporting.v0/context/setting|code\": \"228\",\n"
				+ "\"mhms_-_therapy_reporting.v0/context/setting|value\": \"primary medical care\",\n"
				+ "\"mhms_-_therapy_reporting.v0/context/setting|terminology\": \"openehr\",\n"
				+ "\"mhms_-_therapy_reporting.v0/context/ehrc_metadata:0/confidentiality_level\": \"0\",\n"
				+ "\"mhms_-_therapy_reporting.v0/context/ehrc_metadata:0/program_details/program_name\": \"KMHMS_Therapy\",\n"
				+ "\"mhms_-_therapy_reporting.v0/context/ehrc_metadata:0/program_details/program_id\": \"003\",\n";

		StringBuilder str = new StringBuilder(composition);

		str.append("\"mhms_-_therapy_reporting.v0/education_summary/highest_level_completed\": \"" + ther.getEducation()
				+ "\",\n");
		str.append("\"mhms_-_therapy_reporting.v0/occupation_summary/occupation_record/job_title_role\": \""
				+ ther.getOccupation() + "\",\n");
		str.append("\"mhms_-_therapy_reporting.v0/psychological_therapy/type_of_therapy\": \"" + ther.getTherapyType()
				+ "\",\n");
		str.append("\"mhms_-_therapy_reporting.v0/psychological_therapy/therapy_method|code\":\"at0006\",\n");
		str.append("\"mhms_-_therapy_reporting.v0/psychological_therapy/therapy_method|value\": \""
				+ ther.getTherapyMethod() + "\",\n");
		str.append("\"mhms_-_therapy_reporting.v0/psychological_therapy/therapy_method|terminology\": \"local\",\n");
		str.append("\"mhms_-_therapy_reporting.v0/psychological_therapy/session_number\": \"" + ther.getSessionNumber()
				+ "\",\n");
		str.append("\"mhms_-_therapy_reporting.v0/psychological_therapy/objective_of_session\": \"" + ther.getOsession()
				+ "\",\n");
		str.append("\"mhms_-_therapy_reporting.v0/psychological_therapy/issues_themes_discussed:0\": \""
				+ ther.getKeyIssue() + "\",\n");
		str.append("\"mhms_-_therapy_reporting.v0/psychological_therapy/psychiatric_diagnosis:0\": \""
				+ ther.getPsychiatricDiagnosis() + "\",\n");

		str.append("\"mhms_-_therapy_reporting.v0/psychological_therapy/therapist_observations:0\":\""
				+ ther.getObservationReflection() + "\",\n");
		str.append("\"mhms_-_therapy_reporting.v0/psychological_therapy/therapy_technique_used\":\""
				+ ther.getTherapyTechnique() + "\",\n");
		str.append("\"mhms_-_therapy_reporting.v0/psychological_therapy/therapist/therapist_name\":\""
				+ ther.getTherapistName() + "\",\n");
		str.append("\"mhms_-_therapy_reporting.v0/psychological_therapy/therapist/qualification\":\""
				+ ther.getTherapistQualification() + "\",\n");
		str.append("\"mhms_-_therapy_reporting.v0/psychological_therapy/supervisor/supervisor_name\":\""
				+ ther.getSupervisorName() + "\",\n");
		str.append("\"mhms_-_therapy_reporting.v0/psychological_therapy/supervisor/qualification\":\""
				+ ther.getSupervisorQualification() + "\",\n");
		str.append(
				"\"mhms_-_therapy_reporting.v0/psychological_therapy/ism_transition/current_state|code\":\"532\",\n");
		str.append(
				"\"mhms_-_therapy_reporting.v0/psychological_therapy/ism_transition/current_state|value\": \"completed\",\n");
		str.append(
				"\"mhms_-_therapy_reporting.v0/psychological_therapy/ism_transition/current_state|terminology\":\"openehr\",\n");
		str.append(
				"\"mhms_-_therapy_reporting.v0/psychological_therapy/ism_transition/careflow_step|code\":\"at0025\",\n");
		str.append(
				"\"mhms_-_therapy_reporting.v0/psychological_therapy/ism_transition/careflow_step|value\":\"Completed\",\n");
		str.append(
				"\"mhms_-_therapy_reporting.v0/psychological_therapy/ism_transition/careflow_step|terminology\":\"local\",\n");

		if (ther.getFollowUp() != null) {
			str.append("\"mhms_-_therapy_reporting.v0/service_request/current_activity:0/service_name\":\""
					+ ther.getNextSession() + "\",\n");
			str.append("\"mhms_-_therapy_reporting.v0/service_request/current_activity:0/service_due\":\""
					+ ther.getFollowUp() + "\",\n");
			str.append(
					"\"mhms_-_therapy_reporting.v0/service_request/current_activity:0/timing\":\"DEFAULT_TIMING\",\n");
			str.append("\"mhms_-_therapy_reporting.v0/service_request/narrative\":\"DEFAULT_NARRATIVE\",\n");

		}

		String pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, new Locale("fr", "FR"));
		String date = simpleDateFormat.format(new Date());

		str.append("\"mhms_-_therapy_reporting.v0/composer|id\": \"" + hiu.getUuid() + "\",\n");
		str.append(
				"\"mhms_-_therapy_reporting.v0/context/health_care_facility|name\": \"" + ther.getOrgName() + "\",\n");

		str.append("\"mhms_-_therapy_reporting.v0/context/start_time\": \"" + date + "\",\n");
		str.append(
				"\"mhms_-_therapy_reporting.v0/context/ehrc_metadata:0/program_details/professional_registration_id\": \""
						+ ther.getMhpId().toString() + "\",\n");
		str.append(
				"\"mhms_-_therapy_reporting.v0/context/ehrc_metadata:0/program_details/establishment_registration_id\": \""
						+ ther.getOrgId().toString() + "\"\n");

		str.append("}\n");

		ObjectMapper mapper = new ObjectMapper();
		Object objComp;
		try {
			objComp = mapper.readValue(str.toString(), Object.class);
		} catch (Exception e) {

			return "E1021";

		}
		compreq.setPersonId(patientID);
		compreq.setFormat("FLAT");
		compreq.setTemplateId("MHMS - Therapy reporting.v0");
		compreq.setComposition(objComp);
		compreq.setAuthorization(hiu.getMheSessionToken());

		String pvID = null;

		String compide = mapper.writeValueAsString(compreq);
		HttpHeaders headers = secServ.checkSecurity();
		// headers.add( "Authorization", "Bearer "+hiu.getMheAccessToken());
		// headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);

		HttpEntity<String> entity = new HttpEntity<String>(compide, headers);

		String compid = restTemplate.postForObject(LoadConfig.getConfigValue("CREATECOMPOSITION"), entity,
				String.class);

		if ((compid != null && !compid.equalsIgnoreCase("Treatment cannot be posted")
				&& !compid.equalsIgnoreCase("Patient Not Found"))) {
			ChildVirtualFolder cvf = new ChildVirtualFolder();
			cvf.setPatientId(compreq.getPersonId());
			cvf.setToken(compreq.getAuthorization());

			UiData ui = new UiData();
			ui.setTemplateId(compreq.getTemplateId());
			ui.setCompositionid(compid);

			Object[] uidata = new Object[] { ui };
			cvf.setOrgUUID(hiu.getOrguuid());
			cvf.setUuid(hiu.getUuid());
			cvf.setUidata(uidata);

			LocalDate therapyStartdate = LocalDate.now();
			LocalDate therapyEnddate = LocalDate.now();

			cvf.setStartDate(therapyStartdate.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli());
			cvf.setEndDate(therapyEnddate.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli());

			String cvfStr = mapper.writeValueAsString(cvf);

			HttpEntity<String> entity1 = new HttpEntity<String>(cvfStr, headers);

			pvID = restTemplate.postForObject(LoadConfig.getConfigValue("SAVECOMPOSITION") + "Therapy", entity1,
					String.class);

			if (pvID != null) {

				String jsonString = "{\"vfid\":\"" + pvID + "\"," + " \"PatientID\":\"" + patientID + "\","
						+ " \"mheOrgname\":\"" + hiu.getMheOrgname() + "\"," + " \"mheUserName\":\""
						+ hiu.getMheOrgname() + "\"," + " \"orguuid\":\"" + hiu.getOrguuid() + "\","
						+ " \"Type\":\"THERAPY\"," + "\"docId\":\"" + ther.getMhpId() + "\"" + "}";

				HttpEntity<String> entityMap = new HttpEntity<String>(jsonString, headers);

				Boolean saveassesement = restTemplate.postForObject(LoadConfig.getConfigValue("SAVEBMRFORRM"),
						entityMap, Boolean.class);

				if (saveassesement) {

					return "Treatment created successfully.\n";

				} else {

					return "E1017";

					// return "Treatment could not be created.\n";
				}

			} else {
				pvID = compid;
			}
		} else {
			pvID = compid;
		}

		return "error - " + pvID;

	}

	public String createIPBMRService(IPBMR ip, HiuUser hiu, String patientID) {

		CompositionReq compreq = new CompositionReq();
		String bmrRequest = "{\n" + "\"ctx/language\": \"en\",\n" + "\"mhms_-_ip_bmr.v1/territory|code\": \"IN\",\n"
				+ "\"mhms_-_ip_bmr.v1/territory|terminology\": \"ISO_3166-1\",\n"
				+ "\"mhms_-_ip_bmr.v1/language|code\": \"en\",\n"
				+ "\"mhms_-_ip_bmr.v1/language|terminology\": \"ISO_639-1\",\n" +

				"\"mhms_-_ip_bmr.v1/composer|id_scheme\": \"UUID\",\n"
				+ "\"mhms_-_ip_bmr.v1/composer|id_namespace\": \"EHR.NETWORK\",\n" +

				"\"mhms_-_ip_bmr.v1/context/health_care_facility|id\": \"123456-123\",\n"
				+ "\"mhms_-_ip_bmr.v1/context/health_care_facility|id_scheme\": \"UUID\",\n"
				+ "\"mhms_-_ip_bmr.v1/context/health_care_facility|id_namespace\": \"EHR.NETWORK\",\n"
				+ "\"mhms_-_ip_bmr.v1/context/setting|code\": \"228\",\n"
				+ "\"mhms_-_ip_bmr.v1/context/setting|value\": \"primary medical care\",\n"
				+ "\"mhms_-_ip_bmr.v1/context/setting|terminology\": \"openehr\",\n"
				+ "\"mhms_-_ip_bmr.v1/context/ehrc_metadata:0/confidentiality_level\": \"0\",\n"
				+ "\"mhms_-_ip_bmr.v1/context/ehrc_metadata:0/program_details/program_name\": \"KMHMS_IPBMR\",\n"
				+ "\"mhms_-_ip_bmr.v1/context/ehrc_metadata:0/program_details/program_id\": \"0005\",\n";

		StringBuilder str = new StringBuilder(bmrRequest);

		String pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, new Locale("fr", "FR"));

		if (ip.getCapacity() != null && !ip.getCapacity().equalsIgnoreCase("")) {

			str.append("\"mhms_-_ip_bmr.v1/service_request/current_activity:0/timing\": \"DEFAULT_TIMING\",\n"
					+ "\"mhms_-_ip_bmr.v1/service_request/current_activity:0/timing|formalism\": \"DEFAULT_FORMALISM\",\n"
					+ "\"mhms_-_ip_bmr.v1/service_request/narrative\": \"DEFAULT_NARRATIVE\" ,\n ");

			restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
			// ResponseEntity<String> objResponse = null;
			ConfigList[] response = restTemplate.getForObject(LoadConfig.getConfigValue("GETCONFIGDETAILSBYNAME"),
					ConfigList[].class);

			simpleDateFormat.format(new Date());

			String description = "";
			int addDate = 0;
			String service_duedate = "";
			Calendar c = Calendar.getInstance();
			for (int i = 0; i < response.length; i++) {

				if (response[i].getValue().equalsIgnoreCase(ip.getCapacity())) {
					description = response[i].getKey().split("_")[1];
					addDate = Integer.parseInt(response[i].getKey().split("_")[2]);
					c.add(Calendar.DAY_OF_MONTH, addDate);
					service_duedate = simpleDateFormat.format(c.getTime());
					str.append("\"mhms_-_ip_bmr.v1/service_request/current_activity:0/service_name\":\""
							+ ip.getCapacity() + "\",\n"
							+ "\"mhms_-_ip_bmr.v1/service_request/current_activity:0/service_due\":\"" + service_duedate
							+ "\",\n" + "\"mhms_-_ip_bmr.v1/mental_capacity/capacity_description\":\"" + description
							+ " \",\n");

				}

			}

			if (ip.getDiagnosisDisplay() != null && ip.getDiagnosisDisplay().size() > 0) {
				for (int i = 0; i < ip.getDiagnosisDisplay().size(); i++) {

					str.append("\"mhms_-_ip_bmr.v1/problem_diagnosis:" + i + "/clinical_description\": \""
							+ ip.getDiagnosisDisplay().get(i).getDescription() + "\",\n");
					str.append("\"mhms_-_ip_bmr.v1/problem_diagnosis:" + i + "/diagnostic_certainty\": \""
							+ ip.getDiagnosisDisplay().get(i).getDiagnosisType() + "\",\n");
					str.append("\"mhms_-_ip_bmr.v1/problem_diagnosis:" + i
							+ "/problem_diagnosis_name|terminology\": \"ICD 10\",\n");
					str.append("\"mhms_-_ip_bmr.v1/problem_diagnosis:" + i + "/problem_diagnosis_name|value\":\""
							+ ip.getDiagnosisDisplay().get(i).getDescription() + "\",\n");
					str.append("\"mhms_-_ip_bmr.v1/problem_diagnosis:" + i + "/problem_diagnosis_name|code\":\""
							+ ip.getDiagnosisDisplay().get(i).getIcdCode() + "\",\n");

				}

			}

			if (ip.getMedicationDisplay() != null && ip.getMedicationDisplay().size() > 0) {
				for (int i = 0; i < ip.getMedicationDisplay().size(); i++) {

					if (!ip.getMedicationDisplay().get(i).getMedDuration1().matches("[0-9]+")) {
						return "E1018";
					}

					str.append("\"mhms_-_ip_bmr.v1/medication_order/order:" + i
							+ "/therapeutic_direction/dosage/timing_-_daily/timing_description\":\""
							+ ip.getMedicationDisplay().get(i).getMedTime() + "\",\n");
					str.append("\"mhms_-_ip_bmr.v1/medication_order/order:" + i + "/timing\":\"DEFAULT_TIMING\",\n");
					str.append("\"mhms_-_ip_bmr.v1/medication_order/order:" + i
							+ "/timing|formalism\":\"DEFAULT_FORMALISM\",\n");
					str.append("\"mhms_-_ip_bmr.v1/medication_order/narrative\": \"DEFAULT_NARRATIVE\",\n");
					str.append("\"mhms_-_ip_bmr.v1/medication_order/order:" + i
							+ "/therapeutic_direction/direction_duration/value\":\"" + "P"
							+ ip.getMedicationDisplay().get(i).getMedDuration1()
							+ ip.getMedicationDisplay().get(i).getMedDuration2().substring(0, 1) + "\",\n");

					str.append("\"mhms_-_ip_bmr.v1/medication_order/order:" + i + "/additional_instruction\":\""
							+ ip.getMedicationDisplay().get(i).getMedRemarks() + "\",\n");

					str.append("\"mhms_-_ip_bmr.v1/medication_order/order:" + i + "/medication_item\":\""
							+ ip.getMedicationDisplay().get(i).getMedName() + "_"
							+ ip.getMedicationDisplay().get(i).getMedDosage() + "\",\n");

				}

			}

			if (ip.getMental_status() != null) {
				str.append("\"mhms_-_ip_bmr.v1/clinical_history/clinical_history\": \""

						+ ip.getMental_status() + "\",\n");
			}
			if (ip.getCourse() != null) {
				str.append(
						"\"mhms_-_ip_bmr.v1/progress_note/any_event:0/progress_note\": \"" + ip.getCourse() + "\",\n");
			}
			if (ip.getPast_prescription() != null) {
				str.append("\"mhms_-_ip_bmr.v1/mhms_summaries/any_event:0/past_prescriptions\": \""
						+ ip.getPast_prescription() + "\",\n");
			}
			if (ip.getHistory() != null) {
				str.append("\"mhms_-_ip_bmr.v1/story_history/any_event:0/story:0\": \"" + ip.getHistory() + "\",\n");
			}
			if (ip.getInvestigation() != null) {
				str.append("\"mhms_-_ip_bmr.v1/mhms_summaries/any_event:0/summary_of_investigation\": \""
						+ ip.getInvestigation() + "\",\n");
			}
			if (ip.getPhysical_exam() != null) {
				str.append("\"mhms_-_ip_bmr.v1/physical_examination_findings/any_event:0/interpretation:0\": \""
						+ ip.getPhysical_exam() + "\",\n");
			}

			if (ip.getVitals() != null) {
				str.append("\"mhms_-_ip_bmr.v1/mhms_summaries/any_event:0/summary_of_vitals\": \"" + ip.getVitals()
						+ "\",\n");
			}

			if (ip.getComplaintDisplay() != null && ip.getComplaintDisplay().size() > 0) {

				for (int i = 0; i < ip.getComplaintDisplay().size(); i++) {

					str.append("\"mhms_-_op_bmr.v2/story_history/any_event:0/symptom_sign:" + i + "/pattern\":\""
							+ ip.getComplaintDisplay().get(i).getDuration() + "\",\n");
					str.append("\"mhms_-_op_bmr.v2/story_history/any_event:0/symptom_sign:" + i
							+ "/symptom_sign_name\": \"" + ip.getComplaintDisplay().get(i).getComplaint() + "\",\n");

				}

			}

			String date = simpleDateFormat.format(new Date());
			str.append("\"mhms_-_ip_bmr.v1/composer|id\":\"" + hiu.getUuid() + "\",\n");
			str.append("\"mhms_-_ip_bmr.v1/context/start_time\":\"" + date + "\",\n");
			str.append("\"mhms_-_ip_bmr.v1/context/health_care_facility|name\":\"" + ip.getOrgName() + "\",\n");
			str.append("\"mhms_-_ip_bmr.v1/context/ehrc_metadata:0/program_details/professional_registration_id\":\""
					+ ip.getMhpId() + "\",\n");
			str.append("\"mhms_-_ip_bmr.v1/context/ehrc_metadata:0/program_details/establishment_registration_id\":\""
					+ ip.getOrgId() + "\",\n");
			str.append("\"mhms_-_ip_bmr.v1/context/health_care_facility|name\":\"" + ip.getOrgName() + "\"\n");
			str.append("}\n");

		}
		ObjectMapper mapper = new ObjectMapper();
		Object objComp;
		try {
			objComp = mapper.readValue(str.toString(), Object.class);
		} catch (Exception e) {

			return "E1021";

		}
		compreq.setPersonId(patientID);
		compreq.setFormat("FLAT");
		compreq.setTemplateId("MHMS - ip_bmr.v1");
		compreq.setComposition(objComp);
		compreq.setAuthorization(hiu.getMheSessionToken());

		HttpHeaders headers = secServ.checkSecurity();
		// headers.add( "Authorization", "Bearer "+hiu.getMheAccessToken());
		// headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
		String pvID = null;

		String compide = null;
		try {
			compide = mapper.writeValueAsString(compreq);

		} catch (JsonProcessingException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		HttpEntity<String> entity = new HttpEntity<String>(compide, headers);
		String compid = restTemplate.postForObject(LoadConfig.getConfigValue("CREATECOMPOSITION"), entity,
				String.class);

		if ((compid != null && !compid.equalsIgnoreCase("Treatment cannot be posted")
				&& !compid.equalsIgnoreCase("Patient Not Found"))) {

			ChildVirtualFolder cvf = new ChildVirtualFolder();
			cvf.setPatientId(compreq.getPersonId());
			cvf.setToken(compreq.getAuthorization());

			UiData ui = new UiData();
			ui.setTemplateId(compreq.getTemplateId());
			ui.setCompositionid(compid);

			Object[] uidata = new Object[] { ui };
			cvf.setOrgUUID(hiu.getOrguuid());
			cvf.setUuid(hiu.getUuid());
			cvf.setUidata(uidata);
			Locale.setDefault(Locale.US);
			DateTimeFormatter f = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss z yyyy");

			if (ip.getAdmitDate() != null) {
				LocalDate gadmitStartdate = LocalDate.parse(ip.getAdmitDate().toString(), f);
				cvf.setStartDate(gadmitStartdate.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli());
			}

			String cvfStr = null;
			try {
				cvfStr = mapper.writeValueAsString(cvf);
			} catch (JsonProcessingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			HttpEntity<String> entity1 = new HttpEntity<String>(cvfStr, headers);

			try {

				pvID = restTemplate.postForObject(LoadConfig.getConfigValue("SAVEIPPATIENTS"), entity1, String.class);

				if (ip.getGuardianName() != null && ip.getGuardianPhoneNumber() != null) {

					String enStr = "{ \"gName\": \"" + ip.getGuardianName() + "\"  ,\"patientID\": \""
							+ ip.getPatientID() + "\"  , \"gRelation\" : \"" + ip.getGuardianPhoneNumber() + "\" " + ","
							+ "\"gAdmitdate\" : \"" + ip.getAdmitDate() + "\" }";
					HttpEntity<String> enPatient = new HttpEntity<String>(enStr, headers);
					Boolean updateGuardianDetails = restTemplate.postForObject(
							LoadConfig.getConfigValue("UPDATEGUARDIANPATIENT"), enPatient, Boolean.class);

				}

				if (pvID != null) {

					hiu.setMheUserName(ip.getMhpId().toString());

					if (ip.getAdmitDate() != null) {

						DateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy");
						Date d = formatter.parse(ip.getAdmitDate().toString());

						hiu.setLastUpdatedTime(d);
					}

					String hiuStrign = mapper.writeValueAsString(hiu);

					HttpEntity<String> entityMap = new HttpEntity<String>(hiuStrign, headers);

					Boolean saveassesement = restTemplate.postForObject(LoadConfig.getConfigValue("SAVEBMRFORIP")
							+ ip.getCapacity() + "/" + ip.getPatientID() + "/" + pvID, entityMap, Boolean.class);

					if (saveassesement) {

						if (ip.getDischargeType() != null) {

							return createIPDischargeService(ip, hiu, patientID);

						} else {
							return "Treatment created successfully.  \n";
						}

					} else {

						return "E1017";

					}

				} else {
					pvID = compid;
				}
			} catch (Exception e) {

				return "E1022";

			}
		} else {
			pvID = compid;
		}

		return pvID;

		// return pvID;

	}

	public String createIPService(String ehrData, HiuUser hiu) {
		ObjectMapper objStringMapper = new ObjectMapper();
		IPBMR ip = null;
		String patientID = null;
		try {

			JsonNode node = objStringMapper.readValue(ehrData, JsonNode.class);

			patientID = node.get("patientId").asText();
			JsonNode ehrdata = node.get("ehrData");

			ip = objStringMapper.readValue(ehrdata.toString(), IPBMR.class);

			if (ip != null && ip.getCapacity() != null) {

				return createIPBMRService(ip, hiu, patientID);

			} else if (ip != null && ip.getCapacity() == null && ip.getDischargeType() != null) {

				Boolean reqRes = restTemplate.getForObject(
						LoadConfig.getConfigValue("ADMITIPCHECK") + patientID + "/IPDISCHARGE", Boolean.class);

				if (!reqRes) {

					return "Patient is not admitted to create discharge record";

				} else {
					return createIPDischargeService(ip, hiu, patientID);
				}

			}

		} catch (JsonMappingException e1) {
			// TODO Auto-generated catch block

			return "E1001";

		} catch (JsonProcessingException e1) {

			// TODO Auto-generated catch block
			return "E1001";
		}

		return null;
	}

	public String createOPBMRService(String ehrData, HiuUser hiu) {
		ObjectMapper objStringMapper = new ObjectMapper();
		OPBMR op = null;
		String patientID = null;
		try {

			JsonNode node = objStringMapper.readValue(ehrData, JsonNode.class);

			patientID = node.get("patientId").asText();
			JsonNode ehrdata = node.get("ehrData");

			op = objStringMapper.readValue(ehrdata.toString(), OPBMR.class);


		} catch (JsonMappingException e1) {
			// TODO Auto-generated catch block

			return "E1001";

		} catch (JsonProcessingException e1) {

			// TODO Auto-generated catch block
			return "E1001";
		}
		CompositionReq compreq = new CompositionReq();

		String composition = " {\"ctx/language\": \"en\",\n"
				+ "\"mhms_-_op_bmr.v2/composer|id_namespace\": \"EHR.NETWORK\",\n"
				+ "\"mhms_-_op_bmr.v2/composer|id_scheme\": \"UUID\",\n"
				+ "\"mhms_-_op_bmr.v2/context/ehrc_metadata/confidentiality_level\": \"0\",\n"

				+ "\"mhms_-_op_bmr.v2/context/health_care_facility|id_namespace\": \"EHR.NETWORK\",\n"
				+ "\"mhms_-_op_bmr.v2/context/health_care_facility|id_scheme\": \"UUID\",\n"
				+ "\"mhms_-_op_bmr.v2/context/setting|code\": \"228\",\n"
				+ "\"mhms_-_op_bmr.v2/context/setting|terminology\": \"openehr\",\n"
				+ "\"mhms_-_op_bmr.v2/context/setting|value\": \"primary medical care\",\n"
				+ "\"mhms_-_op_bmr.v2/language|code\": \"en\",\n"
				+ "\"mhms_-_op_bmr.v2/language|terminology\": \"ISO_639-1\",\n"
				+ "\"mhms_-_op_bmr.v2/territory|code\": \"IN\",\n"
				+ "\"mhms_-_op_bmr.v2/territory|terminology\": \"ISO_3166-1\",\n";

		StringBuilder str = new StringBuilder(composition);

		if (op.getTreatmentInstructions() != null) {
			str.append("\"mhms_-_op_bmr.v2/clinical_synopsis/synopsis\": \"" + op.getTreatmentInstructions() + "\",\n");
		}

		if (op.getImprovementStatus() != null) {
			str.append("\"mhms_-_op_bmr.v2/progress_note/any_event:0/progress_note\": \"" + op.getImprovementStatus()
					+ "\",\n");
		}

		if (op.getVisitType() != null && op.getVisitType().equalsIgnoreCase("Regular OPD Visit")) {
			str.append("\"mhms_-_op_bmr.v2/context/ehrc_metadata:0/program_details/program_id\": \"001\",\n"
					+ "\"mhms_-_op_bmr.v2/context/ehrc_metadata:0/program_details/program_name\": \"KMHMS_OPBMR\",\n");
		}
		if (op.getVisitType() != null && op.getVisitType().equalsIgnoreCase("Emergency")) {
			str.append("\"mhms_-_op_bmr.v2/context/ehrc_metadata:0/program_details/program_id\": \"108\",\n"
					+ "\"mhms_-_op_bmr.v2/context/ehrc_metadata:0/program_details/program_name\": \"KMHMS_EMBMR\",\n");
		}

		for (int i = 0; i < op.getDiagnosisDisplay().size(); i++) {

			str.append("\"mhms_-_op_bmr.v2/problem_diagnosis:" + i + "/clinical_description\": \""
					+ op.getDiagnosisDisplay().get(i).getDescription() + "\",\n");
			str.append("\"mhms_-_op_bmr.v2/problem_diagnosis:" + i + "/diagnostic_certainty\": \""
					+ op.getDiagnosisDisplay().get(i).getDiagnosisType() + "\",\n");
			str.append("\"mhms_-_op_bmr.v2/problem_diagnosis:" + i
					+ "/problem_diagnosis_name|terminology\": \"ICD 10\",\n");
			str.append("\"mhms_-_op_bmr.v2/problem_diagnosis:" + i + "/problem_diagnosis_name|value\":\""
					+ op.getDiagnosisDisplay().get(i).getDescription() + "\",\n");
			str.append("\"mhms_-_op_bmr.v2/problem_diagnosis:" + i + "/problem_diagnosis_name|code\":\""
					+ op.getDiagnosisDisplay().get(i).getIcdCode() + "\",\n");

		}

		for (int i = 0; i < op.getComplaintDisplay().size(); i++) {

			str.append("\"mhms_-_op_bmr.v2/story_history/any_event:0/symptom_sign:" + i + "/pattern\":\""
					+ op.getComplaintDisplay().get(i).getDuration() + "\",\n");
			str.append("\"mhms_-_op_bmr.v2/story_history/any_event:0/symptom_sign:" + i + "/symptom_sign_name\": \""
					+ op.getComplaintDisplay().get(i).getComplaint() + "\",\n");

		}

		for (int i = 0; i < op.getMedicationDisplay().size(); i++) {

			if (!op.getMedicationDisplay().get(i).getMedDuration1().matches("[0-9]+")) {
				return "E1018";
			}

			str.append("\"mhms_-_op_bmr.v2/medication_order/order:" + i
					+ "/therapeutic_direction/dosage/timing_-_daily/timing_description\":\""
					+ op.getMedicationDisplay().get(i).getMedTime() + "\",\n");
			str.append("\"mhms_-_op_bmr.v2/medication_order/order:" + i + "/timing\":\"DEFAULT_TIMING\",\n");
			str.append("\"mhms_-_op_bmr.v2/medication_order/narrative\": \"DEFAULT_NARRATIVE\",\n");
			str.append("\"mhms_-_op_bmr.v2/medication_order/order:" + i
					+ "/therapeutic_direction/direction_duration/value\":\"" + "P"
					+ op.getMedicationDisplay().get(i).getMedDuration1()
					+ op.getMedicationDisplay().get(i).getMedDuration2().substring(0, 1) + "\",\n");

			str.append("\"mhms_-_op_bmr.v2/medication_order/order:" + i + "/additional_instruction\":\""
					+ op.getMedicationDisplay().get(i).getMedRemarks() + "\",\n");

			str.append("\"mhms_-_op_bmr.v2/medication_order/order:" + i + "/medication_item\":\""
					+ op.getMedicationDisplay().get(i).getMedName() + "_"
					+ op.getMedicationDisplay().get(i).getMedDosage() + "\",\n");

		}

		if (op.getFollowUpDate() != null)

		{
			str.append("\"mhms_-_op_bmr.v2/service_request/current_activity:0/service_due/value\":\""
					+ op.getFollowUpDate() + "\",\n");
			str.append("\"mhms_-_op_bmr.v2/service_request/current_activity:0/service_name\": \"Follow-up\",\n"
					+ "\"mhms_-_op_bmr.v2/service_request/current_activity:0/timing\": \"DEFAULT_TIMING\",\n"
					+ "\"mhms_-_op_bmr.v2/service_request/narrative\": \"104\",\n");
			str.append("\"mhms_-_op_bmr.v2/service_request/organisation/organisation_name\": \"" + hiu.getMheOrgname()
					+ "\",\n");
		}
		String pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, new Locale("fr", "FR"));

		String date = simpleDateFormat.format(new Date());

		str.append("\"mhms_-_op_bmr.v2/composer|id\": \"" + hiu.getUuid() + "\",\n");
		str.append("\"mhms_-_op_bmr.v2/context/start_time\": \"" + date + "\",\n");

		str.append("\"mhms_-_op_bmr.v2/context/health_care_facility|id\": \"" + hiu.getOrguuid() + "\",\n");

		str.append("\"mhms_-_op_bmr.v2/context/health_care_facility|name\": \"" + hiu.getMheOrgname() + "\",\n");
		str.append("\"mhms_-_op_bmr.v2/context/ehrc_metadata:0/program_details/professional_registration_id\": \""
				+ op.getMhpId() + "\",\n");
		str.append("\"mhms_-_op_bmr.v2/context/ehrc_metadata:0/program_details/establishment_registration_id\": \""
				+ op.getOrgId() + "\"\n");

		str.append("}\n");

		ObjectMapper mapper = new ObjectMapper();
		Object objComp;
		try {
			objComp = mapper.readValue(str.toString(), Object.class);
		} catch (Exception e) {

			return "E1021";

		}
		compreq.setPersonId(patientID);
		compreq.setFormat("FLAT");
		compreq.setTemplateId("MHMS - op_bmr.v2");
		compreq.setComposition(objComp);
		compreq.setAuthorization(hiu.getMheSessionToken());

		String pvID = null;

		String compide;
		try {
			compide = mapper.writeValueAsString(compreq);
		} catch (JsonProcessingException e1) {
			// TODO Auto-generated catch block
			return "E1001";
		}
		HttpHeaders headers = secServ.checkSecurity();
		// headers.add("Authorization", "Bearer " + hiu.getMheAccessToken());
		// headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);

		HttpEntity<String> entity = new HttpEntity<String>(compide, headers);

		String compid = restTemplate.postForObject(LoadConfig.getConfigValue("CREATECOMPOSITION"), entity,
				String.class);
		// "http://localhost:8082/MHMS_DEV/rest/fhir/CreateCompositionForPatients",
		// entity, String.class);

		if ((compid != null && !compid.equalsIgnoreCase("Treatment cannot be posted")
				&& !compid.equalsIgnoreCase("Patient Not Found"))) {
			ChildVirtualFolder cvf = new ChildVirtualFolder();
			cvf.setPatientId(compreq.getPersonId());
			cvf.setToken(compreq.getAuthorization());

			UiData ui = new UiData();
			ui.setTemplateId(compreq.getTemplateId());
			ui.setCompositionid(compid);

			Object[] uidata = new Object[] { ui };
			cvf.setOrgUUID(hiu.getOrguuid());
			cvf.setUuid(hiu.getUuid());
			cvf.setUidata(uidata);

			LocalDate opStartdate = LocalDate.now();
			LocalDate opEnddate = LocalDate.now();

			cvf.setStartDate(opStartdate.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli());
			cvf.setEndDate(opEnddate.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli());

			String cvfStr;
			try {
				cvfStr = mapper.writeValueAsString(cvf);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				return "E1001";
			}

			String saveassesement = null;
			if (op.getVisitType() != null && op.getVisitType().equalsIgnoreCase("Emergency")) {
				HttpEntity<String> entity1 = new HttpEntity<String>(cvfStr, headers);
				saveassesement = restTemplate.postForObject(LoadConfig.getConfigValue("SAVECOMPOSITION") + "Emergency",
						entity1, String.class);

			} else {
				HttpEntity<String> entity1 = new HttpEntity<String>(cvfStr, headers);
				saveassesement = restTemplate.postForObject(LoadConfig.getConfigValue("SAVECOMPOSITION") + "OP",
						entity1, String.class);
			}

			if (saveassesement.equalsIgnoreCase("Treatment posted")) {

				return "Treatment created successfully.";
			}

			else {

				return saveassesement;
			}
		} else {
			return compid;
		}

	}

	public String createIPDischargeService(IPBMR ipdis, HiuUser hiu, String patientID) {

		CompositionReq compreq = new CompositionReq();
		String bmrRequest = "{\n" + "\"ctx/language\": \"en\",\n" + "\"mhms_-_discharge.v1/territory|code\": \"IN\",\n"
				+ "\"mhms_-_discharge.v1/territory|terminology\": \"ISO_3166-1\",\n"
				+ "\"mhms_-_discharge.v1/language|code\": \"en\",\n"
				+ "\"mhms_-_discharge.v1/language|terminology\": \"ISO_639-1\",\n" +

				"\"mhms_-_discharge.v1/composer|id_scheme\": \"UUID\",\n"
				+ "\"mhms_-_discharge.v1/composer|id_namespace\": \"EHR.NETWORK\",\n" +

				"\"mhms_-_discharge.v1/context/health_care_facility|id\": \"123456-123\",\n"
				+ "\"mhms_-_discharge.v1/context/health_care_facility|id_scheme\": \"UUID\",\n"
				+ "\"mhms_-_discharge.v1/context/health_care_facility|id_namespace\": \"EHR.NETWORK\",\n"
				+ "\"mhms_-_discharge.v1/context/setting|code\": \"228\",\n"
				+ "\"mhms_-_discharge.v1/context/setting|value\": \"primary medical care\",\n"
				+ "\"mhms_-_discharge.v1/context/setting|terminology\": \"openehr\",\n"
				+ "\"mhms_-_discharge.v1/context/ehrc_metadata:0/confidentiality_level\": \"0\",\n"
				+ "\"mhms_-_discharge.v1/context/ehrc_metadata:0/program_details/program_name\": \"KMHMS_Discharge\",\n"
				+ "\"mhms_-_discharge.v1/context/ehrc_metadata:0/program_details/program_id\": \"0006\",\n";

		StringBuilder str = new StringBuilder(bmrRequest);

		String pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, new Locale("fr", "FR"));

		if (ipdis.getDischargeCondition() != null && ipdis.getDischargeDate() != null) {
			str.append("\"mhms_-_discharge.v1/service_request/current_activity:0/service_name\":\""
					+ ipdis.getDischargeCondition() + "\",\n"
					+ "\"mhms_-_discharge.v1/service_request/current_activity:0/service_due\":\""
					+ ipdis.getDischargeDate() + "\",\n"
					+ "\"mhms_-_discharge.v1/service_request/current_activity:0/timing\":\"DEFAULT_TIMING\",\n"
					+ "\"mhms_-_discharge.v1/service_request/narrative\":\"DEFAULT_NARRATIVE\",\n");
		}

		str.append("\"mhms_-_discharge.v1/discharge/discharging_doctor/name\":\"" + ipdis.getMhpId() + "\",\n"

		);

		str.append("\"mhms_-_discharge.v1/discharge/discharging_doctor/designation\":\"" + ipdis.getMhpDesignation()
				+ "\",\n");
		str.append("\"mhms_-_discharge.v1/discharge/type_of_discharge\": \"" + ipdis.getDischargeType() + "\",\n");
		if (ipdis.getDischargeCondition() != null) {
			str.append("\"mhms_-_discharge.v1/discharge/condition_at_discharge\": \"" + ipdis.getDischargeCondition()
					+ "\",\n");
		}

		if (ipdis.getConditionDescription() != null) {
			str.append("\"mhms_-_discharge.v1/discharge/additional_details\": \"" + ipdis.getConditionDescription()
					+ "\",\n");
		}

		DateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
		Date date = null;
		try {
			date = formatter.parse(ipdis.getDischargeDate().toString());
		} catch (ParseException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
		format.setTimeZone(TimeZone.getTimeZone("UTC"));

		String date1 = format.format(date);

		str.append("\"mhms_-_discharge.v1/discharge/discharge_date\": \"" + date1 + "\",\n");

		if (ipdis.getMedicationAfterDischarge() != null && ipdis.getMedicationAfterDischarge().size() > 0) {
			for (int i = 0; i < ipdis.getMedicationAfterDischarge().size(); i++) {

				if (!ipdis.getMedicationAfterDischarge().get(i).getMedDuration1().matches("[0-9]+")) {
					return "E1018";
				}

				str.append("\"mhms_-_discharge.v1/medication_order/order:" + i
						+ "/therapeutic_direction/dosage/timing_-_daily/timing_description\":\""
						+ ipdis.getMedicationAfterDischarge().get(i).getMedTime() + "\",\n");
				str.append("\"mmhms_-_discharge.v1/medication_order/order:" + i + "/timing\":\"DEFAULT_TIMING\",\n");
				str.append("\"mhms_-_discharge.v1/medication_order/order:" + i
						+ "/timing|formalism\":\"DEFAULT_FORMALISM\",\n");
				str.append("\"mhms_-_discharge.v1/medication_order/narrative\": \"DEFAULT_NARRATIVE\",\n");
				str.append("\"mhms_-_discharge.v1/medication_order/order:" + i
						+ "/therapeutic_direction/direction_duration/value\":\"" + "P"
						+ ipdis.getMedicationAfterDischarge().get(i).getMedDuration1()
						+ ipdis.getMedicationAfterDischarge().get(i).getMedDuration2().substring(0, 1) + "\",\n");

				str.append("\"mhms_-_discharge.v1/medication_order/order:" + i + "/additional_instruction\":\""
						+ ipdis.getMedicationAfterDischarge().get(i).getMedRemarks() + "\",\n");

				str.append("\"mhms_-_discharge.v1/medication_order/order:" + i + "/medication_item\":\""
						+ ipdis.getMedicationAfterDischarge().get(i).getMedName() + "_"
						+ ipdis.getMedicationAfterDischarge().get(i).getMedDosage() + "\",\n");

			}

		}

		String date3 = simpleDateFormat.format(new Date());

		str.append("\"mhms_-_discharge.v1/composer|id\":\"" + hiu.getUuid() + "\",\n");
		str.append("\"mhms_-_discharge.v1/context/start_time\":\"" + date3 + "\",\n");
		str.append("\"mhms_-_discharge.v1/context/health_care_facility|name\":\"" + ipdis.getOrgName().toString()
				+ "\",\n");
		str.append("\"mhms_-_discharge.v1/context/ehrc_metadata:0/program_details/professional_registration_id\":\""
				+ ipdis.getMhpId().toString() + "\",\n");
		str.append("\"mhms_-_discharge.v1/context/ehrc_metadata:0/program_details/establishment_registration_id\":\""
				+ ipdis.getOrgId().toString() + "\",\n");
		str.append("\"mhms_-_discharge.v1/context/health_care_facility|name\":\"" + ipdis.getOrgName().toString()
				+ "\"\n");

		str.append("}\n");

		ObjectMapper mapper = new ObjectMapper();
		Object objComp;
		try {
			objComp = mapper.readValue(str.toString(), Object.class);
		} catch (Exception e) {

			return "E1021";

		}
		compreq.setPersonId(patientID);
		compreq.setFormat("FLAT");
		compreq.setTemplateId("MHMS - discharge.v1");
		compreq.setComposition(objComp);
		compreq.setAuthorization(hiu.getMheSessionToken());

		String pvID = null;

		String compide = null;
		try {
			compide = mapper.writeValueAsString(compreq);
		} catch (JsonProcessingException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		HttpHeaders headers = secServ.checkSecurity();

		HttpEntity<String> entity = new HttpEntity<String>(compide, headers);
		String compid = restTemplate.postForObject(LoadConfig.getConfigValue("CREATECOMPOSITION"), entity,
				String.class);

		if ((compid != null && !compid.equalsIgnoreCase("Treatment cannot be posted")
				&& !compid.equalsIgnoreCase("Patient Not Found"))) {
			ChildVirtualFolder cvf = new ChildVirtualFolder();
			cvf.setPatientId(compreq.getPersonId());
			cvf.setToken(compreq.getAuthorization());

			UiData ui = new UiData();
			ui.setTemplateId(compreq.getTemplateId());
			ui.setCompositionid(compid);

			Object[] uidata = new Object[] { ui };
			cvf.setOrgUUID(hiu.getOrguuid());
			cvf.setUuid(hiu.getUuid());
			cvf.setUidata(uidata);

			DateFormat formatter1 = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
			Date dateF = null;
			try {
				dateF = formatter1.parse(ipdis.getDischargeDate().toString());
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
			format1.setTimeZone(TimeZone.getTimeZone("UTC"));

			String date5 = format.format(dateF);

			DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
			if (ipdis.getDischargeDate().toString() != null) {
				LocalDate gdisdate = LocalDate.parse(date5, f);
				cvf.setEndDate(gdisdate.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli());
			}

			String cvfStr = null;
			try {
				cvfStr = mapper.writeValueAsString(cvf);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			HttpEntity<String> entity1 = new HttpEntity<String>(cvfStr, headers);

			pvID = restTemplate.postForObject(
					LoadConfig.getConfigValue("SAVEIPDISCHARGEPATIENT") + date1 + "/" + ipdis.getMhpId(), entity1,
					String.class);

			if (pvID != null) {
				return "Treatment created successfully.  \n";

			} else {
				return "E1017";

			}
		} else {
			pvID = compid;
		}

		return pvID;

	}

}
