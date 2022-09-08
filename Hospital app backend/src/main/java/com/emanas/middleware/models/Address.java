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
public class Address {
	int id = 0;
	String type = "";
	@Pattern(message = PatternUtil.patternMessage,regexp = PatternUtil.address)
	String address1 = "";
	@Pattern(message = PatternUtil.patternMessage,regexp = PatternUtil.address)
	String address2 = "";
	@Pattern(message = PatternUtil.patternMessage,regexp = PatternUtil.address)
	String building = "";
	@Pattern(message = PatternUtil.patternMessage,regexp = PatternUtil.alphabetOnly)
	String taluk = "";
	@Pattern(message = PatternUtil.patternMessage,regexp = PatternUtil.address)
	String district = "";
	@Pattern(message = PatternUtil.patternMessage,regexp = PatternUtil.address)
	String landMark = "";
//	String pinCode = "";
	@Pattern(message = PatternUtil.patternMessage,regexp = PatternUtil.alphabetOnly)
	String city = "";
	@Pattern(message = PatternUtil.patternMessage,regexp = PatternUtil.alphabetOnly)
	String state = "";
	@Pattern(message = PatternUtil.patternMessage,regexp = PatternUtil.alphabetOnly)
	String country = "";
	@Pattern(message = PatternUtil.patternMessage,regexp = PatternUtil.alphabetOnly)
	String postOffice = "";
	@Pattern(message = PatternUtil.patternMessage,regexp = PatternUtil.pincodeOnly)
	String postalCode = "";
	
	/**
	 * @return the postalCode
	 */
	public String getPostalCode() {
		return postalCode;
	}

	/**
	 * @param postalCode the postalCode to set
	 */
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

//	public String getPinCode() {
//		return pinCode;
//	}
//
//	public void setPinCode(String pinCode) {
//		this.pinCode = pinCode;
//	}

	
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	

	/**
	 * @return the building
	 */
	public String getBuilding() {
		return building;
	}

	/**
	 * @param building the building to set
	 */
	public void setBuilding(String building) {
		this.building = building;
	}

	public String getTaluk() {
		return taluk;
	}

	public void setTaluk(String taluk) {
		this.taluk = taluk;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getLandMark() {
		return landMark;
	}

	public void setLandMark(String landMark) {
		this.landMark = landMark;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	

	/**
	 * @return the postOffice
	 */
	public String getPostOffice() {
		return postOffice;
	}

	/**
	 * @param postOffice the postOffice to set
	 */
	public void setPostOffice(String postOffice) {
		this.postOffice = postOffice;
	}

	@Override
	public String toString() {
		return "Address [id=" + id + ", type=" + type + ", address1=" + address1 + ", address2=" + address2
				+ ", building=" + building + ", taluk=" + taluk + ", district=" + district + ", landMark=" + landMark
				+ ", city=" + city + ", state=" + state + ", country=" + country + ", postOffice=" + postOffice
				+ ", postalCode=" + postalCode + "]";
	}

	
	
	

}


/*"address": [
            {
                "id": 9,
                "type": "permanent",
                "address1": "first main",
                "address2": "first cross",
                "taluk": "",
                "district": "",
                "landMark": "",
                "postalCode": null
            }
        ],*/
