package com.lms.packages.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lms.packages.model.Book;
import com.lms.packages.model.Issue;

public interface IssueRepository extends JpaRepository<Issue,Long>{

	
	@Query("select i from Issue i where i.user.email = ?1 and i.taken = 0 ")
	List<Issue> getIssuePerson(String email);
	
	@Query("select i from Issue i where i.book = ?1 and i.taken = 1 ")
	List<Issue> getIssueBook(Book book);
	
	

}
