package com.project.reservationservice.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "members")
@Entity
public class MemberEntity extends BaseEntity implements UserDetails {

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String realname;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String phonenumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemberRole role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return this.email; // email을 username으로 사용
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // 또는 계정 만료 로직 구현
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // 또는 계정 잠금 로직 구현
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 또는 자격 증명 만료 로직 구현
    }

    @Override
    public boolean isEnabled() {
        return true; // 또는 계정 활성화 상태 로직 구현
    }

    public enum MemberRole {
        ROLE_USER, ROLE_PARTNER
    }
}