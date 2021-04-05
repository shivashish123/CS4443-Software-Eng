package com.lms.packages.payload.request;

import java.util.Date;

import javax.validation.constraints.*;

public class SignupRequest {
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;
 
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;
    
    @NotBlank
    private String role;
    
    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
    
    @Size(min=10, max=10)
    private String contact;
    
    private String address;
    
    private Date dob;
  
    public String getUsername() {
        return username;
    }
 
    public void setUsername(String username) {
        this.username = username;
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
    
    public String getRole() {
      return this.role;
    }
    
    public void setRole(String role) {
      this.role = role;
    }
    
    public String getAddress() {
        return this.address;
    }
      
	public void setAddress(String address) {
		this.address = address;
	}
	
    public String getContact() {
        return this.contact;
    }
      
	public void setContact(String contact) {
		this.contact = contact;
	}
	
	public Date getDOB() {
        return this.dob;
    }
      
	public void setDOB(Date dob) {
		this.dob = dob;
	}
    
    
}