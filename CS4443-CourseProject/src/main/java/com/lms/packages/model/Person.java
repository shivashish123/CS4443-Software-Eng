package com.lms.packages.model;

import java.util.Date;

import javax.persistence.*;
import com.lms.packages.model.Role;

@Entity
@Table(name="PERSON")
public class Person {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	
	@Column(name="USER_NAME", length=50, nullable=false, unique=false)
	private String userName;
	
	// email address max length is 254
	@Column(name="USER_EMAIL",length=254, nullable=false, unique=true)
	private String emailId;
	
	@Column(name="USER_PASSWORD",length=50, nullable=false, unique=false)
	private String password;
	
	@Column(name="USER_FULLNAME", length=50, nullable=false, unique=false)
	private String name;
	
	
	
	@Temporal(TemporalType.DATE)
    private Date birthDate;
	
	@Column(name="CONTACT_NO", length=12, nullable=true, unique=false)
	private String contantNo;	
	
	@Column(name="ADDRESS", length= 100 ,nullable=true, unique=false)
	private String address;	
	
	@Column(name="FINE", nullable=true, unique=false)
	private int fine;
	
	@ManyToOne(fetch = FetchType.LAZY)	
	@JoinTable(name="role_id")
	private Role role;
	


	public Person(String username, String email, String password) {
		this.emailId = email;
		this.password = password;
		this.userName = username;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getname() {
		return name;
	}

	public void setname(String name) {
		this.name = name;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLoginId() {
		return emailId;
	}

	public void setEmail(String email) {
		this.emailId = email;
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
	
	
}