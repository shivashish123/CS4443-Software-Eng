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
import com.lms.packages.payload.request.ShowBookRequest;
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

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/issue")
public class BookIssueController {
	@Autowired
	BookRepository bookrepository;
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
		Optional<Book> book= bookrepository.findBybookId(request.getId());
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
			bookrepository.decreaseCopies(request.getId());
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
				if(issueList.size()==3) {
					return ResponseEntity
							.badRequest()
							.body(new MessageResponse("Maximum issue's reached "));
				}
				issueList.add(bookissue);
				user.setIssues(issueList);
			}
			issueRepository.save(bookissue);
			personRepository.save(user);
			IssueOTP issueotp = new IssueOTP(user);
			String otp = Integer.toString(random.nextInt(99999));
			issueotp.setIssuetoken(otp) ;
			// save it
			issueOTPRepository.save(issueotp);
			
			// create the email
			
			SimpleMailMessage mailMessage = new SimpleMailMessage();
			mailMessage.setTo(user.getEmail());
			mailMessage.setSubject("Issue Book OTP!");
			
			mailMessage.setText("To complete the transaction please show the OTP to Admin "+ issueotp.getIssuetoken());
			emailSenderService.sendEmail(mailMessage);
			return ResponseEntity.ok(new MessageResponse("Book issued successfully"));
		}
		
		
		
	}
}
