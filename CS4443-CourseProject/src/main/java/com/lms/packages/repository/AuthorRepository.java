package com.lms.packages.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lms.packages.model.Author;
import com.lms.packages.model.Book;
import com.lms.packages.model.Person;
import com.lms.packages.model.Publisher;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
	Optional<Author> findByAuthorName(String authorName);
	
	Boolean existsByAuthorName(String authorName);

	Person findByAuthorNameIgnoreCase(String authorName);
	
	List<Author> findAll();
	
	@Query("select a from Author a where a.authorName like %?1% ") 
	List<Author> findByAuthorKeyword(String keyword);
	
	
}
