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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lms.packages.model.ERole;
import com.lms.packages.model.Role;
import com.lms.packages.model.Person;
import com.lms.packages.model.Staff;

import com.lms.packages.repository.PersonRepository;
import com.lms.packages.repository.StaffRepository;
import com.lms.packages.security.services.UserDetailsImpl;
import com.lms.packages.payload.request.LoginRequest;
import com.lms.packages.payload.request.SearchRequest;
import com.lms.packages.payload.request.StaffSignupRequest;
import com.lms.packages.payload.response.JwtResponse;
import com.lms.packages.payload.response.MessageResponse;
import com.lms.packages.security.jwt.JwtUtils;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	PersonRepository personRepository;

	@Autowired
	StaffRepository staffRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@PostMapping("/add-staff")
	public ResponseEntity<?> addStaff(@Valid @RequestBody StaffSignupRequest staffSignupRequest) {
		
		if (staffRepository.existsByUsername(staffSignupRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken!"));
		}

		if (staffRepository.existsByEmail(staffSignupRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already in use!"));
		}
		Staff user = new Staff(staffSignupRequest.getUsername() ,staffSignupRequest.getEmail(),
				staffSignupRequest.getAddress(),staffSignupRequest.getContact(),staffSignupRequest.getDOB());
		staffRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("Staff member registered successfully!"));
	}
	
	
	@PostMapping("/remove-staff")	
	public ResponseEntity<?> registerUser() {
		return ResponseEntity.ok(new MessageResponse("Staff member removed successfully!"));
	}
	

}