package com.lms.packages.controller;


import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lms.packages.service.EmailSenderService;
import java.util.Random;
import com.lms.packages.model.ConfirmationToken;
import com.lms.packages.model.ERole;
import com.lms.packages.model.Feedback;
import com.lms.packages.model.Role;
import com.lms.packages.model.Person;
import com.lms.packages.repository.ConfirmationTokenRepository;
import com.lms.packages.repository.FeedbackRepository;
import com.lms.packages.repository.PersonRepository;
import com.lms.packages.repository.RoleRepository;
import com.lms.packages.security.services.UserDetailsImpl;
import com.lms.packages.payload.request.ConfirmPassword;
import com.lms.packages.payload.request.FeedbackRequest;
import com.lms.packages.payload.request.ForgotEmailRequest;
import com.lms.packages.payload.request.ForgotToken;
import com.lms.packages.payload.request.LoginRequest;
import com.lms.packages.payload.request.SearchRequest;
import com.lms.packages.payload.request.SignupRequest;
import com.lms.packages.payload.response.JwtResponse;
import com.lms.packages.payload.response.MessageResponse;
import com.lms.packages.security.jwt.JwtUtils;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	PersonRepository personRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;
	
	@Autowired
	FeedbackRepository feedbackRepository;
	
	@Autowired
	private ConfirmationTokenRepository confirmationTokenRepository;
	
	@Autowired
	private EmailSenderService emailSenderService;
	
	
	Random random = new Random(System.currentTimeMillis());

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
			
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));		
		
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		
		
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();		
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());
		String role = roles.get(0); 
		System.out.println(role);
		return ResponseEntity.ok(new JwtResponse(jwt, 
												 userDetails.getId(), 
												 userDetails.getUsername(), 
												 userDetails.getEmail(), 
												 role));
	}
	
	
	@PostMapping("/signup")	
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {

		if (personRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already in use!"));
		}

		// Create new user's account
		Person user = new Person(signUpRequest.getUsername() ,signUpRequest.getEmail(),
							 encoder.encode(signUpRequest.getPassword()),signUpRequest.getAddress(),signUpRequest.getContact(),signUpRequest.getDOB());

		String strRole = signUpRequest.getRole();
		Role role;
		
		System.out.println(signUpRequest.getContact());
		System.out.println(signUpRequest.getDOB());
		System.out.println(signUpRequest.getAddress());

		if (strRole == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			role = userRole;
		} else {
			
				switch (strRole) {
				case "ROLE_ADMIN":
					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					role = (adminRole);

					break;			
				default:
					Role userRole = roleRepository.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					role = (userRole);
				}		
		}
		
		user.setRole(role);		
		personRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}
	
	@RequestMapping(value="/forgot-password", method=RequestMethod.POST)
	public ResponseEntity<?> forgotUserPassword(@RequestBody ForgotEmailRequest request) {
		Person existingPerson = personRepository.findByEmailIgnoreCase(request.getEmail());
		if(existingPerson != null) {
			// create token
			ConfirmationToken confirmationToken = new ConfirmationToken(existingPerson);
			String otp = Integer.toString(random.nextInt(99999));
			confirmationToken.setConfirmationToken(otp) ;
			// save it
			confirmationTokenRepository.save(confirmationToken);
			
			// create the email
			
			SimpleMailMessage mailMessage = new SimpleMailMessage();
			mailMessage.setTo(request.getEmail());
			mailMessage.setSubject("Complete Password Reset!");
			
			mailMessage.setText("To complete the password reset process, please Enter the Otp "+ confirmationToken.getConfirmationToken());
			emailSenderService.sendEmail(mailMessage);

			

		} else {
				System.out.println("ok");
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: User not found!"));
		}
		
			return ResponseEntity.ok(new MessageResponse("Email sent to user successfully!"));
	}


	@RequestMapping(value="/confirm-reset", method= RequestMethod.POST)
	public ResponseEntity<?> validateResetToken( @RequestBody ForgotToken token)
	{	
		System.out.println(token.getToken());
		ConfirmationToken token1 = confirmationTokenRepository.findByConfirmationToken(token.getToken());
		
		if(token1 != null ) {
			Person person = personRepository.findByEmailIgnoreCase(token1.getPerson().getEmail());
			//person.setEnabled(true);
			personRepository.save(person);
			
		} else {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Wrong OTP!"));
		}
		
		return ResponseEntity.ok(new MessageResponse("Email sent to user successfully!"));
	}	

	
	@RequestMapping(value = "/reset-password", method = RequestMethod.POST)
	public ResponseEntity<?> resetUserPassword( @RequestBody ConfirmPassword password ) {
		 ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(password.getToken());
		 
		//if(person.getEmail() != null) {
			// use email to find user
			Person tokenUser = personRepository.findByEmailIgnoreCase(token.getPerson().getEmail());
			//tokenUser.setEnabled(true);
			tokenUser.setPassword(encoder.encode(password.getPassword()));
			// System.out.println(tokenUser.getPassword());
			personRepository.save(tokenUser);
			
		//} else {
			
		//}
		
		
		return ResponseEntity.ok(new MessageResponse("Email sent to user successfully!"));
	}
	@RequestMapping(value = "/savefeedback", method = RequestMethod.POST)
	public ResponseEntity<?> savefeedback( @RequestBody FeedbackRequest feedback ) {
		 
		System.out.println(feedback.getName());
		System.out.println(feedback.getEmail());
		System.out.println(feedback.getMessage());
		System.out.println(feedback.getRating());
		
		Feedback userfeedback=new Feedback(feedback.getName(),
				feedback.getEmail()
				,feedback.getMessage(),
				feedback.getRating());
		
		feedbackRepository.save(userfeedback);
		
		
		return ResponseEntity.ok(new MessageResponse("Feedback added successfully!"));
	}

}
