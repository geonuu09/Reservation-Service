package com.project.reservationservice.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
// BaseEntity를 상속한 엔티티들은 BaseEntity에 있는 멤버변수들을 모두 컬럼으로 인식
@MappedSuperclass
// 엔티티 객체의 변화(생성, 수정)를 감지, 자동으로 특정 필드를 업데이트
@EntityListeners(AuditingEntityListener.class)
@Setter
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    @Column(updatable = false)
    //생성 시간
    private LocalDateTime createdAt;

    @LastModifiedDate
    //마지막 수정 시간
    private LocalDateTime updatedAt;
}