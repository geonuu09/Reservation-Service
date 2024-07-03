package com.project.reservationservice.service;

import com.project.reservationservice.domain.MemberEntity;
import com.project.reservationservice.domain.Reservation;
import com.project.reservationservice.domain.Store;
import com.project.reservationservice.dto.ReservationDTO;
import com.project.reservationservice.repository.MemberRepository;
import com.project.reservationservice.repository.ReservationRepository;
import com.project.reservationservice.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final StoreRepository storeRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public ReservationDTO createReservation(ReservationDTO reservationDTO) {
        Store store = storeRepository.findById(reservationDTO.getStoreId())
                .orElseThrow(() -> new RuntimeException("Store not found"));
        MemberEntity member = memberRepository.findById(reservationDTO.getMemberId())
                .orElseThrow(() -> new RuntimeException("Member not found"));

        Reservation reservation = new Reservation();
        reservation.setStore(store);
        reservation.setMember(member);
        reservation.setReservationDate(reservationDTO.getReservationDate());
        reservation.setStatus(Reservation.ReservationStatus.CONFIRMED);

        Reservation savedReservation = reservationRepository.save(reservation);
        return convertToDTO(savedReservation);
    }

    // 예약 조회
    public ReservationDTO getReservation(Long id) {
        Reservation reservation = reservationRepository.findByMemberId(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));
        return convertToDTO(reservation);
    }

    // 예약 상태 업데이트
    @Transactional
    public ReservationDTO updateReservationStatus(Long id, Reservation.ReservationStatus status) {
        Reservation reservation = reservationRepository.findByMemberId(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));
        reservation.setStatus(status);
        return convertToDTO(reservationRepository.save(reservation));
    }

    public ReservationDTO cancelReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        // 예약 상태 확인
        if (reservation.getStatus() == Reservation.ReservationStatus.CANCELLED) {
            throw new IllegalStateException("Reservation is already cancelled");
        }

        // 현재 시간이 예약 시간 이후인지 확인
        if (LocalDate.now().isAfter(reservation.getReservationDate())) {
            throw new IllegalStateException("Cannot cancel past reservations");
        }

        reservation.setStatus(Reservation.ReservationStatus.CANCELLED);
        Reservation updatedReservation = reservationRepository.save(reservation);

        return convertToDTO(updatedReservation);
    }


    // 특정 매장의 특정 시간대 예약 목록 조회
    public List<ReservationDTO> getReservationsForStore(Long storeId, LocalDate start, LocalDate end) {
        return reservationRepository.findByStoreIdAndReservationDateBetween(storeId, start, end)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private ReservationDTO convertToDTO(Reservation reservation) {
        ReservationDTO dto = new ReservationDTO();
        dto.setId(reservation.getId());
        dto.setStoreId(reservation.getStore().getId());
        dto.setMemberId(reservation.getMember().getId());
        dto.setReservationDate(reservation.getReservationDate());
        dto.setStatus(reservation.getStatus());
        return dto;
    }
}
