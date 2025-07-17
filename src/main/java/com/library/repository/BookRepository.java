package com.library.repository;

import com.library.dto.response.BookResponseDto;
import com.library.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT b FROM Book b WHERE b.deleted = false")
    List<Book> findAllNotDeleted();

    @Query("SELECT b FROM Book b WHERE b.deleted = true")
    List<Book> findAllDeleted();

    @Query("SELECT b FROM Book b WHERE b.availableQuantity > 0")
    List<Book> findAllAvailable();

    @Query("SELECT b FROM Book b WHERE b.availableQuantity <= 0")
    List<Book> findAllUnavailable();

    @Query("SELECT b FROM Book b WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%'))")
    List<Book> findByTitle(@Param("title") String title);

    @Query("SELECT b FROM Book b WHERE LOWER(b.author) LIKE LOWER(CONCAT('%', :author, '%'))")
    List<Book> findByAuthor(@Param("author") String author);
}
