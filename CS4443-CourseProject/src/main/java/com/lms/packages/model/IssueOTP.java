package com.lms.packages.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="issue_otp")
public class IssueOTP {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="otp_id")
	private long tokenid;
	
	@Column(name="otp")
	private String issuetoken;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;	
	
	public IssueOTP() {		
		createdDate = new Date();		
	}

	public long getTokenid() {
		return tokenid;
	}

	public void setTokenid(long tokenid) {
		this.tokenid = tokenid;
	}

	public String getIssuetoken() {
		return issuetoken;
	}

	public void setIssuetoken(String issuetoken) {
		this.issuetoken = issuetoken;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}


}
