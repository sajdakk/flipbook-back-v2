package com.sajdakk.flipbook.controllers;

import com.sajdakk.flipbook.dtos.ToggleFavoritesDto;
import com.sajdakk.flipbook.models.FavoritesModel;
import com.sajdakk.flipbook.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController("/favorites")
public class FavoriteController {
    private final FavoritesModel favoritesModel;
    private final JwtUtil jwtUtil;

    @Autowired
    public FavoriteController(FavoritesModel favoritesModel, JwtUtil jwtUtil) {
        this.favoritesModel = favoritesModel;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/favorites/toggle")
    public void toggleFavorite(HttpServletRequest request, @RequestBody ToggleFavoritesDto dto) {
        Claims claims = jwtUtil.resolveClaims(request);
        if (claims == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You need to be logged in to access this resource");
        }

        Object currentUserId = claims.get("userId");
        favoritesModel.toggleFavorite((int) currentUserId, dto.getBookId());
    }
}
