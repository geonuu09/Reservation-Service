package com.project.reservationservice.controller;

import com.project.reservationservice.domain.Reservation;
import com.project.reservationservice.DTO.ReservationDTO;
import com.project.reservationservice.service.ReservationService;

import lombok.RequiredArgsConstructor;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    /**
     * 예약 생성 API
     * POST /api/reservations
     * @param reservationDTO 생성할 예약 정보
     * @return 생성된 예약 정보와 HTTP 상태 코드 201 (CREATED)
     */
    @PostMapping
    public ResponseEntity<ReservationDTO> createReservation(@RequestBody ReservationDTO reservationDTO) {
        ReservationDTO createdReservation = reservationService.createReservation(reservationDTO);
        return new ResponseEntity<>(createdReservation, HttpStatus.CREATED);
    }

    /**
     * 예약 조회 API
     * GET /api/reservations/{id}
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
     * PUT /api/reservations/{id}/status?status={status}
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
     * 예약 취소 API
     * PUT /api/reservations/{id}/cancel
     * @param id 취소할 예약의 ID
     * @return 취소된 예약 정보와 HTTP 상태 코드 200 (OK) 또는 404 (Not Found)
     */
    @PutMapping("/{id}/cancel")
    public ResponseEntity<ReservationDTO> cancelReservation(@PathVariable Long id) {
        try {
            ReservationDTO cancelledReservation = reservationService.cancelReservation(id);
            return ResponseEntity.ok(cancelledReservation);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 특정 매장의 특정 날짜 예약 목록 조회 API
     * GET /api/reservations/store/{storeId}?date={date}
     * @param storeId 조회할 매장의 ID
     * @param date 조회할 날짜 (ISO DATE 형식: yyyy-MM-dd)
     * @return 조회된 예약 목록과 HTTP 상태 코드 200 (OK)
     */
    @GetMapping("/store/{storeId}")
    public ResponseEntity<List<ReservationDTO>> getReservationsForStore(
            @PathVariable Long storeId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<ReservationDTO> reservations = reservationService.getReservationsForStore(storeId, date);
        return ResponseEntity.ok(reservations);
    }

    /**
     * 관리자 권한 - 예약 상태를 COMPLETED로 변경 API
     * PUT /api/reservations/{id}/complete
     * @param id 완료할 예약의 ID
     * @return 완료된 예약 정보와 HTTP 상태 코드 200 (OK)
     */
    @PutMapping("/{id}/complete")
    @PreAuthorize("hasRole('PARTNER')") // Spring Security를 사용한 권한 확인
    public ResponseEntity<ReservationDTO> completeReservation(@PathVariable Long id) {
        ReservationDTO completedReservation = reservationService.completeReservation(id);
        return ResponseEntity.ok(completedReservation);
    }
}
