package com.project.reservationservice.controller;

import com.project.reservationservice.DTO.SignInRequestDTO;
import com.project.reservationservice.DTO.SignUpRequestDTO;
import com.project.reservationservice.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "회원 등록 및 로그인 API")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequestDTO signUpRequestDTO) {
        log.info("회원가입 요청 : {}",signUpRequestDTO.getEmail());
        authService.signUp(signUpRequestDTO);
        log.info("회원가입 성공 : {}",signUpRequestDTO.getEmail());
        return ResponseEntity.ok("회원가입 완료");
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody SignInRequestDTO signInRequestDTO) {
        log.info("로그인 요청 : {}", signInRequestDTO.getEmail());
        String token = authService.signIn(signInRequestDTO);
        log.info("로그인 성공 : {}", signInRequestDTO.getEmail());
        return ResponseEntity.ok(token);
    }

}
