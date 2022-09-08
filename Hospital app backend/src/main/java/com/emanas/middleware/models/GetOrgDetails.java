package com.emanas.middleware.models;

public class GetOrgDetails {
	
	String category;
		String	displayname;
		String	district;
		String	email;
		String	ownername;
		String	phoneNumber;
		String	sysOfPractice;
		String		uuid;
		String mheRegId; 
		
		
		public String getMheRegId() {
			return mheRegId;
		}
		public void setMheRegId(String mheRegId) {
			this.mheRegId = mheRegId;
		}
		public String getCategory() {
			return category;
		}
		public void setCategory(String category) {
			this.category = category;
		}
		public String getDisplayname() {
			return displayname;
		}
		public void setDisplayname(String displayname) {
			this.displayname = displayname;
		}
		public String getDistrict() {
			return district;
		}
		public void setDistrict(String district) {
			this.district = district;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getOwnername() {
			return ownername;
		}
		public void setOwnername(String ownername) {
			this.ownername = ownername;
		}
		public String getPhoneNumber() {
			return phoneNumber;
		}
		public void setPhoneNumber(String phoneNumber) {
			this.phoneNumber = phoneNumber;
		}
		public String getSysOfPractice() {
			return sysOfPractice;
		}
		public void setSysOfPractice(String sysOfPractice) {
			this.sysOfPractice = sysOfPractice;
		}
		public String getUuid() {
			return uuid;
		}
		public void setUuid(String uuid) {
			this.uuid = uuid;
		}
		@Override
		public String toString() {
			return "GetOrgDetails [category=" + category + ", displayname=" + displayname + ", district=" + district
					+ ", email=" + email + ", ownername=" + ownername + ", phoneNumber=" + phoneNumber
					+ ", sysOfPractice=" + sysOfPractice + ", uuid=" + uuid + ", mheRegId=" + mheRegId + "]";
		}
		
		

}
