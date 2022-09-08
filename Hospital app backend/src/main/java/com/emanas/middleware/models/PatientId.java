package com.emanas.middleware.models;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
@XmlRootElement
public class PatientId {
	
	IDProof idproof;

	public IDProof getIdproof() {
		return idproof;
	}

	public void setIdproof(IDProof idproof) {
		this.idproof = idproof;
	}

	@Override
	public String toString() {
		return "PatientId [idproof=" + idproof + "]";
	}
	
	

}
