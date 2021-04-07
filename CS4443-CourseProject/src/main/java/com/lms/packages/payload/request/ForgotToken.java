package com.lms.packages.payload.request;

public class ForgotToken {
	public String otp;

	public String getToken() {
		return otp;
	}

	public void setToken(String token) {
		this.otp= token;
	}
}
