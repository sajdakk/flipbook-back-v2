package com.sajdakk.flipbook.repositories;

import com.sajdakk.flipbook.entities.BookEntity;
import com.sajdakk.flipbook.entities.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Transactional
public interface ReviewsRepository extends JpaRepository<ReviewEntity, Integer> {

    @Query("SELECT r FROM ReviewEntity r WHERE r.rejectDate is null and r.acceptDate is null")
    Collection<ReviewEntity> findForAdmin();
}
