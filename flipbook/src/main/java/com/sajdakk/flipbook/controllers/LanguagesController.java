package com.sajdakk.flipbook.controllers;

import com.sajdakk.flipbook.models.LanguagesModel;
import com.sajdakk.flipbook.views.LanguageView;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController("/languages")
public class LanguagesController {
    LanguagesModel languagesModel;

    @Autowired
    public LanguagesController(LanguagesModel languagesModel) {
        this.languagesModel = languagesModel;
    }

    @GetMapping("/languages")
    public List<LanguageView> getAll(HttpSession session) {
        Object currentUserId = session.getAttribute("user_id");
        if (currentUserId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You need to be logged in to access this resource");
        }

        return LanguageView.fromEntities(languagesModel.getAll());
    }
}
