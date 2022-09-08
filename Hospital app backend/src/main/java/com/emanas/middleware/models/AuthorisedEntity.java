package com.emanas.middleware.models;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
@XmlRootElement
@Entity
@Table(name = "AuthorisedEntity")
public class AuthorisedEntity implements Serializable{
	
	@Column(name = "userToken")
	private String userToken; 
	
	@Column(name = "userUuid")
	private String userUuid; 
	
	@Column(name = "orgUuid")
	private String orgUuid; 
	 
	@Column(name = "idType")
	private String idType;
	
	@Column(name = "idNumber")
	private String idNumber;

	
	@Column(name = "personId")
	private String personId;
	
	@Column(name = "prefix")
	private String prefix;
	
	@Column(name = "givenName")
	private String givenName;
	
	@Column(name = "middleName")
	private String middleName;
	
	@Column(name = "genderCode")
	private String genderCode;
	
	
	@Column(name = "genderName")
	private String genderName;
	
	
	@Column(name = "emergencyContact")
	private String emergencyContact;
	
	@Column(name = "phoneNumber")
	private String phoneNumber;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "dateOfBirth")
	private String dateOfBirth;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "district")
	private String district;
	
	@Column(name = "city")
	private String city;
	
	@Column(name = "state")
	private String state;
	
	
	@Column(name = "pinCode")
	private String pinCode;
	
	@Column(name = "postalCode")
	private String postalCode;
	
	@Column(name = "address1")
	private String address1;
	
	@Column(name = "address2")
	private String address2;
	
	@Column(name = "transactionId")
	private String transactionId;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id" , unique = true, nullable = false)
	private int ID;

	public String getUserToken() {
		return userToken;
	}

	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}

	public String getUserUuid() {
		return userUuid;
	}

	public void setUserUuid(String userUuid) {
		this.userUuid = userUuid;
	}

	public String getOrgUuid() {
		return orgUuid;
	}

	public void setOrgUuid(String orgUuid) {
		this.orgUuid = orgUuid;
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getGivenName() {
		return givenName;
	}

	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

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

	public String getEmergencyContact() {
		return emergencyContact;
	}

	public void setEmergencyContact(String emergencyContact) {
		this.emergencyContact = emergencyContact;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPinCode() {
		return pinCode;
	}

	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
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

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	@Override
	public String toString() {
		return "AuthorisedEntity [userToken=" + userToken + ", userUuid=" + userUuid + ", orgUuid=" + orgUuid
				+ ", idType=" + idType + ", idNumber=" + idNumber + ", personId=" + personId + ", prefix=" + prefix
				+ ", givenName=" + givenName + ", middleName=" + middleName + ", genderCode=" + genderCode
				+ ", genderName=" + genderName + ", emergencyContact=" + emergencyContact + ", phoneNumber="
				+ phoneNumber + ", email=" + email + ", dateOfBirth=" + dateOfBirth + ", status=" + status
				+ ", district=" + district + ", city=" + city + ", state=" + state + ", pinCode=" + pinCode
				+ ", postalCode=" + postalCode + ", address1=" + address1 + ", address2=" + address2
				+ ", transactionId=" + transactionId + ", ID=" + ID + "]";
	}

     
	
}

