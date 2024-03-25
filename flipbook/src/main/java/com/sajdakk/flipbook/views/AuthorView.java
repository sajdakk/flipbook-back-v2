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
public class AuthorView {
    private Integer id;
    private String name;
    private String surname;

    static public List<AuthorView> fromEntities(List<com.sajdakk.flipbook.entities.AuthorEntity> entities) {
        return entities.stream()
                .map(AuthorView::fromEntity)
                .collect(Collectors.toList());
    }

    static public AuthorView fromEntity(com.sajdakk.flipbook.entities.AuthorEntity entity) {
        return AuthorView.builder()
                .id(entity.getId())
                .name(entity.getName())
                .surname(entity.getSurname())
                .build();
    }


}
