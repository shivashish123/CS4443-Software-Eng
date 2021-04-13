package com.lms.packages.controller;

import java.util.List;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.lms.packages.model.Author;
import com.lms.packages.model.Book;
import com.lms.packages.model.Publisher;
import com.lms.packages.payload.request.AddBookRequest;
import com.lms.packages.payload.request.AddCopiesRequest;
import com.lms.packages.payload.request.RemoveBookRequest;
import com.lms.packages.payload.request.ShowBookRequest;
import com.lms.packages.payload.request.StaffSignupRequest;
import com.lms.packages.payload.response.MessageResponse;
import com.lms.packages.repository.AuthorRepository;
import com.lms.packages.repository.BookRepository;
import com.lms.packages.repository.PublisherRepository;
import com.lms.packages.utils.GenerateID;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/book")
public class BookController {
	
	@Autowired
	AuthorRepository authorRepository;
	
	@Autowired
	PublisherRepository publisherRepository;
	
	@Autowired
	BookRepository bookRepository ;
	
	@PostMapping(value = "/add-book",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> addBook(@Valid @ModelAttribute AddBookRequest addBookRequest) throws IOException {
		System.out.println("here");
		System.out.println(addBookRequest.getTitle());
		System.out.println(addBookRequest.getPublisher());
		System.out.println(addBookRequest.getGenre());
		System.out.println(addBookRequest.getSubGenre());
		System.out.println(addBookRequest.getCopies());
		System.out.println(addBookRequest.getAuthors().length);
		System.out.println(addBookRequest.getCover().getOriginalFilename());
		
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
						addBookRequest.getSubGenre(),
						addBookRequest.getCover().getBytes(),
						addBookRequest.getCover().getOriginalFilename()
				);
		bookRepository.save(book);
				
		
		
		return ResponseEntity.ok(new MessageResponse("Book added successfully!"));
	}
	
	@PostMapping("/remove-book")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> removeBook(@Valid @RequestBody RemoveBookRequest removeBookRequest) {
		
		Optional<Book> book= bookRepository.findBybookId(removeBookRequest.getId());
		System.out.println(removeBookRequest.getId());
		if(!book.isEmpty()) {				
			bookRepository.deleteById(removeBookRequest.getId());
			return ResponseEntity.ok(new MessageResponse("Book deleted successfully!"));
		}else {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: ID does not exist"));
		}		
	}
	
	@PostMapping(value = "/image", produces = MediaType.IMAGE_JPEG_VALUE)
	Resource downloadImage(@Valid @RequestBody ShowBookRequest showBookRequest) {
		
		System.out.println(showBookRequest.getId());
	    byte[] image = bookRepository.findBybookId(showBookRequest.getId())
	      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND))
	      .getContent();

	    return new ByteArrayResource(image);
	}
	
	@PostMapping("/add-copies")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> addCopies(@Valid @RequestBody AddCopiesRequest addCopiesRequest) {
		
		System.out.println(addCopiesRequest.getCount());
		System.out.println(addCopiesRequest.getId());
		Optional<Book> book= bookRepository.findBybookId(addCopiesRequest.getId());
		if(!book.isEmpty()) {
			bookRepository.updateCopies(addCopiesRequest.getId(),addCopiesRequest.getCount());
			
			return ResponseEntity.ok(new MessageResponse("New Copies added successfully!"));
		}else {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: ID does not exist"));
		}		
	}
}
