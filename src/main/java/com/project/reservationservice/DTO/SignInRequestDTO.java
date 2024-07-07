package com.project.reservationservice.DTO;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignInRequestDTO {
    private String email;
    private String password;
}