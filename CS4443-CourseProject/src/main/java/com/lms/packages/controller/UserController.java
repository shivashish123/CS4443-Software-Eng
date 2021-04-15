package com.lms.packages.controller;


import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lms.packages.model.ERole;
import com.lms.packages.model.Role;
import com.lms.packages.model.Person;
import com.lms.packages.model.Staff;

import com.lms.packages.repository.PersonRepository;
import com.lms.packages.repository.RoleRepository;
import com.lms.packages.repository.StaffRepository;
import com.lms.packages.security.services.UserDetailsImpl;
import com.lms.packages.payload.request.UserDashboardRequest;
import com.lms.packages.payload.request.SearchRequest;
import com.lms.packages.payload.request.SignupRequest;
import com.lms.packages.payload.request.StaffSignupRequest;
import com.lms.packages.payload.request.StaffRemoveRequest;
import com.lms.packages.payload.response.JwtResponse;
import com.lms.packages.payload.response.MessageResponse;
import com.lms.packages.security.jwt.JwtUtils;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class UserController {

	@Autowired
	PersonRepository personRepository;
	
	@PostMapping("/user-details")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<?> getDetails(@Valid @RequestBody UserDashboardRequest userDashboardRequest) {
		Person entity = personRepository.getUser(userDashboardRequest.getEmail());
		return ResponseEntity.ok(entity);
	}

}
