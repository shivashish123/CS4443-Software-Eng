package com.lms.packages.model;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.lms.packages.model.Role;

@Entity
@Table(name="PERSON")
public class Person {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	
	@Column(name="USER_NAME", length=50, nullable=false, unique=false)
	private String username;
	
	// email address max length is 254
	@Column(name="USER_EMAIL",length=254, nullable=false, unique=true)
	private String email;
	
	@Column(name="USER_PASSWORD",length=120, nullable=false, unique=false)
	private String password;		
	
	@Temporal(TemporalType.DATE)
    private Date dob;
	
	@Column(name="CONTACT_NO", length=12, nullable=false, unique=false)
	private String contantno;	
	
	@Column(name="ADDRESS", length= 100 ,nullable=true, unique=false)
	private String address;	

	@Column(name="FINE", nullable=true, unique=false)
	private int fine;
	
	

	@ManyToOne	
	@JoinTable(name="role_id")
	private Role role;
	
	@OneToMany(mappedBy="user")
	@Column(name="issue_list",nullable=true)
	@JsonManagedReference
	private List<Issue> issues;
	
	public List<Issue> getIssues() {
		return issues;
	}

	public void setIssues(List<Issue> issues) {
		this.issues = issues;
	}

	public Person() {
		
	}

	public Person(String username, String email, String password,String address, String contantno , Date dob) {
		this.email = email;
		this.password = password;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
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

	
	public int getFine() {
		return fine;
	}

	public void setFine(int fine) {
		this.fine = fine;
	}
	
	
}