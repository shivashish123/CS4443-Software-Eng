package com.lms.packages.repository;

import java.util.List;
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
	
	@Query("select p from person p, role_id r where p.id== r.id and p.role_id = 1")
	List<Person> getAllUsers();
}
