package com.project.reservationservice.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {
    private Long id;                // 리뷰 ID
    private Long reservationId;     // 예약 ID
    private Long memberId;          // 작성자(회원) ID
    private Long storeId;           // 매장 ID
    private String content;         // 리뷰 내용
    private int rating;             // 평점 (1-5)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}