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
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="BOOK")
public class Book {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="Book_id", length=200, nullable=false, unique=false)
    private String bookId;
	
	@Column(name="TITLE", length=200, nullable=false, unique=false)
    private String title;
	
	@ManyToMany(cascade=CascadeType.REMOVE)
	@JoinTable(
	  name = "book_author", 
	  joinColumns = @JoinColumn(name = "bookId"), 
	  inverseJoinColumns = @JoinColumn(name = "author_id"))	
	private List<Author> authors;
	
	@ManyToOne(cascade=CascadeType.REMOVE)	
	@JoinTable(name="book_publisher")
	private Publisher publisher;
	
	@Column(name="CURR_COPIES", nullable=false, unique=false)
	private int currentCopies;
	
	@Column(name="TOT_COPIES", nullable=false, unique=false)
	private int totalCopies;
	
	@Column(name="GENRE", length=50, nullable=false, unique=false)
	private String genre;
	
	@Column(name="SUBGENRE", length=50, nullable=false, unique=false)
	private String subGenre;
	
	@OneToMany(mappedBy="book")
	@Column(name="book_ratings",nullable=true)
	@JsonManagedReference
	private List<Ratings> rating;
	
	@Column(name="Popularity",nullable=false , unique=false)
	private double popularity = 0.0;
	
	@Lob
    private byte[] content;
	
    private String fileName;
    
    @OneToMany(mappedBy="book")
	private List<Issue> issues;
	
	public Book() {
		
	}
	public Book(String bookId,String title, List<Author> authors, Publisher publisher, int copies, String genre, String subgenre,byte[] content,String fileName) {
		this.bookId = bookId;
		this.title = title;
		this.authors = authors;
		this.publisher = publisher;
		this.currentCopies = copies;
		this.totalCopies = copies;
		this.genre = genre;
		this.subGenre = subgenre;	
		this.fileName = fileName;
		this.content = content;
		
		this.popularity = 0.0;
	}
	
	public String getBookId(){
		return bookId;
	}
	public void setBookId(String bookId){
		this.bookId = bookId;
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
	
	public int getCurrentCopies(){
		return currentCopies;
	}
	public void setCurrentCopies(int copies){
		this.currentCopies = copies;
	}
	
	public int getTotalCopies(){
		return totalCopies;
	}
	public void setTotalCopies(int copies){
		this.totalCopies = copies;
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
	
	public byte[] getContent(){
		return content;
	}
	public void setContent(byte[] content){
		this.content = content;
	}
	
	public String getFilename(){
		return fileName;
	}
	public void setFilename(String fileName){
		this.fileName = fileName;
	}
	
	
	
	public List<Ratings> getRating() {
		return rating;
	}
	public void setRating(List<Ratings> rating) {
		this.rating = rating;
	}
	public double getPopularity(){
		return popularity;
	}
	public void setPopularity(double rating){
		this.popularity = popularity;
	}
	public Long getId() {
		return id;
	}
	
	
	
}
