package com.lms.packages.model;

import java.util.List;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="rating_review")
public class Ratings {
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "rating_id")
	private Long id;
	
	@Column(name="ratings", nullable=true, unique=false)
	private double rating;
	
	
	@Column(name="reviews", nullable=true, unique=false)
	private String review;
	
	
	
	@ManyToOne(cascade=CascadeType.REMOVE)
	@JoinTable(name="rating_user")
	@JsonBackReference
	private Person user;
	
	@ManyToOne(cascade=CascadeType.REMOVE)	
	@JoinTable(name="rating_book")
	@JsonBackReference
	private Book book;
	
	
	public double getRating() {
		return rating;
	}


	public void setRating(double rating) {
		this.rating = rating;
	}


	public String getReview() {
		return review;
	}


	public void setReview(String review) {
		this.review = review;
	}



	


	public Book getBook() {
		return book;
	}


	public void setBook(Book book) {
		this.book = book;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Person getUser() {
		return user;
	}


	public void setUser(Person user) {
		this.user = user;
	}

	

}
