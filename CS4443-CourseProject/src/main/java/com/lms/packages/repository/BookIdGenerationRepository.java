package com.lms.packages.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lms.packages.model.Book;
import com.lms.packages.model.BookIdGeneration;

@Repository
public interface BookIdGenerationRepository extends JpaRepository<BookIdGeneration, Long> {
	
	@Query("select count(id) from BookIdGeneration b where b.genre= ?1 and b.subGenre= ?2")
	int countByGenreSubgenre(String genre , String subgenre);   
	
}

