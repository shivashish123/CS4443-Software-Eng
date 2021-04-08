package com.lms.packages.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class AddBookRequest {
	@NotBlank
    private String title;
	
	private String[] authors;
	
	private String publisher;
	
	private int copies;
	
	private String genre;
	
	private String subGenre;
	
	public String getTitle(){
		return title;
	}
	public void setTitle(String title){
		this.title = title;
	}
	
	public String getPublisher(){
		return publisher;
	}
	public void setPublisher(String publisher){
		this.publisher = publisher;
	}
	
	public String[] getAuthors(){
		return authors;
	}
	public void setAuthors(String[] authors){
		this.authors = authors;
	}
	
	public int getCopies(){
		return copies;
	}
	public void setCopies(int copies){
		this.copies = copies;
	}
	
	public String getGenre(){
		return genre;
	}
	public void setGenre(String genre){
		this.genre = genre;
	}
	
	public String getSubGenre(){
		return subGenre;
	}
	public void setSubGenre(String subgenre){
		this.subGenre = subgenre;
	}
	
}
