package com.project.reservationservice.controller;

import com.project.reservationservice.DTO.MemberDTO;
import com.project.reservationservice.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
public class MemberController {
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/register")
    public ResponseEntity<MemberDTO> register(@RequestBody MemberDTO memberDTO) {
        MemberDTO registeredMember = memberService.register(memberDTO);
        return new ResponseEntity<>(registeredMember, HttpStatus.CREATED);
    }

    @PostMapping("/login") // 이 메서드는 HTTP POST 요청을 처리하며, 요청 경로는 "/login"입니다.
    public ResponseEntity<MemberDTO> login(@RequestBody LoginRequest loginRequest) {
        try {
            // loginRequest 객체에서 이메일과 비밀번호를 추출하여 로그인 서비스 메서드를 호출합니다.
            MemberDTO memberDTO = memberService.login(loginRequest.getEmail(), loginRequest.getPassword());

            // 로그인 성공 시, 회원 정보를 담은 MemberDTO 객체를 HTTP 상태 코드 200 (OK)와 함께 반환합니다.
            return ResponseEntity.ok(memberDTO);
        } catch (UsernameNotFoundException | BadCredentialsException e) {
            // 로그인 실패 시, HTTP 상태 코드 401 (Unauthorized)와 함께 빈 응답 본문을 반환합니다.
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}