package com.project.reservationservice.service;

import com.project.reservationservice.domain.MemberEntity;
import com.project.reservationservice.DTO.MemberDTO;
import com.project.reservationservice.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 회원 가입 기능
     * @param memberDTO 회원 정보 DTO
     * @return 생성된 회원 정보 DTO
     */
    public MemberDTO register(MemberDTO memberDTO) {
        log.info("회원 가입 시도: {}", memberDTO.getEmail());
        if (memberRepository.findByEmail(memberDTO.getEmail()).isPresent()) {
            log.warn("이미 존재하는 이메일: {}", memberDTO.getEmail());
            throw new RuntimeException("Email already exists");
        }

        MemberEntity member = new MemberEntity();
        member.setEmail(memberDTO.getEmail());
        member.setUsername(memberDTO.getUsername());
        member.setPassword(passwordEncoder.encode(memberDTO.getPassword()));
        member.setPhonenumber(memberDTO.getPhonenumber());
        member.setRole(MemberEntity.MemberRole.ROLE_PARTNER);

        MemberEntity savedMember = memberRepository.save(member);
        log.info("회원 가입 성공: {}", savedMember.getEmail());
        return convertToDTO(savedMember);
    }

    /**
     * 로그인 기능
     * @param email 이메일
     * @param password 비밀번호
     * @return 로그인한 회원 정보 DTO
     */
    public MemberDTO login(String email, String password) {
        log.info("로그인 시도: {}", email);
        MemberEntity member = memberRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.warn("존재하지 않는 이메일: {}", email);
                    return new UsernameNotFoundException("User not found with email: " + email);
                });

        if (!passwordEncoder.matches(password, member.getPassword())) {
            log.warn("잘못된 비밀번호: {}", email);
            throw new BadCredentialsException("Invalid password");
        }

        log.info("로그인 성공: {}", email);
        return convertToDTO(member);
    }

    private MemberDTO convertToDTO(MemberEntity member) {
        MemberDTO dto = new MemberDTO();
        dto.setId(member.getId());
        dto.setEmail(member.getEmail());
        dto.setUsername(member.getUsername());
        dto.setRole(member.getRole());
        return dto;
    }
}