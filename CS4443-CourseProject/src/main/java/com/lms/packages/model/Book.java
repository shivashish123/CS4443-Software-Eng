package com.lms.packages.model;

import java.util.List;
import java.util.ArrayList;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name="BOOK")
public class Book {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="Book_id", length=200, nullable=false, unique=false)
    private String book_id;
	
	@Column(name="TITLE", length=200, nullable=false, unique=false)
    private String title;
	
	@ManyToMany(cascade=CascadeType.REMOVE)
	@JoinTable(
	  name = "book_author", 
	  joinColumns = @JoinColumn(name = "book_id"), 
	  inverseJoinColumns = @JoinColumn(name = "author_id"))	
	private List<Author> authors;
	
	@ManyToOne(fetch = FetchType.LAZY,cascade=CascadeType.REMOVE)	
	@JoinTable(name="book_publisher")
	private Publisher publisher;
	
	@Column(name="COPIES", nullable=false, unique=false)
	private int copies;
	
	@Column(name="GENRE", length=50, nullable=false, unique=false)
	private String genre;
	
	@Column(name="SUBGENRE", length=50, nullable=false, unique=false)
	private String subGenre;
	
	public Book() {
		
	}
	public Book(String bookId,String title, List<Author> authors, Publisher publisher, int copies, String genre, String subgenre) {
		this.book_id = bookId;
		this.title = title;
		this.authors = authors;
		this.publisher = publisher;
		this.copies = copies;
		this.genre = genre;
		this.subGenre = subgenre;		
	}
	
	public String getTitle(){
		return title;
	}
	public void setTitle(String title){
		this.title = title;
	}
	
	public Publisher getPublisher(){
		return publisher;
	}
	public void setPublisher(Publisher publisher){
		this.publisher = publisher;
	}
	
	public List<Author> getAuthors(){
		return authors;
	}
	public void setAuthors(List<Author> authors){
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
