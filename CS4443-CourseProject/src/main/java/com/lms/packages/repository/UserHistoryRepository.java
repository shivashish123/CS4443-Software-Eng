package com.lms.packages.repository;

import java.util.*;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import com.lms.packages.model.UserHistory;

@Repository
public interface UserHistoryRepository extends JpaRepository<UserHistory, Long> {
	
	@Query("select s from UserHistory s where s.emailID = ?1")
	List<UserHistory> getHistory(String email);
	
}
