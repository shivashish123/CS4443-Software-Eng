package com.lms.packages.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lms.packages.model.Staff;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {
	Optional<Staff> findByUsername(String userName);
	
	Optional<Staff> findByEmail(String email);

	Boolean existsByUsername(String userName);

	Boolean existsByEmail(String email);
}