package com.sajdakk.flipbook.models;

import com.sajdakk.flipbook.dtos.ReviewDto;
import com.sajdakk.flipbook.entities.*;
import com.sajdakk.flipbook.repositories.BooksRepository;
import com.sajdakk.flipbook.repositories.ReviewsRepository;
import com.sajdakk.flipbook.repositories.UsersRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Component
public class ReviewsModel {

    ReviewsRepository reviewsRepository;
    UsersRepository userRepository;
    BooksRepository bookRepository;

    public ReviewsModel(ReviewsRepository reviewsRepository, UsersRepository userRepository, BooksRepository bookRepository) {
        this.reviewsRepository = reviewsRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    public List<ReviewEntity> getForAdmin() {
        Collection<ReviewEntity> result = reviewsRepository.findForAdmin();
        return new ArrayList<ReviewEntity>(result);

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

    public void addReview(ReviewDto dto, int userId) {
        // Fetch the UserEntity from the database
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with id " + userId + " not found"));

        // Fetch the BookEntity from the database
        BookEntity book = bookRepository.findById(dto.getBookId())
                .orElseThrow(() -> new IllegalArgumentException("Book with id " + dto.getBookId() + " not found"));

        ReviewEntity review = ReviewEntity.builder()
                .user(user)
                .book(book)
                .rate(dto.getRate())
                .content(dto.getContent())
                .uploadDate(new Timestamp(new Date().getTime()))
                .build();


        reviewsRepository.save(review);
    }

}
