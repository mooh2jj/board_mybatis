package com.example.board_springboot.service;

import com.example.board_springboot.common.exception.CustomException;
import com.example.board_springboot.common.exception.ErrorCode;
import com.example.board_springboot.domain.MemberVO;
import com.example.board_springboot.domain.auth.AuthType;
import com.example.board_springboot.domain.auth.AuthVO;
import com.example.board_springboot.dto.JoinDTO;
import com.example.board_springboot.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

import static com.example.board_springboot.common.utils.EncodePasswordUtils.passwordEncoder;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final HttpSession httpSession;
    private final MemberMapper memberMapper;

//    public MemberVO getMemberByName(String name) {
//        MemberVO memberVO = memberMapper.findMemberByName(name);
//        log.info("getMemberByEmail memberVO: {}", memberVO);
//        return memberVO;
//    }

    public MemberVO getMember(MemberVO memberVO) {
        return memberMapper.findMember(memberVO.getName(), memberVO.getPassword());
    }

    @Transactional
    public boolean joinMember(JoinDTO joinDTO) {

        if (memberMapper.findMemberByName(joinDTO.getName()) != null) {
            throw new CustomException("이미 존재하는 맴버입니다.", ErrorCode.DUPLICATED_ENTITY);
        }

        MemberVO memberVO = MemberVO.builder()
                .name(joinDTO.getName())
                .email(joinDTO.getEmail())
                .password(passwordEncoder().encode(joinDTO.getPassword()))
                .auth(AuthType.ROLE_USER)
                .build();

        AuthVO authVO = AuthVO.builder()
                .username(joinDTO.getName())
                .auth("ROLE_USER")
                .build();

        memberMapper.insertAuth(authVO);

        memberVO.addMemberRole(authVO);

        return memberMapper.joinMember(memberVO);
    }

/*    public MemberVO loginMember(String username, String password) {
        MemberVO principal = memberMapper.findMemberByEmailAndPassword(username, password);
        if (principal != null) {
            httpSession.setAttribute("principal", principal);
            return principal;
        } else {
            throw new CustomException("principal이 없습니다.", ErrorCode.NOT_FOUND_USER);
        }
    }*/
}
