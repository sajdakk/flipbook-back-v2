package com.sajdakk.flipbook.repositories;

import com.sajdakk.flipbook.entities.AuthorEntity;
import com.sajdakk.flipbook.entities.BookEntity;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface AuthorsRepository extends JpaRepository<AuthorEntity, Integer> {

}
