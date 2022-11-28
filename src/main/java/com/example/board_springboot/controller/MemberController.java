package com.example.board_springboot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class MemberController {

    @GetMapping("/member/joinForm")
    public String joinForm() {
        return "/member/joinForm";
    }

    @GetMapping("/member/loginForm")
    public String loginForm() {
        return "/member/loginForm";
    }

}
