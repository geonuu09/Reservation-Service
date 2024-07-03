package com.project.reservationservice.dto;

import com.project.reservationservice.domain.Reservation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class ReservationDTO {
    private Long id;
    private Long storeId;
    private Long memberId;
    private LocalDate reservationDate;
    private Reservation.ReservationStatus status;

}