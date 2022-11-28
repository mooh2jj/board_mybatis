package com.example.board_springboot.controller;

import com.example.board_springboot.domain.MemberVO;
import com.example.board_springboot.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping("/member/join")
    public ResponseEntity<?> join(@RequestBody MemberVO memberVO) {	// username, password, email
        log.info("memberVO: {}", memberVO);
        log.info("MemberApiController: save 호출됨");

        if (memberService.joinMember(memberVO)) {
            log.info("join 성공");
            return new ResponseEntity<Integer>(1, HttpStatus.OK);
        }
        return new ResponseEntity<Integer>((Integer) null, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/member/login")
	public ResponseEntity<?> login(@RequestBody MemberVO memberVO) {
        log.info("memberVO: {}", memberVO);
        log.info("MemberApiController: login 호출됨");

        MemberVO principal = memberService.loginMember(memberVO); 	//principal 접근주체
        log.info("principal: {}", principal);
		if(principal != null) {
//			session.setAttribute("principal", principal);
            return new ResponseEntity<Integer>(1, HttpStatus.OK);
		}
		return new ResponseEntity<Integer>((Integer) null, HttpStatus.NOT_FOUND);
	}

}
