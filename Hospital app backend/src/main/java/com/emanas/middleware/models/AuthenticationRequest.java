package com.emanas.middleware.models;

import java.io.Serializable;

public class AuthenticationRequest implements Serializable {


    private String hiu_username;
    private String hiu_password;
    private String hiu_ipaddress;
    
    

    public String getHiu_ipaddress() {
		return hiu_ipaddress;
	}

	public void setHiu_ipaddress(String hiu_ipaddress) {
		this.hiu_ipaddress = hiu_ipaddress;
	}

	public String getHiu_username() {
		return hiu_username;
	}

	public void setHiu_username(String hiu_username) {
		this.hiu_username = hiu_username;
	}

	public String getHiu_password() {
		return hiu_password;
	}

	public void setHiu_password(String hiu_password) {
		this.hiu_password = hiu_password;
	}

	//need default constructor for JSON Parsing
    public AuthenticationRequest()
    {

    }

    public AuthenticationRequest(String username, String password) {
        this.setHiu_username(username);
        this.setHiu_password(password);
    }

	@Override
	public String toString() {
		return "AuthenticationRequest [hiu_username=" + hiu_username + ", hiu_password=" + hiu_password
				+ ", hiu_ipaddress=" + hiu_ipaddress + ", getHiu_ipaddress()=" + getHiu_ipaddress()
				+ ", getHiu_username()=" + getHiu_username() + ", getHiu_password()=" + getHiu_password()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}
    
    
}
