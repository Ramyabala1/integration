package com.emanas.middleware.models;



import java.io.Serializable;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import com.emanas.middleware.utility.PatternUtil;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)


@XmlRootElement(name = "CompositionReq")
@XmlSeeAlso({CompositionReq.class})

public class CompositionReq implements Serializable{
	
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//	@JsonIgnore
	Object composition;
	@Pattern(message = PatternUtil.patternMessage,regexp = PatternUtil.uuids)
	String personId;
	@Pattern(message = PatternUtil.patternMessage,regexp = PatternUtil.uuids)
	String templateId;
	@Pattern(message = PatternUtil.patternMessage,regexp = PatternUtil.comments)
	String format;
	@Pattern(message = PatternUtil.patternMessage,regexp = PatternUtil.session)
	String authorization;
	Object aql;

	public Object getComposition() {
		return composition;
	}

	public void setComposition(Object composition) {
		this.composition = composition;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getAuthorization() {
		return authorization;
	}

	public void setAuthorization(String authorization) {
		this.authorization = authorization;
	}

	public Object getAql() {
		return aql;
	}

	public void setAql(Object aql) {
		this.aql = aql;
	}


}
