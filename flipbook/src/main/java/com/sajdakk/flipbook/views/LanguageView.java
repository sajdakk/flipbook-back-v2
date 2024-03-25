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
public class LanguageView {
    private Integer id;
    private String language;

    static public List<LanguageView> fromEntities(List<com.sajdakk.flipbook.entities.LanguageEntity> entities) {
        return entities.stream()
                .map(LanguageView::fromEntity)
                .collect(Collectors.toList());
    }

    static public LanguageView fromEntity(com.sajdakk.flipbook.entities.LanguageEntity entity) {
        return LanguageView.builder()
                .id(entity.getId())
                .language(entity.getLanguage())
                .build();
    }


}
