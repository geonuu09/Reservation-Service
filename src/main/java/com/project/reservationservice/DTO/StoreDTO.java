package com.project.reservationservice.DTO;

import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StoreDTO {
    private Long id;
    private String storeName;
    private String ownerEmail;
    private String storeDesc;
    private String storeAddress;
    private String category;
    private LocalTime openTime;
    private LocalTime closeTime;
    private String phoneNumber;
    private List<String> amenities;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long kioskId;

}