package com.sajdakk.flipbook.controllers;

import com.sajdakk.flipbook.models.GenresModel;
import com.sajdakk.flipbook.utils.JwtUtil;
import com.sajdakk.flipbook.views.GenreView;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController("/genres")
public class GenresController {
    private final GenresModel genresModel;
    private final JwtUtil jwtUtil;


    @Autowired
    public GenresController(GenresModel genresModel, JwtUtil jwtUtil) {
        this.genresModel = genresModel;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/genres")
    public List<GenreView> getAll(HttpServletRequest request) {
        Claims claims = jwtUtil.resolveClaims(request);
        if (claims == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You need to be logged in to access this resource");
        }

        return GenreView.fromEntities(genresModel.getAll());
    }
}
