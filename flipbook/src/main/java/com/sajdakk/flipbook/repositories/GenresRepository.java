package com.sajdakk.flipbook.repositories;

import com.sajdakk.flipbook.entities.AuthorEntity;
import com.sajdakk.flipbook.entities.GenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenresRepository extends JpaRepository<GenreEntity, Integer> {

}
