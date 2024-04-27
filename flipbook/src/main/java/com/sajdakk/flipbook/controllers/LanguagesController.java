package com.sajdakk.flipbook.controllers;

import com.sajdakk.flipbook.models.LanguagesModel;
import com.sajdakk.flipbook.utils.JwtUtil;
import com.sajdakk.flipbook.views.LanguageView;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController("/languages")
public class LanguagesController {
    private final LanguagesModel languagesModel;

    private final JwtUtil jwtUtil;

    @Autowired
    public LanguagesController(LanguagesModel languagesModel, JwtUtil jwtUtil) {
        this.languagesModel = languagesModel;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/languages")
    public List<LanguageView> getAll(HttpServletRequest request) {
        Claims claims = jwtUtil.resolveClaims(request);
        if (claims == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You need to be logged in to access this resource");
        }

        return LanguageView.fromEntities(languagesModel.getAll());
    }
}
