package com.emanas.middleware.models;

import java.util.Arrays;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlRootElement;

import com.emanas.middleware.utility.PatternUtil;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
@XmlRootElement
public class Patient {
	
	@Pattern(message = PatternUtil.patternMessage,regexp = PatternUtil.uuids)
	String patientId;
	@Pattern(message = PatternUtil.patternMessage,regexp = PatternUtil.name)
	String prefix ="";
	@Pattern(message = PatternUtil.patternMessage,regexp = PatternUtil.name)
	String givenName="";
	@Pattern(message = PatternUtil.patternMessage,regexp = PatternUtil.name)
	String middleName="";
	String dateOfBirth="";
	@Pattern(message = PatternUtil.patternMessage,regexp = PatternUtil.phoneNumberOnly)
	String phoneNumber="";
	@Pattern(message = PatternUtil.patternMessage,regexp = PatternUtil.phoneNumberOnly)
	String phoneExt="";
	@Email
	String email="";
	@Pattern(message = PatternUtil.patternMessage,regexp = PatternUtil.alphabetOnly)
	String nationality="";
	@Pattern(message = PatternUtil.patternMessage,regexp = PatternUtil.alphabetOnly)
	String religiousOrientation="";
	@Pattern(message = PatternUtil.patternMessage,regexp = PatternUtil.alphabetOnly)
	String caste="";
	@Pattern(message = PatternUtil.patternMessage,regexp = PatternUtil.alphabetOnly)
	String dobStatus="";
	@Pattern(message = PatternUtil.patternMessage,regexp = PatternUtil.fileNameOnly)
	String profileImage="null";
	@Pattern(message = PatternUtil.patternMessage,regexp = PatternUtil.alphabetOnly)
	String maritalStatus="";
	@Pattern(message = PatternUtil.patternMessage,regexp = PatternUtil.alphabetOnly)
	String education="";
	@Pattern(message = PatternUtil.patternMessage,regexp = PatternUtil.alphabetOnly)
	String occupation="";
	@Pattern(message = PatternUtil.patternMessage,regexp = PatternUtil.alphabetOnly)
	String bloodGroup="";
	String dateCreated="";
	@Pattern(message = PatternUtil.patternMessage,regexp = PatternUtil.alphabetOnly)
	String status="";
	@Pattern(message = PatternUtil.patternMessage,regexp = PatternUtil.uuids)
	String personId;
	@Pattern(message = PatternUtil.patternMessage,regexp = PatternUtil.uuids)
	String orgUuid;
	@Pattern(message = PatternUtil.patternMessage,regexp = PatternUtil.uuids)
	String userUuid;
	@Pattern(message = PatternUtil.patternMessage,regexp = PatternUtil.comments)
	String idType;
	@Pattern(message = PatternUtil.patternMessage,regexp = PatternUtil.comments)
	String idNumber;
	@Pattern(message = PatternUtil.patternMessage,regexp = PatternUtil.comments)
	String hospitalId;
	@Pattern(message = PatternUtil.patternMessage,regexp = PatternUtil.session)
	String userToken;
	@Pattern(message = PatternUtil.patternMessage,regexp = PatternUtil.uuids)
	String mobToken;
	@Valid
	Gender gender = new Gender();
	@Valid
	Address[] address= {new Address()};
	
	@Valid
	IDProof[] idproof = {new IDProof()};
	
 	@Valid
	EmergencyContact emergencyContact = new EmergencyContact();
		
	
 	
 	
	public IDProof[] getIdproof() {
		return idproof;
	}
	public void setIdproof(IDProof[] idproof) {
		this.idproof = idproof;
	}
	public String getHospitalId() {
		return hospitalId;
	}
	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
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
	public String getOrgUuid() {
		return orgUuid;
	}
	public void setOrgUuid(String orgUuid) {
		this.orgUuid = orgUuid;
	}
	public String getUserUuid() {
		return userUuid;
	}
	public void setUserUuid(String userUuid) {
		this.userUuid = userUuid;
	}
	public String getUserToken() {
		return userToken;
	}
	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}
	public String getPatientId() {
		return patientId;
	}
	public void setPatientId(String patientId) {
		this.patientId = patientId;
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
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getPhoneExt() {
		return phoneExt;
	}
	public void setPhoneExt(String phoneExt) {
		this.phoneExt = phoneExt;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public String getReligiousOrientation() {
		return religiousOrientation;
	}
	public void setReligiousOrientation(String religiousOrientation) {
		this.religiousOrientation = religiousOrientation;
	}
	public String getCaste() {
		return caste;
	}
	public void setCaste(String caste) {
		this.caste = caste;
	}
	public String getDobStatus() {
		return dobStatus;
	}
	public void setDobStatus(String dobStatus) {
		this.dobStatus = dobStatus;
	}
	public String getProfileImage() {
		return profileImage;
	}
	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}
	public String getMaritalStatus() {
		return maritalStatus;
	}
	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}
	public String getOccupation() {
		return occupation;
	}
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}
	public String getBloodGroup() {
		return bloodGroup;
	}
	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}
	public String getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPersonId() {
		return personId;
	}
	public void setPersonId(String personId) {
		this.personId = personId;
	}
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	public Address[] getAddress() {
		return address;
	}
	public void setAddress(Address[] address) {
		this.address = address;
	}
	public EmergencyContact getEmergencyContact() {
		return emergencyContact;
	}
	public void setEmergencyContact(EmergencyContact emergencyContact) {
		this.emergencyContact = emergencyContact;
	}
	public String getMobToken() {
		return mobToken;
	}
	public void setMobToken(String mobToken) {
		this.mobToken = mobToken;
	}
	@Override
	public String toString() {
		return "PatientCreateReq [patientId=" + patientId + ", prefix=" + prefix + ", givenName=" + givenName
				+ ", middleName=" + middleName + ", dateOfBirth=" + dateOfBirth + ", phoneNumber=" + phoneNumber
				+ ", phoneExt=" + phoneExt + ", email=" + email + ", nationality=" + nationality
				+ ", religiousOrientation=" + religiousOrientation + ", caste=" + caste + ", dobStatus=" + dobStatus
				+ ", profileImage=" + profileImage + ", maritalStatus=" + maritalStatus + ", education=" + education
				+ ", occupation=" + occupation + ", bloodGroup=" + bloodGroup + ", dateCreated=" + dateCreated
				+ ", status=" + status + ", personId=" + personId + ", orgUuid=" + orgUuid + ", userUuid=" + userUuid
				+ ", idType=" + idType + ", idNumber=" + idNumber + ", hospitalId=" + hospitalId + ", userToken="
				+ userToken + ", mobToken=" + mobToken + ", gender=" + gender + ", address=" + Arrays.toString(address)
				+ ", emergencyContact=" + emergencyContact + "]";
	}
	


}
