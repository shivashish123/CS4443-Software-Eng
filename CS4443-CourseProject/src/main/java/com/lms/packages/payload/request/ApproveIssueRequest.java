package com.lms.packages.payload.request;

import javax.validation.constraints.NotBlank;

public class ApproveIssueRequest {
	@NotBlank
    private long issueId;
	
	@NotBlank
    private String otp;

	@NotBlank
    private String email;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public long getIssueId() {
		return issueId;
	}
	public void setId(long id) {
		this.issueId = id;
	}
	
	public String getOTP() {
		return otp;
	}
	public void setOTP(String otp) {
		this.otp = otp;
	}
}
