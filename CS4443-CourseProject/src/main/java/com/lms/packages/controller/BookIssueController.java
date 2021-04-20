package com.lms.packages.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import com.lms.packages.service.EmailSenderService;
import com.lms.packages.payload.request.ApproveIssueRequest;
import com.lms.packages.payload.request.BookIssueRequest;
import com.lms.packages.payload.request.GetIssueRequest;
import com.lms.packages.payload.request.GetOTP;
import com.lms.packages.payload.request.ShowBookRequest;
import com.lms.packages.payload.response.IssueRequestResponse;
import com.lms.packages.payload.response.MessageResponse;
import com.lms.packages.repository.BookRepository;
import com.lms.packages.repository.IssueOTPRepository;
import com.lms.packages.repository.IssueRepository;
import com.lms.packages.repository.PersonRepository;
import com.lms.packages.security.jwt.JwtUtils;
import com.lms.packages.model.Book;
import com.lms.packages.model.ConfirmationToken;
import com.lms.packages.model.Issue;
import com.lms.packages.model.IssueOTP;
import com.lms.packages.model.Person;
import com.lms.packages.model.Publisher;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/issue")
public class BookIssueController {
	@Autowired
	BookRepository bookRepository;
	@Autowired
	PersonRepository personRepository;
	@Autowired
	private JwtUtils jwtUtils;
	@Autowired
	IssueRepository issueRepository;
	@Autowired
	IssueOTPRepository issueOTPRepository;
	Random random = new Random(System.currentTimeMillis());
	
	@Autowired
	private EmailSenderService emailSenderService;
	
	@PostMapping("/issue-book")
	public ResponseEntity<?>issuebook(@RequestHeader(value="Authorization")String header,@Valid @RequestBody ShowBookRequest request){
		
		System.out.println(request.getId());
		String token= header.substring(7, header.length());
		String email = jwtUtils.getEmailFromJwtToken(token);
		System.out.println("User initiated issue request "+email);
		Optional<Book> book= bookRepository.findBybookId(request.getId());
		Person user = personRepository.findByEmailIgnoreCase(email);
		
		if(book.get().getCurrentCopies()==0){
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("No copies of book available please initiate book request."));
		}
		else {
			Date currdate=new Date();
			
			Calendar c= Calendar.getInstance();
			c.add(Calendar.DATE, 30);
			Date returndate=c.getTime();
			bookRepository.decreaseCopies(request.getId());
			Issue bookissue = new Issue();
			bookissue.setBook(book.get());
			bookissue.setUser(user);
			bookissue.setIssueDate(currdate);
			bookissue.setReturnDate(returndate);
			if(user.getIssues().isEmpty()) {
				List<Issue> issueList=new ArrayList<Issue>();
				issueList.add(bookissue);
				user.setIssues(issueList);
			}else {
				
				List<Issue> issueList=user.getIssues();
				if(issueList.size()==20) {
					return ResponseEntity
							.badRequest()
							.body(new MessageResponse("Maximum issue's reached "));
				}
				issueList.add(bookissue);
				user.setIssues(issueList);
			}
			issueRepository.save(bookissue);
			personRepository.save(user);
			
//			
//			IssueOTP issueotp = new IssueOTP(user);
//			
//			// save it
//			issueOTPRepository.save(issueotp);
//			
//			// create the email
//			
//			SimpleMailMessage mailMessage = new SimpleMailMessage();
//			mailMessage.setTo(user.getEmail());
//			mailMessage.setSubject("Issue Book OTP!");
//			
//			mailMessage.setText("To complete the transaction please show the OTP to Admin "+ issueotp.getIssuetoken());
//			emailSenderService.sendEmail(mailMessage);
			return ResponseEntity.ok(new MessageResponse("Book issued successfully"));
		}
	}
	
	
	@PostMapping("/get-issues")
	public ResponseEntity<?> getIssues(@Valid @RequestBody GetIssueRequest getIssueRequest){
		System.out.println(getIssueRequest.getEmail());
		String email =  getIssueRequest.getEmail();
		Optional<Person> user= personRepository.findByEmail(email);
		if(user.isEmpty()) {
			return ResponseEntity.ok(new MessageResponse("Email Id does not exist in records "));
		}
		List<Issue> issues = issueRepository.getIssuePerson(email);
		List<IssueRequestResponse> issueResponses = new ArrayList<IssueRequestResponse>();
		System.out.println(user.get().getUserName());
		for(Issue issue : issues) {
			IssueRequestResponse response = new IssueRequestResponse( issue.getIssueId(),
					user.get().getUserName(),
					user.get().getEmail(),
					issue.getBook().getBookId(),
					issue.getBook().getTitle(),
					issue.getIssueDate(),
					issue.getReturnDate());
			issueResponses.add(response);
		}
		
		return ResponseEntity.ok(issueResponses);
		
	}
	
	@PostMapping("/get-otp")
	public ResponseEntity<?> getIssueApproveOTP(@Valid @RequestBody GetOTP getOTP){
		
		IssueOTP issueotp = new IssueOTP();
		String otp = Integer.toString(random.nextInt(99999));		
		issueotp.setIssuetoken(otp) ;
		issueOTPRepository.save(issueotp);
		
		
		System.out.println(otp);
		// create the email
		Optional<Person> user= personRepository.findByEmail(getOTP.getEmail());
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(user.get().getEmail());
		mailMessage.setSubject("Issue Book OTP!");
		
		mailMessage.setText("To complete the transaction please show the OTP to Admin "+ otp);
		emailSenderService.sendEmail(mailMessage);
		
		return ResponseEntity.ok(issueotp.getTokenid());
		
	}
		
	
	@PostMapping("/approve-issues")
	public ResponseEntity<?> ApproveIssues(@Valid @RequestBody ApproveIssueRequest approveIssueRequest){
		String email = approveIssueRequest.getEmail();
		Optional<Person> user= personRepository.findByEmail(email);
		if(user.isEmpty()) {
			return ResponseEntity.ok(new MessageResponse("Email Id does not exist in records "));
		}
		
		Optional<IssueOTP> issueOTP = issueOTPRepository.findBytokenid(approveIssueRequest.getIssueId());
		if(issueOTP.isEmpty()) {
			return ResponseEntity.badRequest()
					.body(new MessageResponse("Error: Issue ID does not exist"));
		}else if(!issueOTP.get().getIssuetoken().equals(approveIssueRequest.getOTP())) {
			System.out.println("otp does not match");
			return ResponseEntity.badRequest()
					.body(new MessageResponse("OTP does not match"));
		}else {
			
			List<Issue> issues = issueRepository.getIssuePerson(email);
			for(Issue issue: issues) {
				issue.setTaken(true);
				issueRepository.save(issue);
			}
			return ResponseEntity.ok(new MessageResponse("All the issues were successfully approved"));	
		}		
	}	
	
	@PostMapping("/book-issues")
	public ResponseEntity<?> BookIssues(@Valid @RequestBody BookIssueRequest bookIssueRequest){
		
		System.out.println("book issues");
		Optional<Book> book= bookRepository.findBybookId(bookIssueRequest.getbookId());
		System.out.println(bookIssueRequest.getbookId());
		if(!book.isEmpty()) {				
			List<Issue> issues = issueRepository.getIssueBook(book.get());
			List<IssueRequestResponse> issueResponses = new ArrayList<IssueRequestResponse>();
			System.out.println(issues.size());
			for(Issue issue : issues) {
				IssueRequestResponse response = new IssueRequestResponse( issue.getIssueId(),
						issue.getUser().getUserName(),
						issue.getUser().getEmail(),
						issue.getBook().getBookId(),
						issue.getBook().getTitle(),
						issue.getIssueDate(),
						issue.getReturnDate());
				issueResponses.add(response);
			}
			
			return ResponseEntity.ok(issueResponses);
			
		}else {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: ID does not exist"));
		}			
	}
}
