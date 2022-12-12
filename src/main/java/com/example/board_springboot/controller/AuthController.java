package com.example.board_springboot.controller;

import com.example.board_springboot.domain.MemberVO;
import com.example.board_springboot.dto.UserInfo;
import com.example.board_springboot.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final MemberService memberService;

    @GetMapping("/foo")
    public String foo(@UserInfo MemberVO memberVO) {
        log.info(">>>>>> memberVO: {}", memberVO);

        MemberVO member = memberService.getMember(memberVO);
        return member.getName();
    }



}
