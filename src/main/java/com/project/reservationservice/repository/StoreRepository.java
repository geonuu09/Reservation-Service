package com.project.reservationservice.repository;

import com.project.reservationservice.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {
}