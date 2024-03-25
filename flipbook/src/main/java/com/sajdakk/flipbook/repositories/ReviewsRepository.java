package com.sajdakk.flipbook.repositories;

import com.sajdakk.flipbook.entities.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewsRepository extends JpaRepository<ReviewEntity, Integer> {


}
