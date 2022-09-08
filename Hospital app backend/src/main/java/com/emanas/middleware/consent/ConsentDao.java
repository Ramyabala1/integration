package com.emanas.middleware.consent;

import java.util.HashMap;

import com.emanas.middleware.authorisedEntity.AEDetails;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface ConsentDao {

	void saveConsent(Consent consent);

	Consent getConsent(String consentID) throws JsonMappingException, JsonProcessingException;

	void deleteConsent(String consentID);

	boolean createConsentrecord(Consent objData);

	public String verifyandCreateConsentObject(Consent objData);

	public String getConsentForPatient(Consent objData, String status);

	public String getInActiveConsentForPatient(Consent objData);

	public Boolean revokeConsent(Consent consentObj);

	public Boolean updateConsent(Consent consentObj);

	public HashMap<String, String> validateConsent(String ehrData, String token);

	public String validateConsentForAEMap(AEDetails aeDetails, String token);

}
