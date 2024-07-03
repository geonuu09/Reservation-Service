package com.project.reservationservice.repository;

import com.project.reservationservice.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByStoreIdAndReservationDateBetween(Long storeId, LocalDateTime start, LocalDateTime end);
    Optional<Reservation> findByMemberId(Long memberId);
}
