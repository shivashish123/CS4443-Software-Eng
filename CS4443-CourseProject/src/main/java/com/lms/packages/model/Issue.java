package com.lms.packages.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="ISSUE")
public class Issue {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "issue_id")
	private Long id;
	
	@ManyToOne(cascade=CascadeType.REMOVE)	
	@JoinTable(name="issue_user")
	private Person user;
	
	@ManyToOne(cascade=CascadeType.REMOVE)	
	@JoinTable(name="issue_book")
	private Book book;
	
	@Column(name="ISSUE_DATE", nullable=false, unique=false)
	private Date issueDate;
	
	@Column(name="RETURN_DATE", nullable=false, unique=false)
	private Date returnDate;
	
	@Column(name="TAKEN", nullable=false, unique=false , columnDefinition = "bit default 0")
	private boolean taken = false;
	
	public long getIssueId() {
		return id;
	}
	
	public Person getUser() {
		return user;		
	}
	
	public void setUser(Person user) {
		this.user = user;
	}
	
	public Book getBook() {
		return book;		
	}
	
	public void setBook(Book book) {
		this.book = book;
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
	
	public void setTaken(boolean taken) {
		this.taken = taken;
	}
	
	public boolean getTaken() {
		return taken;
	}
}
