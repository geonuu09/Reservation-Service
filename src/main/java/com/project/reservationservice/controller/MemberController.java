package com.project.reservationservice.controller;

import com.project.reservationservice.dto.MemberDTO;
import com.project.reservationservice.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/{email}")
    public ResponseEntity<MemberDTO> getMemberByEmail(@PathVariable String email) {
        MemberDTO member = memberService.findByEmail(email);
        return ResponseEntity.ok(member);
    }
}