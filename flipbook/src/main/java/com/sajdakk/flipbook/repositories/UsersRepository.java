package com.sajdakk.flipbook.repositories;

import com.sajdakk.flipbook.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UsersRepository extends JpaRepository<UserEntity, Integer> {
    UserEntity findByEmail(String email);

    @Query("SELECT remove_user_and_reviews(:userId)")
    void removeUserById(
            @Param("userId") Integer userId
    );


}
