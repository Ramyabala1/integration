package com.emanas.middleware.authorisedEntity;

import java.io.Serializable;

public class AEDetails implements Serializable {
	String consentID;
	String personID;
	String transID;
	String eManasID;

	public String geteManasID() {
		return eManasID;
	}

	public void seteManasID(String eManasID) {
		this.eManasID = eManasID;
	}

	public String getConsentID() {
		return consentID;
	}

	public void setConsentID(String consentID) {
		this.consentID = consentID;
	}

	public String getPersonID() {
		return personID;
	}

	public void setPersonID(String personID) {
		this.personID = personID;
	}

	public String getTransID() {
		return transID;
	}

	public void setTransID(String transID) {
		this.transID = transID;
	}

	@Override
	public String toString() {
		return "AEDetails [consentID=" + consentID + ", personID=" + personID + ", transID=" + transID + ", eManasID="
				+ eManasID + "]";
	}

}
