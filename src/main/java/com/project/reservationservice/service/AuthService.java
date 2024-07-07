package com.project.reservationservice.service;

import com.project.reservationservice.DTO.SignInRequestDTO;
import com.project.reservationservice.DTO.SignUpRequestDTO;
import com.project.reservationservice.domain.MemberEntity;
import com.project.reservationservice.repository.MemberRepository;
import com.project.reservationservice.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;


    @Transactional
    public void signUp(SignUpRequestDTO signUpRequestDTO) {
        log.info("회원가입 시작 : {} ", signUpRequestDTO.getEmail());
        if (memberRepository.existsByEmail(signUpRequestDTO.getEmail())) {
            log.error("이미 존재하는 이메일 : {}", signUpRequestDTO.getEmail());
            throw new RuntimeException("이미 존재하는 이메일");
        }

        MemberEntity member = MemberEntity.builder()
                .email(signUpRequestDTO.getEmail())
                .realname(signUpRequestDTO.getRealname())
                .password(passwordEncoder.encode(signUpRequestDTO.getPassword()))
                .phonenumber(signUpRequestDTO.getPhonenumber())
                .role(MemberEntity.MemberRole.valueOf(signUpRequestDTO.getRole()))
                .build();
        memberRepository.save(member);
        log.info("회원가입 완료 : {}", member.getEmail());
    }

    public String signIn(SignInRequestDTO signInRequestDTO) {
        log.info("로그인 시도: {}", signInRequestDTO.getEmail());

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            signInRequestDTO.getEmail(),
                            signInRequestDTO.getPassword()
                    )
            );

            log.info("인증 성공: {}", signInRequestDTO.getEmail());

            String token = tokenProvider.createToken(authentication.getName(), authentication.getAuthorities());

            log.info("토큰 생성 성공: {}", signInRequestDTO.getEmail());
            return token;
        } catch (AuthenticationException e) {
            log.error("인증 실패: {}", signInRequestDTO.getEmail(), e);
            throw new RuntimeException("이메일 또는 비밀번호가 올바르지 않습니다.");
        } catch (Exception e) {
            log.error("로그인 처리 중 예외 발생: {}", signInRequestDTO.getEmail(), e);
            throw new RuntimeException("로그인 처리 중 오류 발생", e);
        }
    }
}
