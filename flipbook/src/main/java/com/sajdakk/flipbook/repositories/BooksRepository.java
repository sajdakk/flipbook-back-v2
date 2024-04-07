package com.sajdakk.flipbook.repositories;

import com.sajdakk.flipbook.dtos.SearchDto;
import com.sajdakk.flipbook.entities.BookEntity;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
@Transactional
public interface BooksRepository extends JpaRepository<BookEntity, Integer> {


    @Query("SELECT DISTINCT b FROM BookEntity b " +
            "JOIN b.authorBooks ab " +
            "JOIN ab.author a " +
            "WHERE (COALESCE(:title, '') = '' OR LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%'))) " +
            "AND (COALESCE(:authorName, '') = '' OR (LOWER(a.name) LIKE LOWER(CONCAT('%', :authorName, '%')) OR LOWER(a.surname) LIKE LOWER(CONCAT('%', :authorName, '%')))) " +
            "AND (COALESCE(:authorSurname, '') = '' OR LOWER(a.surname) LIKE LOWER(CONCAT('%', :authorSurname, '%')))")
    Collection<BookEntity> findByTitleOrAuthorNameOrAuthorSurname(@Param("title") String title,
                                                                  @Param("authorName") String authorName,
                                                                  @Param("authorSurname") String authorSurname);


    @Query("SELECT add_book(:title, :genreId, :languageId, :dateOfPublication, :pageCount, :image, :isbn, :description, :uploadDate, :createdBy, :authorsString)")
    Integer addBook(@Param("title") String title,
                 @Param("genreId") Integer genreId,
                 @Param("languageId") Integer languageId,
                 @Param("dateOfPublication") String dateOfPublication,
                 @Param("pageCount") Integer pageCount,
                 @Param("image") String image,
                 @Param("isbn") String isbn,
                 @Param("description") String description,
                 @Param("uploadDate") String uploadDate,
                 @Param("createdBy") Integer createdBy,
                 @Param("authorsString") String authorsString
    );

}
