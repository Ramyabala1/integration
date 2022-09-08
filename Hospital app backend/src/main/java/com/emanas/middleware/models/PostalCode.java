package com.emanas.middleware.models;

public class PostalCode {
	
	
	String id;
	String pincode;
	String district;
	String stateName;
	String cityName;
	
	String office_name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getOffice_name() {
		return office_name;
	}

	public void setOffice_name(String office_name) {
		this.office_name = office_name;
	}

	@Override
	public String toString() {
		return "PostalCode [id=" + id + ", pincode=" + pincode + ", district=" + district + ", stateName=" + stateName
				+ ", cityName=" + cityName + ", office_name=" + office_name + "]";
	}
	
	

}
