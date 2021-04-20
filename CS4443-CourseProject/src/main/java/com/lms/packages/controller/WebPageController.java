package com.lms.packages.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lms.packages.payload.response.MessageResponse;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/webpage")
public class WebPageController {
	@GetMapping("/admin")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<MessageResponse> adminPageAccess() {
		System.out.println("admin page");
		return ResponseEntity.ok(new MessageResponse("Access Granted"));
	}	
	
	@GetMapping("/addBook")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<MessageResponse> addBookPageAccess() {
		System.out.println("add Book page");
		return ResponseEntity.ok(new MessageResponse("Access Granted"));
	}	
	
	@GetMapping("/removeBook")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<MessageResponse> removeBookPageAccess() {
		System.out.println("remove Book page");
		return ResponseEntity.ok(new MessageResponse("Access Granted"));
	}	
	
	@GetMapping("/addCopies")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<MessageResponse> addCopiesPageAccess() {
		System.out.println("add Copies page");
		return ResponseEntity.ok(new MessageResponse("Access Granted"));
	}	
	
	@GetMapping("/approveIssues")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<MessageResponse> approveIssuesPageAccess() {
		System.out.println("Approve Issuespage");
		return ResponseEntity.ok(new MessageResponse("Access Granted"));
	}	
	
	@GetMapping("/bookIssues")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<MessageResponse> bookIssuesPageAccess() {
		System.out.println("Book Issues page");
		return ResponseEntity.ok(new MessageResponse("Access Granted"));
	}	
}
