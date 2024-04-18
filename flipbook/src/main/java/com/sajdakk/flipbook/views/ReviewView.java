package com.sajdakk.flipbook.views;

import com.sajdakk.flipbook.entities.ReviewEntity;
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
public class ReviewView {
    private Integer id;
    private UserView user;
    private BookView book;
    private String content;
    private Integer rate;
    private Timestamp uploadDate;
    private Timestamp acceptDate;
    private Timestamp rejectDate;

    public static List<ReviewView> fromEntities(List<ReviewEntity> entities) {
        return entities.stream().map(ReviewView::fromEntity).toList();
    }

    public static ReviewView fromEntity(ReviewEntity entity) {
        return ReviewView.builder()
                .id(entity.getId())
                .user(UserView.fromEntity(entity.getUser()))
                .book(BookView.fromEntity(entity.getBook()))
                .content(entity.getContent())
                .rate(entity.getRate())
                .uploadDate(entity.getUploadDate())
                .rejectDate(entity.getRejectDate())
                .acceptDate(entity.getAcceptDate())
                .build();
    }
}
