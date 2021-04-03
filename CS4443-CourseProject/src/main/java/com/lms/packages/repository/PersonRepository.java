package com.lms.packages.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lms.packages.model.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
	Optional<Person> findByUsername(String userName);

	Boolean existsByUsername(String userName);

	Boolean existsByEmail(String email);
}
