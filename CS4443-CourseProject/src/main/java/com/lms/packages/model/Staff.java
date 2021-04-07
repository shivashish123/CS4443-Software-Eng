package com.lms.packages.model;

import java.util.Date;

import javax.persistence.*;
import com.lms.packages.model.Role;

@Entity
@Table(name="Staff")
public class Staff {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	
	@Column(name="USER_NAME", length=50, nullable=false, unique=false)
	private String username;
	
	// email address max length is 254
	@Column(name="USER_EMAIL",length=254, nullable=false, unique=true)
	private String email;
		
	@Temporal(TemporalType.DATE)
    private Date dob;
	
	@Column(name="CONTACT_NO", length=12, nullable=false, unique=false)
	private String contantno;	
	
	@Column(name="ADDRESS", length= 100 ,nullable=true, unique=false)
	private String address;	
	
	public Staff() {
		
	}

	public Staff(String username, String email, String address, String contantno , Date dob) {
		this.email = email;
		this.username = username;
		this.address = address;
		this.contantno = contantno;
		this.dob = dob;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getUserName() {
		return username;
	}

	public void setUserName(String userName) {
		this.username = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getAddress() {
        return this.address;
    }
      
	public void setAddress(String address) {
		this.address = address;
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