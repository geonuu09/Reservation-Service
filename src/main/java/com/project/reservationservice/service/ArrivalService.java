package com.project.reservationservice.service;

import com.project.reservationservice.domain.Reservation;
import com.project.reservationservice.domain.Store;
import com.project.reservationservice.repository.ReservationRepository;
import com.project.reservationservice.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ArrivalService {
    private final ReservationRepository reservationRepository;
    private final StoreRepository storeRepository;

    @Transactional
    public boolean comfirmArrival(Long kioskId, Long reservationId) {

        Store store = storeRepository.findById(kioskId)
                .orElseThrow(() -> new RuntimeException("Invalid kiosk id"));

        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("reservation not found"));
        // 예약이 해당 매장의 것인지 확인
        if(!reservation.getStore().getId().equals(store.getId())) {
            throw new RuntimeException("reservation has wrong store");
        }

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime reservationTime =  reservation.getReservationDateTime();

        // 예약 시간 10분 전부터 도착 확인이 가능하도록 합니다.
        if (now.isBefore(reservationTime.minusMinutes(10))) {
            throw new RuntimeException("Too early for arrival confirmation");
        }

        // 예약 상태를 "도착"으로 변경합니다.
        reservation.setStatus(Reservation.ReservationStatus.ARRIVED);
        reservationRepository.save(reservation);

        return true;
    }
}
