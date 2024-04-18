package com.sajdakk.flipbook.controllers;

import com.sajdakk.flipbook.dtos.AddBookDto;
import com.sajdakk.flipbook.dtos.SearchDto;
import com.sajdakk.flipbook.entities.AuthorEntity;
import com.sajdakk.flipbook.entities.BookEntity;
import com.sajdakk.flipbook.entities.ReviewEntity;
import com.sajdakk.flipbook.models.AuthorsModel;
import com.sajdakk.flipbook.repositories.AuthorsRepository;
import com.sajdakk.flipbook.repositories.BooksRepository;
import com.sajdakk.flipbook.views.AuthorView;
import com.sajdakk.flipbook.views.BookView;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.util.*;

@RestController("/authors")
public class AuthorsController {
    AuthorsModel authorsModel;

    @Autowired
    public AuthorsController(AuthorsModel authorsModel) {
        this.authorsModel = authorsModel;
    }

    @GetMapping("/authors")
    public List<AuthorView> getAll(HttpSession session) {
        Object currentUserId = session.getAttribute("user_id");
        if (currentUserId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You need to be logged in to access this resource");
        }

        return AuthorView.fromEntities(authorsModel.getAll());
    }
}
