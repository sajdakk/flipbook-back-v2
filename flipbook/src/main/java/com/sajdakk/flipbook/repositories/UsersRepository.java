package com.sajdakk.flipbook.repositories;

import com.sajdakk.flipbook.entities.UserEntity;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UsersRepository extends JpaRepository<UserEntity, Integer> {
    UserEntity findByEmail(String email);

    @Query("SELECT remove_user_and_reviews(:user_id)")
    void removeUserById(
            @Param("user_id") Integer userId
    );


}
