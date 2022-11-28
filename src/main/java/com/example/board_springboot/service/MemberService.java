package com.example.board_springboot.service;

import com.example.board_springboot.common.exception.CustomException;
import com.example.board_springboot.common.exception.ErrorCode;
import com.example.board_springboot.domain.MemberVO;
import com.example.board_springboot.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final HttpSession httpSession;
    private final MemberMapper memberMapper;

    public MemberVO getMember(MemberVO memberVO) {
        return memberMapper.findMember(memberVO.getName(), memberVO.getPassword());
    }

    public boolean joinMember(MemberVO memberVO) {
        return memberMapper.joinMember(memberVO);
    }

    public MemberVO loginMember(MemberVO memberVO) {
        MemberVO principal = memberMapper.findMemberByEmailAndPassword(memberVO.getEmail(), memberVO.getPassword());
        if (principal != null) {
            httpSession.setAttribute("principal", principal);
            return principal;
        } else {
            throw new CustomException("principal이 없습니다.", ErrorCode.NOT_FOUND_USER);
        }
    }
}
