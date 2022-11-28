package com.example.board_springboot.mapper;

import com.example.board_springboot.domain.MemberVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {

    MemberVO findMember(String name, String password);

    boolean joinMember(MemberVO memberVO);

    MemberVO findMemberByEmailAndPassword(String email, String password);
}
