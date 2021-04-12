package com.lms.packages.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lms.packages.payload.request.SearchRequest;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/search")
public class SearchController {

		
	@PostMapping("/search-book")
	public ResponseEntity<?> searchByKeyword(@Valid @RequestBody SearchRequest searchRequest){
		
		System.out.println(searchRequest.getKeyword());
		System.out.println(searchRequest.getSortBy());
		System.out.println(searchRequest.getSearchBy());
		return null;
		
	}
}
