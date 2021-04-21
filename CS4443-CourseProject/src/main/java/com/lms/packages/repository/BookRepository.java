package com.lms.packages.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lms.packages.model.Author;
import com.lms.packages.model.Book;
import com.lms.packages.model.Publisher;
import com.lms.packages.model.Staff;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
	
	Optional<Book> findByTitle(String title);
	
    
    @Modifying
    @Query("delete from Book b where b.bookId= ?1")
	@Transactional
    int deleteById(String id);

	Optional<Book> findBybookId(String id);
	
	@Modifying
	@Query("update Book b set b.totalCopies = ?2 + b.totalCopies,b.currentCopies=?2 + b.currentCopies where b.bookId= ?1")
	@Transactional
	void updateCopies(String id, int count);
	
	@Modifying
	@Query("update Book b set b.currentCopies = b.currentCopies - 1 where b.bookId= ?1")
	@Transactional
	void decreaseCopies(String id);
	
	//@Query("select b from Book b ")
	List<Book> findAll();
	
	//@Query("select b from Book b where title like '%?1%' order by case ?2 when 1 then b.rating when 2 then b.popularity when 3 then b.title end")
	@Query("select b from Book b where b.title like %?1%  order by case ?2 when 1 then b.rating when 2 then b.popularity when 3 then b.title end ") 
	List<Book> findByBookKeywordByrating(String keyword, int sortBy);

	//@Query("select b from Book b where title like '%?1%' ")
	@Query("select b from Book b where b.title like %?1% ") 
	List<Book> findByBookKeyword(String keyword, int sortBy);
		
	@Query("select b from Book b , in(b.authors) y where y in ?1 ") 
	List<Book> findAllAuthorBooks(List<Author> authors);
	
	@Query("select b from Book b  where b.publisher in ?1 ") 
	List<Book> findAllPublisherBooks(List<Publisher> publishers);
	
	//SELECT x FROM Magazine x, IN(x.articles) y WHERE y.authorName = 'John Doe'

}
