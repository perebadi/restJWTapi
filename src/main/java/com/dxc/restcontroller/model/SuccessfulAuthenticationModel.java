package com.dxc.restcontroller.model;

import java.io.Serializable;

public class SuccessfulAuthenticationModel implements Serializable {

	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	public SuccessfulAuthenticationModel() {}
	
}
