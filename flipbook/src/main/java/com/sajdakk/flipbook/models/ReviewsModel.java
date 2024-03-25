package com.sajdakk.flipbook.models;

import com.sajdakk.flipbook.dtos.ReviewDto;
import com.sajdakk.flipbook.entities.*;
import com.sajdakk.flipbook.repositories.ReviewsRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Component
public class ReviewsModel {

    ReviewsRepository reviewsRepository;

    public ReviewsModel(ReviewsRepository reviewsRepository) {
        this.reviewsRepository = reviewsRepository;
    }

    public void acceptReview(Integer id) {
        ReviewEntity book = reviewsRepository.findById(id).orElse(null);
        if (book == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Review not found");
        }

        Timestamp now = new Timestamp(new Date().getTime());
        book.setAcceptDate(now);
        reviewsRepository.save(book);
    }

    public void rejectReview(Integer id) {
        ReviewEntity book = reviewsRepository.findById(id).orElse(null);
        if (book == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Review not found");
        }

        Timestamp now = new Timestamp(new Date().getTime());
        book.setRejectDate(now);
        reviewsRepository.save(book);
    }

    public ReviewEntity addReview(ReviewDto dto) {
        ReviewEntity review = ReviewEntity.builder()
                .user(UserEntity.builder().id(dto.getUserId()).build())
                .book(BookEntity.builder().id(dto.getBookId()).build())
                .rate(dto.getRate())
                .content(dto.getContent())
                .uploadDate(new Timestamp(new Date().getTime()))
                .build();

        return reviewsRepository.save(review);
    }

}
