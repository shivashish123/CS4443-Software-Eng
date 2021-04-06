package com.lms.packages.payload.request;

import javax.validation.constraints.NotBlank;

public class SearchRequest {
	
	private String keyword;

	@NotBlank
	private String searchBy;
	
	@NotBlank
	private String sortBy;
	
	public String getKeyword(){
		return this.keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	public String getSearchBy(){
		return this.searchBy;
	}
	public void setSearchBy(String searchBy) {
		this.searchBy = searchBy;
	}
	
	public String getSortBy(){
		return this.sortBy;
	}
	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

}
