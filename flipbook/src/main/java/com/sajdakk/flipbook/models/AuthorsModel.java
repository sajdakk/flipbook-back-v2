package com.sajdakk.flipbook.models;

import com.sajdakk.flipbook.entities.AuthorEntity;
import com.sajdakk.flipbook.repositories.AuthorsRepository;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class AuthorsModel {
    AuthorsRepository authorsRepository;

    public AuthorsModel(AuthorsRepository authorsRepository) {
        this.authorsRepository = authorsRepository;
    }

    public List<AuthorEntity> getAll() {
        return authorsRepository.findAll();
    }
}
