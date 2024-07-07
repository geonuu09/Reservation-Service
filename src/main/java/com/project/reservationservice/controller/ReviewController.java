package com.project.reservationservice.controller;

import com.project.reservationservice.DTO.ReviewDTO;
import com.project.reservationservice.service.ReviewService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@Tag(name = "REVIEW", description = "리뷰 API")
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    /**
     * 리뷰 생성 API
     * POST /api/reviews
     * @param reviewDTO 생성할 리뷰 정보
     * @return 생성된 리뷰 정보와 상태코드 201 반환
     */
    @PostMapping
    public ResponseEntity<ReviewDTO> createReview(@RequestBody ReviewDTO reviewDTO) {
        ReviewDTO createdReview = reviewService.createReview(reviewDTO);
        return new ResponseEntity<>(createdReview, HttpStatus.CREATED);
    }

    /**
     * 리뷰 수정 API
     * PUT /api/reviews/{id}?memberId={memberId}
     * @param id 수정할 리뷰의 ID
     * @param reviewDTO 수정할 리뷰 정보
     * @param memberId 리뷰를 수정하는 회원의 ID
     * @return 수정된 리뷰 정보와 상태코드 200 반환
     */
    @PutMapping("/{id}")
    public ResponseEntity<ReviewDTO> updateReview(@PathVariable Long id,
                                                  @RequestBody ReviewDTO reviewDTO,
                                                  @RequestParam Long memberId) {
        ReviewDTO updatedReview = reviewService.updateReview(id, reviewDTO, memberId);
        return ResponseEntity.ok(updatedReview);
    }

    /**
     * 리뷰 삭제 API
     * DELETE /api/reviews/{id}?memberId={memberId}&isStoreManager={isStoreManager}
     * @param id 삭제할 리뷰의 ID
     * @param memberId 리뷰를 삭제하는 회원의 ID
     * @param isStoreManager 가게 관리자 여부
     * @return 삭제 성공 시 상태코드 204 반환
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id,
                                             @RequestParam Long memberId,
                                             @RequestParam boolean isStoreManager) {
        reviewService.deleteReview(id, memberId, isStoreManager);
        return ResponseEntity.noContent().build();
    }

    /**
     * 가게별 리뷰 목록 조회 API
     * GET /api/reviews/store/{storeId}
     * @param storeId 조회할 가게의 ID
     * @return 가게별 리뷰 목록과 상태코드 200 반환
     */
    @GetMapping("/store/{storeId}")
    public ResponseEntity<List<ReviewDTO>> getReviewsByStore(@PathVariable Long storeId) {
        List<ReviewDTO> reviews = reviewService.getReviewsByStore(storeId);
        return ResponseEntity.ok(reviews);
    }
}
