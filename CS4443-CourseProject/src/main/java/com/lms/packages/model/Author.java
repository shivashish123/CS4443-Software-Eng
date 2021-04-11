package com.lms.packages.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="AUTHOR")
public class Author {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;	
	
	@Column(name="AUTHOR_NAME", length=50, nullable=false, unique=false)
	private String authorName;
	
	@ManyToMany(mappedBy = "authors")
	List<Book> books;
	
	@Temporal(TemporalType.DATE)
    private Date dob;
	
	@Column(name="CONTACT_NO", length=12, nullable=true, unique=false)
	private String contantno;	
	
	@Column(name="ADDRESS", length= 100 ,nullable=true, unique=false)
	private String address;	
	
	public Author() {
		// TODO Auto-generated constructor stub
	}
	
	public Author(String authorName,Date dob, String address) {
		// TODO Auto-generated constructor stub
		this.authorName = authorName;
		this.dob = dob;
		this.address = address;
	}
	
	public Author(String authorName) {
		// TODO Auto-generated constructor stub
		this.authorName = authorName;
	}
	
	

	public String getAuthorName() {
        return this.authorName;
    }
      
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	
    public String getContact() {
        return this.contantno;
    }
      
	public void setContact(String contantno) {
		this.contantno = contantno;
	}
	
	public Date getDOB() {
        return this.dob;
    }
      
	public void setDOB(Date dob) {
		this.dob = dob;
	}

}
