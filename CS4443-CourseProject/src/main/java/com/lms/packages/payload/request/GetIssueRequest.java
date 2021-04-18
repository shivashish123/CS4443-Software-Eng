package com.lms.packages.payload.request;

import javax.validation.constraints.NotBlank;

public class GetIssueRequest {
	@NotBlank
    private String email;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
