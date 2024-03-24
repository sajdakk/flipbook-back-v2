package com.sajdakk.flipbook.repositories;

import com.sajdakk.flipbook.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<UserEntity, Integer> {
    UserEntity findByEmail(String email);
}
