package com.lms.packages.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.lms.packages.model.Issue;
import com.lms.packages.model.Person;
import com.lms.packages.repository.IssueRepository;
import com.lms.packages.repository.PersonRepository;

@Service
public class ComputeFine {
	
	@Autowired
	IssueRepository issueRepository;
	
	@Autowired
	PersonRepository personRepository;
	
	
	@Scheduled(cron="0 0 0 * * *")
	public void addfines() {
		List<Issue> issue_list = issueRepository.findAll();
		int[] fines = {10, 20, 30, 40,50,60};
		
		System.out.println("Scheduler running .........");
		for (Issue issues : issue_list) {
			System.out.println("Computing fines .........");
			Person user = issues.getUser();
			Date returnDate= issues.getReturnDate();
			Calendar c= Calendar.getInstance();
			Date currentDate = c.getTime();
			Date issueDate= issues.getIssueDate();
			Instant currentDate_=currentDate.toInstant();
			Instant returnDate_=returnDate.toInstant();
			Instant issueDate_=issueDate.toInstant();
			int num_weeks=0;
			if(currentDate.after(returnDate)) {
				num_weeks =(int)ChronoUnit.WEEKS.between(returnDate_, currentDate_);
				System.out.println("Minutes elapsed .... "+ num_weeks);
				if(num_weeks>=fines.length) {
					int user_fine =user.getFine();
					user.setFine(user_fine + fines[5]);
				}else {
					
					int user_fine =user.getFine();
					user.setFine(user_fine + fines[num_weeks]);
				}
			}
			personRepository.save(user);
			System.out.println("Fines added to the user  .........");
			System.out.println("Fine for user "+user.getEmail()+" is now "+user.getFine());
		}
		
	}

}
