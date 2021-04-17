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
import com.lms.packages.model.Staff;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
	
	Optional<Book> findByTitle(String title);
	
    @Query("select count(id) from Book b where b.genre= ?1 and b.subGenre= ?2")
	int countByGenreSubgenre(String genre , String subgenre);    
    
    @Modifying
    @Query("delete from Book b where b.bookId= ?1")
	@Transactional
    int deleteById(String id);

	Optional<Book> findBybookId(String id);
	
	@Modifying
	@Query("update Book b set b.totalCopies = ?2 + b.totalCopies where b.bookId= ?1")
	@Transactional
	void updateCopies(String id, int count);
	
	//@Query("select b from Book b ")
	List<Book> findAll();

	//@Query("select b from Book b where title like '%?1%' order by case ?2 when 1 then b.rating when 2 then b.popularity when 3 then b.title end")
	@Query("select b from Book b where b.title like %?1%  order by case ?2 when 1 then b.rating when 2 then b.popularity when 3 then b.title end ") 
	List<Book> findByBookKeyword(String keyword, int sortBy);

}
