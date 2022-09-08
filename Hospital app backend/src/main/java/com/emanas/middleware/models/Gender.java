package com.emanas.middleware.models;

import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlRootElement;

import com.emanas.middleware.utility.PatternUtil;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonIgnoreProperties(ignoreUnknown = true)	
@JsonInclude(value=Include.NON_EMPTY, content=Include.NON_NULL)
@XmlRootElement
public class Gender {
	@Pattern(message = PatternUtil.patternMessage,regexp = PatternUtil.address)
	String genderCode;
	@Pattern(message = PatternUtil.patternMessage,regexp = PatternUtil.alphabetOnly)
	String genderName;
//	int id;
//	String genderCd;
	
	
	
//	public String getGenderCd() {
//		return genderCd;
//	}
//
//	public void setGenderCd(String genderCd) {
//		this.genderCd = genderCd;
//	}

	public String getGenderCode() {
		return genderCode;
	}

	public void setGenderCode(String genderCode) {
		this.genderCode = genderCode;
	}

	public String getGenderName() {
		return genderName;
	}

	public void setGenderName(String genderName) {
		this.genderName = genderName;
	}

//	public int getId() {
//		return id;
//	}
//
//	public void setId(int id) {
//		this.id = id;
//	}

	public Gender() {
		
	}

	@Override
	public String toString() {
		return "Gender [genderCode=" + genderCode + "]";
	}
	

}

/*"gender": {
    "genderCode": "F",
    "genderName": null,
    "id": 485
},
*/