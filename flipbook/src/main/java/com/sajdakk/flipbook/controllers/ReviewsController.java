package com.sajdakk.flipbook.controllers;

import com.sajdakk.flipbook.dtos.ReviewDto;
import com.sajdakk.flipbook.models.ReviewsModel;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController("/reviews")
public class ReviewsController {
    ReviewsModel reviewsModel;

    @Autowired
    public ReviewsController(ReviewsModel reviewsModel) {
        this.reviewsModel = reviewsModel;
    }


    @PostMapping("/reviews/{id}/accept")
    public void acceptReview(@PathVariable("id") int id, HttpSession session) {
        Object role = session.getAttribute("role");
        if (role == null || !role.equals(3)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You need to be admin to access this resource");
        }

        reviewsModel.acceptReview(id);
    }

    @PostMapping("/reviews/{id}/reject")
    public void rejectReview(@PathVariable("id") int id, HttpSession session) {
        Object role = session.getAttribute("role");
        if (role == null || !role.equals(3)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You need to be admin to access this resource");
        }

        reviewsModel.rejectReview(id);
    }

    @GetMapping("/reviews/add")
    public void addReview(@RequestBody ReviewDto dto, HttpSession session) {
        Object role = session.getAttribute("role");
        if (role == null || !role.equals(2)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You need to be logged in to access this resource");
        }

        reviewsModel.addReview(dto);
    }


}
