package com.emanas.middleware.models;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
@XmlRootElement

public class UiData{
	
	
	String templateId ;
	String compositionid ;
	
	
	
	
	
	public String getTemplateId() {
		return templateId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	public String getCompositionid() {
		return compositionid;
	}
	public void setCompositionid(String compositionid) {
		this.compositionid = compositionid;
	}
	@Override
	public String toString() {
		return "UiData [templateId=" + templateId + ", compositionid=" + compositionid + "]";
	}
	
	
	
	
	
}




