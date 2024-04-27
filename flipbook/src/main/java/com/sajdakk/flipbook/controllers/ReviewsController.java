package com.sajdakk.flipbook.controllers;

import com.sajdakk.flipbook.dtos.ReviewDto;
import com.sajdakk.flipbook.entities.BookEntity;
import com.sajdakk.flipbook.entities.ReviewEntity;
import com.sajdakk.flipbook.models.ReviewsModel;
import com.sajdakk.flipbook.utils.JwtUtil;
import com.sajdakk.flipbook.views.BookView;
import com.sajdakk.flipbook.views.ReviewView;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController("/reviews")
public class ReviewsController {
    private final ReviewsModel reviewsModel;
    private final JwtUtil jwtUtil;


    @Autowired
    public ReviewsController(ReviewsModel reviewsModel, JwtUtil jwtUtil) {
        this.reviewsModel = reviewsModel;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/reviews/admin")
    public List<ReviewView> getForAdmin(HttpServletRequest request) {
        Claims claims = jwtUtil.resolveClaims(request);
        if (claims == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You need to be logged in to access this resource");
        }

        Object role = claims.get("role");
        if (role == null || !role.equals(3)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You need to be admin to access this resource");
        }

        List<ReviewEntity> reviewEntities = reviewsModel.getForAdmin();
        return ReviewView.fromEntities(reviewEntities);
    }


    @PostMapping("/reviews/{id}/accept")
    public void acceptReview(HttpServletRequest request, @PathVariable("id") int id) {
        Claims claims = jwtUtil.resolveClaims(request);
        if (claims == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You need to be logged in to access this resource");
        }

        Object role = claims.get("role");
        if (role == null || !role.equals(3)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You need to be admin to access this resource");
        }

        reviewsModel.acceptReview(id);
    }

    @PostMapping("/reviews/{id}/reject")
    public void rejectReview(HttpServletRequest request, @PathVariable("id") int id) {
        Claims claims = jwtUtil.resolveClaims(request);
        if (claims == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You need to be logged in to access this resource");
        }

        Object role = claims.get("role");
        if (role == null || !role.equals(3)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You need to be admin to access this resource");
        }

        reviewsModel.rejectReview(id);
    }

    @PostMapping("/reviews/add")
    public void addReview(HttpServletRequest request, @RequestBody ReviewDto dto) {
        Claims claims = jwtUtil.resolveClaims(request);
        if (claims == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You need to be logged in to access this resource");
        }

        Object currentUserId = claims.get("user_id");
        reviewsModel.addReview(dto, (int) currentUserId);
    }


}
