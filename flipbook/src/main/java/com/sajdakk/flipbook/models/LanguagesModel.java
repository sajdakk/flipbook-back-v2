package com.sajdakk.flipbook.models;

import com.sajdakk.flipbook.entities.LanguageEntity;
import com.sajdakk.flipbook.repositories.LanguagesRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LanguagesModel {

    LanguagesRepository languagesRepository;

    public LanguagesModel(LanguagesRepository languagesRepository) {
        this.languagesRepository = languagesRepository;
    }

    public List<LanguageEntity> getAll() {
        return languagesRepository.findAll();
    }
}
