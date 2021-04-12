package com.lms.packages.controller;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lms.packages.model.Author;
import com.lms.packages.model.Book;
import com.lms.packages.model.Publisher;
import com.lms.packages.payload.request.AddBookRequest;
import com.lms.packages.payload.request.RemoveBookRequest;
import com.lms.packages.payload.request.StaffSignupRequest;
import com.lms.packages.repository.AuthorRepository;
import com.lms.packages.repository.BookRepository;
import com.lms.packages.repository.PublisherRepository;
import com.lms.packages.utils.GenerateID;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class BookController {
	
	@Autowired
	AuthorRepository authorRepository;
	
	@Autowired
	PublisherRepository publisherRepository;
	
	@Autowired
	BookRepository bookRepository ;
	
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
		
		// get author
		List<Author> authors = new ArrayList<Author>();
		for(String authorName:addBookRequest.getAuthors()) {
			Optional<Author> author = authorRepository.findByAuthorName(authorName);			
			if(author.isEmpty()){
				Author author1 = new Author(authorName);
				authorRepository.save(author1);
				author = Optional.ofNullable(author1);
			}
			authors.add(author.get());				
		}
		
		// get publisher
		Optional<Publisher> publisher = publisherRepository.findByPublisherName(addBookRequest.getPublisher());
		if(publisher.isEmpty()) {
			Publisher publisher1 = new Publisher(addBookRequest.getPublisher());
			publisherRepository.save(publisher1);
			publisher = Optional.ofNullable(publisher1);
		}
		
		
		// get Id
		GenerateID generateId = new GenerateID();		
		String bookId = generateId.generateBookId(addBookRequest.getGenre(),addBookRequest.getSubGenre(),bookRepository);
		
		System.out.println(bookId);
		Book book = new Book(bookId,
						addBookRequest.getTitle(),
						authors,
						publisher.get(),
						addBookRequest.getCopies(),
						addBookRequest.getGenre(),
						addBookRequest.getSubGenre()				
				);
		bookRepository.save(book);
				
		
		
		return null;
	}
	
	@PostMapping("/remove-book")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> removeBook(@Valid @RequestBody RemoveBookRequest removeBookRequest) {
		
		System.out.println(removeBookRequest.getId());		
		bookRepository.deleteById(removeBookRequest.getId());
		return null;	
	}

}
