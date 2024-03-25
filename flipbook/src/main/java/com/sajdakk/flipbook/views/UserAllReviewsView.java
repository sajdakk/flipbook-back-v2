package com.sajdakk.flipbook.views;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sajdakk.flipbook.entities.BookEntity;
import com.sajdakk.flipbook.entities.ReviewEntity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import java.sql.Timestamp;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Jacksonized
public class UserAllReviewsView {
    private Integer id;

    private BookForReviewView book;

    private String content;

    private Integer rate;

    private Timestamp uploadDate;

    private Timestamp acceptDate;

    private Timestamp rejectDate;

    public static UserAllReviewsView fromEntity(ReviewEntity entity) {
        return UserAllReviewsView.builder().id(entity.getId()).book(BookForReviewView.fromEntity(entity.getBook())).content(entity.getContent()).rate(entity.getRate()).uploadDate(entity.getUploadDate()).acceptDate(entity.getAcceptDate()).rejectDate(entity.getRejectDate()).build();
    }
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Jacksonized
class BookForReviewView {
    private Integer id;
    private String title;
    private Timestamp dateOfPublication;
    private Integer pageCount;
    private String image;
    private String isbnNumber;
    private String description;
    private Timestamp uploadDate;
    private Timestamp acceptDate;
    private Integer createdBy;
    private Timestamp rejectDate;

    public static BookForReviewView fromEntity(BookEntity entity) {
        return BookForReviewView.builder().id(entity.getId()).title(entity.getTitle()).dateOfPublication(entity.getDateOfPublication()).pageCount(entity.getPageCount()).image(entity.getImage()).isbnNumber(entity.getIsbnNumber()).description(entity.getDescription()).uploadDate(entity.getUploadDate()).acceptDate(entity.getAcceptDate()).createdBy(entity.getCreatedBy()).rejectDate(entity.getRejectDate()).build();
    }
}

