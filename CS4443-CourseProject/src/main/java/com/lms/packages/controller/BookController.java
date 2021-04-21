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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.lms.packages.model.Author;
import com.lms.packages.model.Book;
import com.lms.packages.model.Person;
import com.lms.packages.model.Publisher;
import com.lms.packages.model.Ratings;
import com.lms.packages.payload.request.AddBookRequest;
import com.lms.packages.payload.request.AddCopiesRequest;
import com.lms.packages.payload.request.RatingsRequest;
import com.lms.packages.payload.request.RemoveBookRequest;
import com.lms.packages.payload.request.ShowBookRequest;
import com.lms.packages.payload.request.StaffSignupRequest;
import com.lms.packages.payload.response.MessageResponse;
import com.lms.packages.repository.AuthorRepository;
import com.lms.packages.repository.BookIdGenerationRepository;
import com.lms.packages.repository.BookRepository;
import com.lms.packages.repository.PersonRepository;
import com.lms.packages.repository.PublisherRepository;
import com.lms.packages.repository.RatingRepository;
import com.lms.packages.utils.GenerateID;
import com.lms.packages.security.jwt.JwtUtils;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/book")
public class BookController {
	
	@Autowired
	AuthorRepository authorRepository;
	
	@Autowired
	PublisherRepository publisherRepository;
	
	@Autowired
	RatingRepository ratingRepository;
	
	@Autowired
	BookRepository bookRepository ;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	PersonRepository personRepository;
	
	@Autowired
	BookIdGenerationRepository bookIdGenerationRepository ;
	
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
		String bookId = generateId.generateBookId(addBookRequest.getGenre(),addBookRequest.getSubGenre(),bookIdGenerationRepository);
		
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
		Optional<Book> book = bookRepository.findBybookId(showBookRequest.getId());
		
		if(!book.isEmpty()) {
			byte[] image = book.get().getContent();
			return new ByteArrayResource(image);
		}else {
			return null;
		}
	    
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
	
	
	@PostMapping("/add-rating")
	public ResponseEntity<?> rateBook(@RequestHeader(value="Authorization")String header,@Valid @RequestBody RatingsRequest ratings){
		
		String token= header.substring(7, header.length());
		String email = jwtUtils.getEmailFromJwtToken(token);
		System.out.println("User initiated rating request "+email);
		System.out.println("User initiated rating request for book  "+ratings.getBookid());
		System.out.println("Ratings "+ratings.getRating() +" Review "+ ratings.getReview());
		Person user = personRepository.findByEmailIgnoreCase(email);
		Optional<Book> book = bookRepository.findBybookId(ratings.getBookid());
				
		Ratings newRating =new Ratings();

		newRating.setBook(book.get());
		newRating.setUser(user);
		
		if(book.get().getRating().isEmpty()) {
			List<Ratings> book_rating_list=new ArrayList<Ratings>();
			book_rating_list.add(newRating);
		}
		else {
			List<Ratings> book_rating_list= book.get().getRating();
			book_rating_list.add(newRating);
		}
		if(user.getRating().isEmpty()) {
			List<Ratings>user_rating_list=new ArrayList<Ratings>();
			user_rating_list.add(newRating);
		}
		else {
			List<Ratings> user_rating_list=user.getRating();
			user_rating_list.add(newRating);
		}
		
		
		newRating.setRating(ratings.getRating());
		newRating.setReview(ratings.getReview());
		
		ratingRepository.save(newRating);
		personRepository.save(user);
		bookRepository.save(book.get());
		System.out.println("Book rated insane");
		
		return ResponseEntity.ok(new MessageResponse("Successfully Rated the Book"));
	}
}
