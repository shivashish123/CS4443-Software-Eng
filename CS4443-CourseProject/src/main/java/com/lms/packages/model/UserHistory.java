package com.lms.packages.model;

import java.util.List;
import java.util.ArrayList;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Table(name="UserHistory")
public class UserHistory {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="Book_id", length=200, nullable=false, unique=false)
    private String bookId;
	
	@Column(name="user_email", length=200, nullable=false, unique=false)
    private String emailID;
	
	@Column(name="activity", length=200, nullable=false, unique=false)
    private String activity;
	
	@Temporal(TemporalType.DATE)
    private Date activity_date;
	
	
	public UserHistory() {
		
	}
	
	public UserHistory(String bookid, String email, String activity, Date dob) {
		this.emailID = email;
		this.bookId = bookid;
		this.activity = activity;
		this.activity_date = dob;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getbookId() {
		return bookId;
	}

	public void setbookId(String bookId) {
		this.bookId = bookId;
	}

	public String getEmail() {
		return emailID;
	}

	public void setEmail(String emailID) {
		this.emailID = emailID;
	}
	
	public String getActivity() {
        return this.activity;
    }
      
	public void setActivity(String activity) {
		this.activity = activity;
	}
	
	public Date getActivityDate() {
        return this.activity_date;
    }
      
	public void setActivityDate(Date activity_date) {
		this.activity_date = activity_date;
	}
	
}
