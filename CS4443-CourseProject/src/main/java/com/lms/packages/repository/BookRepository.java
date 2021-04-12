package com.lms.packages.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lms.packages.model.Author;
import com.lms.packages.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
	
	Optional<Book> findByTitle(String title);
	
    @Query("select count(id) from Book b where b.genre= ?1 and b.subGenre= ?2")
	int countByGenreSubgenre(String genre , String subgenre);    
    
    @Modifying
    @Query("delete from Book b where b.book_id= ?1")
	@Transactional
    int deleteById(String id);

}
