package com.example.board_springboot.controller;

import com.example.board_springboot.dto.JoinDTO;
import com.example.board_springboot.dto.LoginDTO;
import com.example.board_springboot.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class MemberApiController {

    private final AuthenticationManager authenticationManager;

    private final MemberService memberService;

    @PostMapping("/member/join")
    public ResponseEntity<?> join(@RequestBody JoinDTO joinDTO) {	// username, password, email
        log.info("joinDTO: {}", joinDTO);
        log.info("MemberApiController: save 호출됨");

        if (memberService.joinMember(joinDTO)) {
            log.info("join 성공");
            return new ResponseEntity<Integer>(1, HttpStatus.OK);
        }
        return new ResponseEntity<Integer>((Integer) null, HttpStatus.BAD_REQUEST);
    }

    // PrincipalDetailsService loadUserByUsername 로 대체
/*    @PostMapping("/member/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        log.info("MemberApiController: login, loginDTO: {}", loginDTO);

        MemberVO principal = memberService.loginMember(loginDTO.getEmail(), loginDTO.getPassword()); 	//principal 접근주체
        log.info("principal: {}", principal);
        if(principal != null) {
//			session.setAttribute("principal", principal);
            return new ResponseEntity<Integer>(1, HttpStatus.OK);
        }
        return new ResponseEntity<Integer>((Integer) null, HttpStatus.NOT_FOUND);
    }*/

    @PostMapping("/member/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        log.info("MemberApiController: login, loginDTO: {}", loginDTO);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getEmail(),
                        loginDTO.getPassword()
                ));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new ResponseEntity<Integer>(1, HttpStatus.OK);
    }

}
