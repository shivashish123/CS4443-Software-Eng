package com.lms.packages.payload.request;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

public class AddBookRequest implements Serializable {
	@NotBlank
    private String title;
	
	private String[] authors;
	
	private String publisher;
	
	private int copies;
	
	private String genre;
	
	private String subGenre;
	
	private MultipartFile cover;
	
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
	
	public MultipartFile getCover(){
		return cover;
	}
	public void setCover(MultipartFile cover){
		this.cover = cover;
	}
	
}
