package com.lms.packages.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.lms.packages.model.Issue;

public interface IssueRepository extends JpaRepository<Issue,Long>{

}
