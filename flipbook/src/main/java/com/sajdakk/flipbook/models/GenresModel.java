package com.sajdakk.flipbook.models;

import com.sajdakk.flipbook.entities.GenreEntity;
import com.sajdakk.flipbook.repositories.GenresRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class GenresModel {

    GenresRepository genresRepository;

    public GenresModel(GenresRepository genresRepository) {
        this.genresRepository = genresRepository;
    }

    public List<GenreEntity> getAll() {
        return genresRepository.findAll();
    }
}
