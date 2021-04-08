package com.lms.packages.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lms.packages.payload.request.AddBookRequest;
import com.lms.packages.payload.request.StaffSignupRequest;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class BookController {
	
	@PostMapping("/add-book")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> addBook(@Valid @RequestBody AddBookRequest addBookRequest) {
		System.out.println("here");
		System.out.println(addBookRequest.getTitle());
		System.out.println(addBookRequest.getPublisher());
		System.out.println(addBookRequest.getGenre());
		System.out.println(addBookRequest.getSubGenre());
		System.out.println(addBookRequest.getCopies());
		System.out.println(addBookRequest.getAuthors().length);
		
		return null;
	}

}
