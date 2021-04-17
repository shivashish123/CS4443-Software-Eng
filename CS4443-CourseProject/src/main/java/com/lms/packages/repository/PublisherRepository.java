package com.lms.packages.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lms.packages.model.Author;
import com.lms.packages.model.Person;
import com.lms.packages.model.Publisher;
import com.lms.packages.model.Staff;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Long> {
	Optional<Publisher> findByPublisherName(String publisherName);
	
	Boolean existsByPublisherName(String publisherName);

	Person findByPublisherNameIgnoreCase(String publisherName);
	
	List<Publisher> findAll();
	
	@Query("select p from Publisher p where p.publisherName like %?1% ") 
	List<Publisher> findByPublisherKeyword(String keyword);

}
