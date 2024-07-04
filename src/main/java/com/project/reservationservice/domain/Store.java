package com.project.reservationservice.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "store")
@Getter
@Setter
public class Store extends BaseEntity {

    @Column(name = "store_name", length = 20, nullable = false)
    private String storeName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private MemberEntity owner;

    // 스토어 설명
    @Column(name = "store_desc", length = 255)
    private String storeDesc;

    // 스토어 주소
    @Column(name = "store_address", length = 50, nullable = false)
    private String storeAddress;
    // 스토어 카테고리
    @Column(length = 20)
    private String category;

    private String phoneNumber;

    @ElementCollection
    @CollectionTable(name = "store_amenities", joinColumns = @JoinColumn(name = "store_id"))
    @Column(name = "amenity")
    private List<String> amenities;


    @Column(name = "open_time")
    private LocalTime openTime;

    @Column(name = "close_time")
    private LocalTime closeTime;

    // kiosk id = store id
    public Long getKioskId() {
        return getId();
    }

}