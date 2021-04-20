package com.lms.packages.payload.response;

import java.util.Date;

public class IssueRequestResponse {
	
	private long issueId;
	private String userName;
	private String email;
	private String bookId;
	private String title;
	private Date issueDate;
	private Date returnDate;
	
	public IssueRequestResponse(long issueId ,String userName ,String email ,String bookId,String title, Date issueDate,Date returnDate) {
		this.issueId = issueId;
		this.userName = userName;
		this.email = email;
		this.bookId = bookId;
		this.title = title;
		this.issueDate = issueDate;
		this.returnDate = returnDate;
	}
	
	public long getIssueId() {
		return issueId;		
	}
	
	public void setIssueId(long issueId) {
		this.issueId = issueId;		
	}
	
	public String getUsername() {
		return userName;		
	}
	
	public void setUserName(String userName) {
		this.userName = userName;		
	}
	
	public String getEmail() {
		return email;		
	}
	
	public void setEmail(String email) {
		this.email = email;		
	}
	
	public String getBookId() {
		return bookId;		
	}
	
	public void setBookId(String bookId) {
		this.bookId = bookId;		
	}
	
	public String getTitle() {
		return title;		
	}
	
	public void setTitle(String title) {
		this.bookId = title;		
	}
	
	public Date getIssueDate() {
		return issueDate;		
	}
	
	public void setIssueDate(Date date) {
		this.issueDate = date;
	}
	
	public Date getReturnDate() {
		return returnDate;		
	}
	
	public void setReturnDate(Date date) {
		this.returnDate = date;
	}
	
	
	
}
