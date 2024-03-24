package com.sajdakk.flipbook.controllers;

import com.sajdakk.flipbook.entities.BookEntity;
import com.sajdakk.flipbook.repositories.BooksRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;

@RestController("/books")
public class BooksController {
    BooksRepository booksRepository;

    @Autowired
    public BooksController(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    @GetMapping("/books")
    public Collection<BookEntity> getAll(HttpSession session) {
        Object role = session.getAttribute("role");
        if (role == null || !role.equals(2)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You need to be logged in to access this resource");
        }

        return booksRepository.findByTitle("Pot");
    }

    @GetMapping("/books/{id}")
    public BookEntity get(@PathVariable("id") int id) {
        return booksRepository.findById(id).orElse(null);
    }
}
