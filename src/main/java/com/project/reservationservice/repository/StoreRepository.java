package com.project.reservationservice.repository;

import com.project.reservationservice.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Long> {
    // 매장 이름으로 매장 목록을 검색하는 메서드
    List<Store> findByStoreNameContainingIgnoreCase(String storeName);
}