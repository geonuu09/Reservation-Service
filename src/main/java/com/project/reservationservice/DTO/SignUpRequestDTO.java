package com.project.reservationservice.DTO;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequestDTO {
    private String email;
    private String realname;
    private String password;
    private String phonenumber;
    private String role;
}