package com.sajdakk.flipbook.repositories;

import com.sajdakk.flipbook.entities.BookEntity;
import com.sajdakk.flipbook.entities.FavoriteEntity;
import com.sajdakk.flipbook.entities.LanguageEntity;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Transactional
public interface FavoritesRepository extends JpaRepository<FavoriteEntity, Integer> {


    @Query("SELECT f FROM FavoriteEntity f WHERE f.user.id = :userId AND f.book.id = :bookId")
    Collection<FavoriteEntity> getFavoritesByUserIdAndBookId(@Param("userId") int userId, @Param("bookId") int bookId);


}
