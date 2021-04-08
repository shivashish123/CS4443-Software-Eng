package com.lms.packages.repository;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.lms.packages.model.Staff;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {
	Optional<Staff> findByUsername(String userName);
	
	Optional<Staff> findByEmail(String email);

	Boolean existsByUsername(String userName);

	Boolean existsByEmail(String email);
	
	@Modifying
    @Query("delete from Staff s where s.email = ?1")
	@Transactional
	int deleteByEmail(String email);
	
	List<Staff> findAll();
}
