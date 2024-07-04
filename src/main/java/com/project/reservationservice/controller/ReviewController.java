package com.project.reservationservice.controller;

import com.project.reservationservice.dto.ReservationDTO;
import com.project.reservationservice.dto.ReviewDTO;
import com.project.reservationservice.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public ResponseEntity<ReviewDTO> createReview(@RequestBody ReviewDTO reviewDTO) {
        ReviewDTO createdReview = reviewService.createReview(reviewDTO);
        return new ResponseEntity<>(createdReview, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReviewDTO> updateReview(@PathVariable Long id,
                                                  @RequestBody ReviewDTO reviewDTO,
                                                  @RequestParam Long memberId) {
        ReviewDTO updatedReview = reviewService.updateReview(id, reviewDTO, memberId);
        return ResponseEntity.ok(updatedReview);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id,
                                             @RequestParam Long memberId,
                                             @RequestParam boolean isStoreManager) {
        reviewService.deleteReview(id, memberId, isStoreManager);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/store/{storeId}")
    public ResponseEntity<List<ReviewDTO>> getReviewsByStore(@PathVariable Long storeId) {
        List<ReviewDTO> reviews = reviewService.getReviewsByStore(storeId);
        return ResponseEntity.ok(reviews);
    }
}