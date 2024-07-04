package com.project.reservationservice.DTO;

import com.project.reservationservice.domain.MemberEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {
    private Long id;
    private String email;
    private String username;
    private String password;
    private String phonenumber;
    private MemberEntity.MemberRole role;

    // 비밀번호를 제외한 생성자
    public MemberDTO(Long id, String email, String username, MemberEntity.MemberRole role) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.role = role;
    }

    // toString 메소드 (비밀번호 제외)
    @Override
    public String toString() {
        return "MemberDTO{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", role=" + role +
                '}';
    }
}
