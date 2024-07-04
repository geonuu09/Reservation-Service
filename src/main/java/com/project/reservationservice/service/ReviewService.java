package com.project.reservationservice.service;

import com.project.reservationservice.domain.MemberEntity;
import com.project.reservationservice.domain.Reservation;
import com.project.reservationservice.domain.Review;
import com.project.reservationservice.dto.ReviewDTO;
import com.project.reservationservice.repository.MemberRepository;
import com.project.reservationservice.repository.ReservationRepository;
import com.project.reservationservice.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReservationRepository reservationRepository;
    private final MemberRepository memberRepository;

    public ReviewDTO createReview(ReviewDTO reviewDTO) {

        // 예약 확인
        Reservation reservation = reservationRepository.findById(reviewDTO.getReservationId())
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        // 예약 상태 확인(COMPLETED 상태일 떄만 리뷰 작성)
        if(reservation.getStatus() != Reservation.ReservationStatus.COMPLETED) {
            throw new RuntimeException("Cannot write a review for uncompleted reservation");
        }

        // 회원 확인
        MemberEntity member = memberRepository.findById(reviewDTO.getMemberId())
                .orElseThrow(()-> new RuntimeException("Member not found"));

        Review review = new Review();
        review.setReservation(reservation);
        review.setMember(member);
        review.setContent(reviewDTO.getContent());
        review.setRating(reviewDTO.getRating());

        Review savedReview = reviewRepository.save(review);
        return convertToDTO(savedReview);
    }

    // 리뷰 수정 (리뷰 작성자만 가능)
    public ReviewDTO updateReview(Long reviewId, ReviewDTO reviewDTO, Long memberId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        if (!review.getMember().getId().equals(memberId)) {
            throw new RuntimeException("You don't have permission to update this review");
        }

        review.setContent(reviewDTO.getContent());
        review.setRating(reviewDTO.getRating());

        Review updatedReview = reviewRepository.save(review);
        return convertToDTO(updatedReview);
    }
    // 리뷰 삭제 (리뷰 작성자 또는 매장 관리자만 가능)
    public void deleteReview(Long reviewId, Long memberId, boolean isStoreManager) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        if (!review.getMember().getId().equals(memberId) && !isStoreManager) {
            throw new RuntimeException("You don't have permission to delete this review");
        }

        reviewRepository.delete(review);
    }

    // 매장의 모든 리뷰 조회
    public List<ReviewDTO> getReviewsByStore(Long storeId) {
        return reviewRepository.findByReservationStoreId(storeId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private ReviewDTO convertToDTO(Review review) {
        ReviewDTO dto = new ReviewDTO();
        dto.setId(review.getId());
        dto.setReservationId(review.getReservation().getId());
        dto.setMemberId(review.getMember().getId());
        dto.setContent(review.getContent());
        dto.setRating(review.getRating());
        dto.setCreatedAt(review.getCreatedAt());
        dto.setUpdatedAt(review.getUpdatedAt());
        return dto;
    }
}
