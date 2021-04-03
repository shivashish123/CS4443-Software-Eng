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
@Table(name="USER")
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	// email address max length is 254
	@Column(name="USER_LOGIN_ID",length=254, nullable=false, unique=true)
	private String loginId;
	
	@Column(name="USER_NAME", length=50, nullable=false, unique=false)
	private String name;
	
	@Temporal(TemporalType.DATE)
    private Date birthDate;
	
	@Column(name="CONTACT_NO", length=12, nullable=true, unique=false)
	private String contantNo;	
	
	@Column(name="ADDRESS", length= 100 ,nullable=true, unique=false)
	private String address;	
	
	@Column(name="FINE", nullable=true, unique=false)
	private int fine;	
	
	
}