package com.sajdakk.flipbook.repositories;

import com.sajdakk.flipbook.entities.GenreEntity;
import com.sajdakk.flipbook.entities.LanguageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LanguagesRepository extends JpaRepository<LanguageEntity, Integer> {

}
