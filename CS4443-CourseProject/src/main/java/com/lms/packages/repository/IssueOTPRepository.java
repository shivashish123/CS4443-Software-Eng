package com.lms.packages.repository;

import org.springframework.data.repository.CrudRepository;

import com.lms.packages.model.ConfirmationToken;
import com.lms.packages.model.IssueOTP;

public interface IssueOTPRepository extends CrudRepository<IssueOTP,String>{
	IssueOTP findByissuetoken(String issuetoken);
}
