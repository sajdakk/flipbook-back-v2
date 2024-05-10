package com.sajdakk.flipbook.repositories;

import com.sajdakk.flipbook.dtos.SearchDto;
import com.sajdakk.flipbook.entities.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Date;
import java.util.List;

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


    @Query("SELECT f.book FROM FavoriteEntity f WHERE f.user.id = :userId")
    Collection<BookEntity> getFavorites(@Param("userId") int userId);

    @Query("SELECT b FROM BookEntity b WHERE b.rejectDate is null and b.acceptDate is null")
    Collection<BookEntity> findForAdmin();


    @Query("SELECT add_book(:title, :genreId, :languageId, :dateOfPublication, :pageCount, :image, :isbn, :description, :uploadDate, :createdBy, :authors)")
    Integer addBook(@Param("title") String title,
                    @Param("genreId") Integer genreId,
                    @Param("languageId") Integer languageId,
                    @Param("dateOfPublication") Date dateOfPublication,
                    @Param("pageCount") Integer pageCount,
                    @Param("image") String image,
                    @Param("isbn") String isbn,
                    @Param("description") String description,
                    @Param("uploadDate") Date uploadDate,
                    @Param("createdBy") Integer createdBy,
                    @Param("authors") Integer[] authors
    );

}
