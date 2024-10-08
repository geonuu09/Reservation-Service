package com.project.reservationservice.repository;

import com.project.reservationservice.domain.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    Optional<MemberEntity> findByEmail(String email);
    boolean existsByEmail(String email);
}
