package com.sajdakk.flipbook.views;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sajdakk.flipbook.entities.BookEntity;
import com.sajdakk.flipbook.entities.ReviewEntity;
import com.sajdakk.flipbook.entities.RoleEntity;
import com.sajdakk.flipbook.entities.UserEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class ProfileView {
    private Integer id;
    private List<BookView> books;
    private List<ReviewForProfileView> reviews;

    public static List<ProfileView> fromEntities(List<UserEntity> entities) {
        return entities.stream().map(ProfileView::fromEntity).toList();
    }

    public static ProfileView fromEntity(UserEntity entity) {
        return ProfileView.builder()
                .id(entity.getId())
                .books(BookView.fromEntities(entity.getBooks()))
                .reviews(ReviewForProfileView.fromEntities(entity.getReviews()))
                .build();


    }
}


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Jacksonized
class ReviewForProfileView {
    private Integer id;

    private BookView book;

    private String content;

    private Integer rate;

    private Timestamp uploadDate;

    private Timestamp acceptDate;

    private Timestamp rejectDate;

    public static List<ReviewForProfileView> fromEntities(List<ReviewEntity> entities) {
        return entities.stream().map(ReviewForProfileView::fromEntity).toList();
    }

    public static ReviewForProfileView fromEntity(ReviewEntity entity) {
        return ReviewForProfileView.builder()
                .id(entity.getId())
                .book(BookView.fromEntity(entity.getBook()))
                .content(entity.getContent())
                .rate(entity.getRate())
                .uploadDate(entity.getUploadDate())
                .acceptDate(entity.getAcceptDate())
                .rejectDate(entity.getRejectDate())
                .build();
    }
}
