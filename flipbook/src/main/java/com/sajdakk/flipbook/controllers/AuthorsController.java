package com.sajdakk.flipbook.controllers;

import com.sajdakk.flipbook.models.AuthorsModel;
import com.sajdakk.flipbook.utils.JwtUtil;
import com.sajdakk.flipbook.views.AuthorView;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController("/authors")
public class AuthorsController {
    private final AuthorsModel authorsModel;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthorsController(AuthorsModel authorsModel, JwtUtil jwtUtil) {
        this.authorsModel = authorsModel;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/authors")
    public List<AuthorView> getAll(HttpServletRequest request) {
        Claims claims = jwtUtil.resolveClaims(request);
        if (claims == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You need to be logged in to access this resource");
        }

        return AuthorView.fromEntities(authorsModel.getAll());
    }
}
