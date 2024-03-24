package com.sajdakk.flipbook.repositories;

import com.sajdakk.flipbook.entities.BookEntity;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface BooksRepository extends JpaRepository<BookEntity, Integer> {
    @Query("SELECT b FROM BookEntity b WHERE b.title LIKE %:title%")
    Collection<BookEntity> findByTitle(@Param("title") String title);
}
