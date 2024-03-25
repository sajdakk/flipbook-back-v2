package com.sajdakk.flipbook.views;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import java.util.List;
import java.util.stream.Collectors;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Jacksonized
public class GenreView {
    private Integer id;
    private String title;

    static public List<GenreView> fromEntities(List<com.sajdakk.flipbook.entities.GenreEntity> entities) {
        return entities.stream()
                .map(GenreView::fromEntity)
                .collect(Collectors.toList());
    }

    static public GenreView fromEntity(com.sajdakk.flipbook.entities.GenreEntity entity) {
        return GenreView.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .build();
    }


}
