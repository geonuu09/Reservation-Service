package com.project.reservationservice.repository;

import com.project.reservationservice.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByReservationStoreId(Long storeId);
    Optional<Review> findByReservationIdAndMemberId(Long reservationId, Long memberId);
}
