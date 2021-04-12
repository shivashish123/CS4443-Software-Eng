package com.lms.packages.payload.request;

import javax.validation.constraints.NotBlank;

public class RemoveBookRequest {
	@NotBlank
    private String id;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
