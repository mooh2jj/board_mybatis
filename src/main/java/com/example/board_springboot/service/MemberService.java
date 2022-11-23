package com.example.board_springboot.service;

import com.example.board_springboot.domain.MemberVO;
import com.example.board_springboot.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberMapper memberMapper;

    public MemberVO getMember(MemberVO memberVO) {
        return memberMapper.findMember(memberVO.getName(), memberVO.getPassword());
    }

}
