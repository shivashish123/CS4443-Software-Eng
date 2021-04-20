package com.lms.packages.payload.request;

import javax.validation.constraints.NotBlank;

public class BookIssueRequest {
	@NotBlank
    private String bookId;
	
	public String getbookId() {
		return bookId;
	}
	public void setbookId(String id) {
		this.bookId = id;
	}
}
