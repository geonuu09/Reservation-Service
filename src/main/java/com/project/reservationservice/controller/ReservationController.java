package com.project.reservationservice.controller;

import com.project.reservationservice.domain.Reservation;
import com.project.reservationservice.dto.ReservationDTO;
import com.project.reservationservice.service.ReservationService;

import lombok.RequiredArgsConstructor;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    /**
     * 예약 생성 API
     * @param reservationDTO 예약을 생성하는 데 필요한 정보
     * @return 생성된 예약 정보와 HTTP 상태 코드 201 (CREATED)
     */
    @PostMapping
    public ResponseEntity<ReservationDTO> createReservation(@RequestBody ReservationDTO reservationDTO) {
        ReservationDTO createdReservation = reservationService.createReservation(reservationDTO);
        return new ResponseEntity<>(createdReservation, HttpStatus.CREATED);
    }

    /**
     * 예약 조회 API
     * @param id 조회할 예약의 ID
     * @return 조회된 예약 정보와 HTTP 상태 코드 200 (OK)
     */
    @GetMapping("/{id}")
    public ResponseEntity<ReservationDTO> getReservation(@PathVariable Long id) {
        ReservationDTO reservation = reservationService.getReservation(id);
        return ResponseEntity.ok(reservation);
    }

    /**
     * 예약 상태 업데이트 API
     * @param id 업데이트할 예약의 ID
     * @param status 업데이트할 예약 상태
     * @return 업데이트된 예약 정보와 HTTP 상태 코드 200 (OK)
     */
    @PutMapping("/{id}/status")
    public ResponseEntity<ReservationDTO> updateReservationStatus(
            @PathVariable Long id,
            @RequestParam Reservation.ReservationStatus status) {
        ReservationDTO updatedReservation = reservationService.updateReservationStatus(id, status);
        return ResponseEntity.ok(updatedReservation);
    }

    /**
     * 특정 매장의 특정 시간대 예약 목록 조회 API
     * @param storeId 조회할 매장의 ID
     * @param start 조회할 시간대의 시작 시간
     * @param end 조회할 시간대의 종료 시간
     * @return 조회된 예약 목록과 HTTP 상태 코드 200 (OK)
     */
    @GetMapping("/store/{storeId}")
    public ResponseEntity<List<ReservationDTO>> getReservationsForStore(
            @PathVariable Long storeId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        List<ReservationDTO> reservations = reservationService.getReservationsForStore(storeId, start, end);
        return ResponseEntity.ok(reservations);
    }
}