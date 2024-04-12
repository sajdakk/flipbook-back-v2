package com.sajdakk.flipbook.views;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sajdakk.flipbook.entities.*;
import jakarta.persistence.*;
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
public class BookView {
    private Integer id;
    private String title;
    private Timestamp dateOfPublication;
    private Integer pageCount;
    private String image;
    private String isbnNumber;
    private String description;
    private Timestamp uploadDate;
    private Timestamp acceptDate;
    private UserView createdBy;
    private Timestamp rejectDate;
    private GenreView genre;
    private LanguageView language;
    private List<AuthorView> authors;
    private List<ReviewForBookView> reviews;

    public static List<BookView> fromEntities(List<BookEntity> entities) {
        return entities.stream().map(BookView::fromEntity).toList();
    }

    public static BookView fromEntity(BookEntity entity) {
        List<AuthorEntity> authorEntities = entity.getAuthorBooks().stream().map(AuthorBookEntity::getAuthor).toList();
        return BookView.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .dateOfPublication(entity.getDateOfPublication())
                .pageCount(entity.getPageCount())
                .image(entity.getImage())
                .isbnNumber(entity.getIsbnNumber())
                .description(entity.getDescription())
                .uploadDate(entity.getUploadDate())
                .acceptDate(entity.getAcceptDate())
                .createdBy(UserView.fromEntity(entity.getCreatedBy()))
                .rejectDate(entity.getRejectDate())
                .genre(GenreView.fromEntity(entity.getGenre()))
                .language(LanguageView.fromEntity(entity.getLanguage()))
                .authors(AuthorView.fromEntities(authorEntities)
                ).reviews(ReviewForBookView.fromEntities(entity.getReviews())
                ).build();
    }
}


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Jacksonized
class ReviewForBookView {
    private Integer id;
    private UserForBookView user;
    private String content;
    private Integer rate;
    private Timestamp uploadDate;
    private Timestamp acceptDate;
    private Timestamp rejectDate;

    public static List<ReviewForBookView> fromEntities(List<ReviewEntity> entities) {
        return entities.stream().map(ReviewForBookView::fromEntity).toList();
    }

    public static ReviewForBookView fromEntity(ReviewEntity entity) {
        return ReviewForBookView.builder().id(entity.getId()).user(UserForBookView.fromEntity(entity.getUser())).content(entity.getContent()).rate(entity.getRate()).uploadDate(entity.getUploadDate()).rejectDate(entity.getRejectDate()).acceptDate(entity.getAcceptDate()).build();
    }
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Jacksonized
class UserForBookView {
    private Integer id;
    private String email;
    private String name;
    private String surname;
    private String avatar;


    public static UserForBookView fromEntity(UserEntity entity) {
        return UserForBookView.builder().id(entity.getId()).email(entity.getEmail()).name(entity.getName()).surname(entity.getSurname()).avatar(entity.getAvatar()).build();
    }
}
