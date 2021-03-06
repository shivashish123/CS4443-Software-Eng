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
import com.lms.packages.model.UserHistory;

import com.lms.packages.repository.PersonRepository;
import com.lms.packages.repository.RoleRepository;
import com.lms.packages.repository.StaffRepository;
import com.lms.packages.repository.UserHistoryRepository;
import com.lms.packages.security.services.UserDetailsImpl;
import com.lms.packages.payload.request.LoginRequest;
import com.lms.packages.payload.request.SearchRequest;
import com.lms.packages.payload.request.SignupRequest;
import com.lms.packages.payload.request.StaffSignupRequest;
import com.lms.packages.payload.request.StaffRemoveRequest;
import com.lms.packages.payload.request.UserHistoryRequest;
import com.lms.packages.payload.response.JwtResponse;
import com.lms.packages.payload.response.MessageResponse;
import com.lms.packages.security.jwt.JwtUtils;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AdminController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	PersonRepository personRepository;

	@Autowired
	StaffRepository staffRepository;
	
	@Autowired
	UserHistoryRepository userHistoryRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;
	
	@Autowired
	RoleRepository roleRepository;

	@PostMapping("/add-staff")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> addStaff(@Valid @RequestBody StaffSignupRequest staffSignupRequest) {
		System.out.println("here");
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
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> removeStaff(@Valid @RequestBody StaffRemoveRequest staffRemoveRequest) {
		int deletedRecords = staffRepository.deleteByEmail(staffRemoveRequest.getEmail());
		if(deletedRecords==1)
			return ResponseEntity.ok(new MessageResponse("Staff member removed successfully!"));
		else
			return ResponseEntity.ok(new MessageResponse("No staff member with given emailID exists!"));
	}
	
	@PostMapping("/info-staff")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> getAllStaff() {
		List<Staff> entities = staffRepository.findAll();
		return ResponseEntity.ok(entities);
	}
	
	@PostMapping("/get-user-history")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> getUserHistory(@Valid @RequestBody UserHistoryRequest urequest) {
		List<UserHistory> entities = userHistoryRepository.getHistory(urequest.getEmail());	
		return ResponseEntity.ok(entities);
	}
	
	@PostMapping("/info-user")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> getAllUsers() {
		List<Person> entities = personRepository.getAllUsers();
		return ResponseEntity.ok(entities);
	}
	
	@PostMapping("/add-admin")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {

		if (personRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already in use!"));
		}

		// Create new user's account
		Person user = new Person(signUpRequest.getUsername() ,signUpRequest.getEmail(),
							 encoder.encode(signUpRequest.getPassword()),signUpRequest.getAddress(),signUpRequest.getContact(),signUpRequest.getDOB());

		//String strRole = signUpRequest.getRole();
		Role role;
		
		System.out.println(signUpRequest.getContact());
		System.out.println(signUpRequest.getDOB());
		System.out.println(signUpRequest.getAddress());
		
		Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
				.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
		role = (adminRole);

		
		user.setRole(role);		
		personRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("Admin added successfully!"));
	}

}
