package com.lms.packages.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lms.packages.model.Book;
import com.lms.packages.model.Ratings;

public interface RatingRepository extends JpaRepository<Ratings, String> {

}
