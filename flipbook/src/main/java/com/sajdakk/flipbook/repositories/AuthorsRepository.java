package com.sajdakk.flipbook.repositories;

import com.sajdakk.flipbook.entities.AuthorEntity;
import com.sajdakk.flipbook.entities.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Transactional
public interface AuthorsRepository extends JpaRepository<AuthorEntity, Integer> {

}
