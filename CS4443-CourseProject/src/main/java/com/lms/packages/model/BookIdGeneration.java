package com.lms.packages.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="BOOKIDGENERATION")
public class BookIdGeneration {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="GENRE", length=50, nullable=false, unique=false)
	private String genre;
	
	@Column(name="SUBGENRE", length=50, nullable=false, unique=false)
	private String subGenre;
		
	public BookIdGeneration(String genre,String subgenre) {
		this.genre = genre;
		this.subGenre = subgenre;
	}
	
	public BookIdGeneration() {
		
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
