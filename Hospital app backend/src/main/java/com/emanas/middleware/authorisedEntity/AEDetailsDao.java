package com.emanas.middleware.authorisedEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface AEDetailsDao {
	public void saveAEDetails(AEDetails aeDetails);

	public AEDetails getAEDetails(String transID) throws JsonMappingException, JsonProcessingException;

	public void deleteConsent(String transID);

}
