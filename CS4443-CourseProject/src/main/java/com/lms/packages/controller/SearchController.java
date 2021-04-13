package com.lms.packages.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lms.packages.model.Book;
import com.lms.packages.model.Staff;
import com.lms.packages.payload.request.SearchRequest;
import com.lms.packages.repository.BookRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/search")
public class SearchController {
	
	@Autowired
	BookRepository bookRepository;
		
	@PostMapping("/search-book")
	public ResponseEntity<?> searchByKeyword(@Valid @RequestBody SearchRequest searchRequest){
		
		List<Book> entities = bookRepository.findAll();
		System.out.println(entities.size());
		return ResponseEntity.ok(entities);
		
	}
}
