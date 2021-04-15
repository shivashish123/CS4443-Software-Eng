package com.lms.packages.repository;

import java.util.*;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import com.lms.packages.model.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
	Optional<Person> findByUsername(String userName);
	
	Optional<Person> findByEmail(String email);

	Boolean existsByUsername(String userName);

	Boolean existsByEmail(String email);
	Person findByEmailIgnoreCase(String email);
	
	@Query("select p from Person p where p.role.id = 1")
	List<Person> getAllUsers();
	
	@Query("select s from Person s where s.email = ?1")
	Person getUser(String email);
}
