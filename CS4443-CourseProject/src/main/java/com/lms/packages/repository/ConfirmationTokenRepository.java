package com.lms.packages.repository;

import org.springframework.data.repository.CrudRepository;

import com.lms.packages.model.ConfirmationToken;

public interface ConfirmationTokenRepository extends CrudRepository<ConfirmationToken, String> {
	ConfirmationToken findByConfirmationToken(String confirmationToken);
}
