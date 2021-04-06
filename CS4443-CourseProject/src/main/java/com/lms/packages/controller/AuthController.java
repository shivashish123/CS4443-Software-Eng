package com.lms.packages.controller;


import java.util.List;
import java.util.Random;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.lms.packages.model.ERole;
import com.lms.packages.model.Role;
import com.lms.packages.model.Person;

import com.lms.packages.repository.PersonRepository;
import com.lms.packages.repository.RoleRepository;
import com.lms.packages.security.services.UserDetailsImpl;
import com.lms.packages.service.EmailSenderService;
import com.lms.packages.model.ConfirmationToken;

import com.lms.packages.repository.ConfirmationTokenRepository;
import com.lms.packages.payload.request.ConfirmPassword;
import com.lms.packages.payload.request.ForgotEmailRequest;
import com.lms.packages.payload.request.ForgotToken;
import com.lms.packages.payload.request.LoginRequest;
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
	private ConfirmationTokenRepository confirmationTokenRepository;
	
	@Autowired
	private EmailSenderService emailSenderService;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
			

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		
		
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
		if (personRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken!"));
		}

		if (personRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already in use!"));
		}

		// Create new user's account
		Person user = new Person(signUpRequest.getUsername() ,signUpRequest.getEmail(),
							 encoder.encode(signUpRequest.getPassword()));

		String strRole = signUpRequest.getRole();
		Role role;

		if (strRole == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			role = userRole;
		} else {
			
				switch (strRole) {
				case "admin":
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
		
		System.out.println(role.getName());
		System.out.println(user.getPassword().length());
		user.setRole(role);
		personRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}
	
	
	@RequestMapping(value="/forgot-password", method=RequestMethod.POST)
	public ResponseEntity<?> forgotUserPassword(@RequestBody ForgotEmailRequest request) {
		//Person existingPerson = personRepository.findByEmailIgnoreCase(person.getEmail());
		//if(existingPerson != null) {
			// create token
			//ConfirmationToken confirmationToken = new ConfirmationToken(existingPerson);
			
			// save it
			//confirmationTokenRepository.save(confirmationToken);
			
			// create the email
			
			SimpleMailMessage mailMessage = new SimpleMailMessage();
			mailMessage.setTo(request.getEmail());
			mailMessage.setSubject("Complete Password Reset!");
			
			//mailMessage.setText("To complete the password reset process, please click here: "
			//+"http://localhost:8080/confirm-reset?token="+confirmationToken.getConfirmationToken());
			mailMessage.setText("To complete the password reset process, please Enter the Otp ");
			emailSenderService.sendEmail(mailMessage);

			

		/*} else {	
			modelAndView.addObject("message", "This email does not exist!");
			modelAndView.setViewName("error");
		}
		
		return modelAndView;*/
			return ResponseEntity.ok(new MessageResponse("Email sent to user successfully!"));
	}


	@RequestMapping(value="/confirm-reset", method= RequestMethod.POST)
	public ModelAndView validateResetToken( @RequestBody ForgotToken token)
	{
		ConfirmationToken token1 = confirmationTokenRepository.findByConfirmationToken(token.getToken());
		
		if(token1 != null) {
			Person person = personRepository.findByEmailIgnoreCase(token1.getPerson().getEmail());
			//person.setEnabled(true);
			personRepository.save(person);
			
		} else {
			
		}
		
		return new ModelAndView("redirect:http://localhost:3000/forgot");
	}	

	
	@RequestMapping(value = "/reset-password", method = RequestMethod.POST)
	public ModelAndView resetUserPassword(@RequestBody ConfirmPassword password) {
		// ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);
		 
		/*if(person.getEmail() != null) {
			// use email to find user
			Person tokenUser = personRepository.findByEmailIgnoreCase(person.getEmail());
			//tokenUser.setEnabled(true);
			tokenUser.setPassword(encoder.encode(person.getPassword()));
			// System.out.println(tokenUser.getPassword());
			personRepository.save(tokenUser);
			modelAndView.addObject("message", "Password successfully reset. You can now log in with the new credentials.");
			modelAndView.setViewName("successResetPassword");
		} else {
			modelAndView.addObject("message","The link is invalid or broken!");
			modelAndView.setViewName("error");
		}
		*/
		
		return new ModelAndView();
	}
	
	
	public PersonRepository getPersonRepository() {
		return personRepository;
	}


	public void setPersonRepository(PersonRepository personRepository) {
		this.personRepository = personRepository;
	}


	public ConfirmationTokenRepository getConfirmationTokenRepository() {
		return confirmationTokenRepository;
	}


	public void setConfirmationTokenRepository(ConfirmationTokenRepository confirmationTokenRepository) {
		this.confirmationTokenRepository = confirmationTokenRepository;
	}


	public EmailSenderService getEmailSenderService() {
		return emailSenderService;
	}


	public void setEmailSenderService(EmailSenderService emailSenderService) {
		this.emailSenderService = emailSenderService;
	}
	
}
