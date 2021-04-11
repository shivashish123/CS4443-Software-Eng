package com.lms.packages.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="PUBLISHER")
public class Publisher {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;	
	
	@Column(name="PUBLISHER_NAME", length=50, nullable=false, unique=false)
	private String publisherName;	
	
	@Column(name="CONTACT_NO", length=12, nullable=true, unique=false)
	private String contantno;	
	
	@Column(name="ADDRESS", length= 100 ,nullable=true, unique=false)
	private String address;	
	
	
	public Publisher(){
		
	}
	
	public Publisher(String publisherName){
		this.publisherName = publisherName;
	}
	
	public Publisher(String publisherName, String contactNo , String address){
		this.publisherName = publisherName;
		this.contantno = contactNo;
		this.address = address;				
	}
	
	public String getPublisherName() {
        return this.publisherName;
    }
      
	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}
	
    public String getContact() {
        return this.contantno;
    }
      
	public void setContact(String contantno) {
		this.contantno = contantno;
	}
	
	public String getAddress() {
		return this.address;
	}
	
	public void setAddress(String address) {
		 this.address = address;
	}

}
