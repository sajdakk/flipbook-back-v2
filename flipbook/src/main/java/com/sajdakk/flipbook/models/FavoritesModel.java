package com.sajdakk.flipbook.models;

import com.sajdakk.flipbook.entities.BookEntity;
import com.sajdakk.flipbook.entities.FavoriteEntity;
import com.sajdakk.flipbook.entities.UserEntity;
import com.sajdakk.flipbook.repositories.FavoritesRepository;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Date;

@Component
public class FavoritesModel {

    FavoritesRepository favoritesRepository;

    public FavoritesModel(FavoritesRepository favoritesRepository) {
        this.favoritesRepository = favoritesRepository;
    }

    public void toggleFavorite(Integer userId, Integer bookId) {
        FavoriteEntity favoriteEntity = favoritesRepository.getFavoritesByUserIdAndBookId(userId, bookId).stream().findFirst().orElse(null);
        if (favoriteEntity == null) {
            favoriteEntity = FavoriteEntity.builder()
                    .user(UserEntity.builder().id(userId).build())
                    .book(BookEntity.builder().id(bookId).build())
                    .build();


            favoritesRepository.save(favoriteEntity);
            return;
        }

        favoritesRepository.delete(favoriteEntity);

    }



}
