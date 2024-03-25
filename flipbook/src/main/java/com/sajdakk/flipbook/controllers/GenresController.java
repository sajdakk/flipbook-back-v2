package com.sajdakk.flipbook.controllers;

import com.sajdakk.flipbook.models.GenresModel;
import com.sajdakk.flipbook.views.GenreView;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController("/genres")
public class GenresController {
    GenresModel genresModel;

    @Autowired
    public GenresController(GenresModel genresModel) {
        this.genresModel = genresModel;
    }

    @GetMapping("/genres")
    public List<GenreView> getAll(HttpSession session) {
        Object role = session.getAttribute("role");
        if (role == null || !role.equals(2)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You need to be logged in to access this resource");
        }
        
        return GenreView.fromEntities(genresModel.getAll());
    }
}
