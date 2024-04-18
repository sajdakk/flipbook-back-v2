package com.sajdakk.flipbook.controllers;

import com.sajdakk.flipbook.dtos.AddBookDto;
import com.sajdakk.flipbook.dtos.ToggleFavoritesDto;
import com.sajdakk.flipbook.models.FavoritesModel;
import com.sajdakk.flipbook.models.GenresModel;
import com.sajdakk.flipbook.views.GenreView;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController("/favorites")
public class FavoriteController {
    FavoritesModel favoritesModel;

    @Autowired
    public FavoriteController(FavoritesModel favoritesModel) {
        this.favoritesModel = favoritesModel;
    }

    @PostMapping("/favorites/toggle")
    public void toggleFavorite(@RequestBody ToggleFavoritesDto dto, HttpSession session) {
        Object currentUserId = session.getAttribute("user_id");
        if (currentUserId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You need to be logged in to access this resource");
        }

        favoritesModel.toggleFavorite((int) currentUserId, dto.getBookId());
    }
}
