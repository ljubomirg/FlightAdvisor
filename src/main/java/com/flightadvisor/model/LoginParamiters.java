package com.flightadvisor.model;

import org.springframework.stereotype.Component;

@Component
public class LoginParamiters {

	private String userName;
	private String password;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "LoginParamiters [userName=" + userName + ", password=" + password + "]";
	}

}
