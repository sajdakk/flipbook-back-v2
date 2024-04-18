package com.sajdakk.flipbook.controllers;

import com.sajdakk.flipbook.dtos.AddBookDto;
import com.sajdakk.flipbook.dtos.SearchDto;
import com.sajdakk.flipbook.entities.BookEntity;
import com.sajdakk.flipbook.models.BooksModel;
import com.sajdakk.flipbook.views.BookView;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RestController("/books")
public class BooksController {
    BooksModel booksModel;

    @Autowired
    public BooksController(BooksModel booksModel) {
        this.booksModel = booksModel;
    }

    @GetMapping("/books")
    public List<BookView> getAll() {
        return BookView.fromEntities(booksModel.getAllBooks());
    }

    @PostMapping("/books/search")
    public List<BookView> search(@RequestBody(required = false) SearchDto searchDto) {
        List<BookEntity> bookEntities = booksModel.search(searchDto);

        return BookView.fromEntities(bookEntities);
    }

    @GetMapping("/books/top")
    public List<BookView> getTop(@RequestParam(required = false, name = "limit") Integer limit) {
        List<BookEntity> bookEntities = booksModel.getAllBooks();

        if (limit == null) {
            return BookView.fromEntities(bookEntities);
        }

        if (limit < 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Limit must be greater than 0");
        }

        return BookView.fromEntities(bookEntities.subList(0, Math.min(limit, bookEntities.size())));
    }


    @GetMapping("/books/favorites")
    public List<BookView> getFavorites(HttpSession session) {
        Object currentUserId = session.getAttribute("user_id");
        if (currentUserId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You need to be logged in to access this resource");
        }

        List<BookEntity> bookEntities = booksModel.getFavorites((int) currentUserId);

        return BookView.fromEntities(bookEntities);
    }

    @GetMapping("/books/admin")
    public List<BookView> getAdminBooks(HttpSession session) {
        Object role = session.getAttribute("role");
        if (role == null || !role.equals(3)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You need to be admin to access this resource");
        }


        List<BookEntity> bookEntities = booksModel.getForAdmin();

        return BookView.fromEntities(bookEntities);
    }


    @PostMapping("/books/add")
    public Integer add(@RequestBody AddBookDto addBookDto, HttpSession session) {
        Object currentUserId = session.getAttribute("user_id");
        if (currentUserId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You need to be logged in to access this resource");
        }

        return booksModel.addBook(addBookDto);
    }

    @GetMapping("/books/{id}")
    public BookView get(@PathVariable("id") int id) {
        return BookView.fromEntity(booksModel.getBookById(id));
    }

    @PostMapping("/books/{id}/accept")
    public void acceptBook(@PathVariable("id") int id, HttpSession session) {
        Object role = session.getAttribute("role");
        if (role == null || !role.equals(3)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You need to be admin to access this resource");
        }

        booksModel.acceptBook(id);
    }

    @PostMapping("/books/{id}/reject")
    public void rejectBook(@PathVariable("id") int id, HttpSession session) {
        Object role = session.getAttribute("role");
        if (role == null || !role.equals(3)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You need to be admin to access this resource");
        }

        booksModel.rejectBook(id);
    }
}
