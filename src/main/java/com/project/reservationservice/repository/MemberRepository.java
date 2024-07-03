package com.project.reservationservice.repository;

import com.project.reservationservice.domain.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
}
