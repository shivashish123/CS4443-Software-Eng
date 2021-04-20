package com.lms.packages.repository;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

import com.lms.packages.model.ConfirmationToken;
import com.lms.packages.model.IssueOTP;

public interface IssueOTPRepository extends CrudRepository<IssueOTP,String>{
	Optional<IssueOTP> findBytokenid(long tokenid);
}
