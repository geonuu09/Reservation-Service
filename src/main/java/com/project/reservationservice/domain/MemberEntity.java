package com.project.reservationservice.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "members")
@Entity
public class MemberEntity extends BaseEntity {

    private String username;
    private String password;
    private String phonenumber;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    public enum UserRole {
        ROLE_USER, ROLE_PARTNER
    }
}
