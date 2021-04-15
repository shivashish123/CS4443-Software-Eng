package com.lms.packages.payload.request;

import java.util.Date;

import javax.validation.constraints.*;

public class UserDashboardRequest {
 
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;
 
    public String getEmail() {
        return email;
    }
 
    public void setEmail(String email) {
        this.email = email;
    }
    
    
    
}