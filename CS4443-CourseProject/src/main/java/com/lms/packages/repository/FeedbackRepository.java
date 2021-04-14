package com.lms.packages.repository;

import org.springframework.data.repository.CrudRepository;

import com.lms.packages.model.Feedback;

public interface FeedbackRepository extends CrudRepository<Feedback, String>{

}
