package com.lms.packages.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lms.packages.model.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
	Optional<Person> findByUsername(String userName);
	
	Optional<Person> findByEmail(String email);

	Boolean existsByUsername(String userName);

	Boolean existsByEmail(String email);
	Person findByEmailIgnoreCase(String email);
	
	
//	@Query("select Person p from role_id  join role_id on role_id.id=person.id ")
//	List<Person> getAllUsers();
}
