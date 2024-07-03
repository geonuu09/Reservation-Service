package com.project.reservationservice.dto;

import com.project.reservationservice.domain.Reservation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
public class ReservationDTO {
    private Long id;
    private Long storeId;
    private Long memberId;
    private LocalDate reservationDate;
    private LocalTime reservationTime;
    private Reservation.ReservationStatus status;

}