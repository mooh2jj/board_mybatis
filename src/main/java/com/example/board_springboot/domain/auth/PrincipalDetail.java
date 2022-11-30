package com.example.board_springboot.domain.auth;

import com.example.board_springboot.domain.MemberVO;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.stream.Collectors;

@Getter
public class PrincipalDetail extends User {

    private MemberVO memberVO;


    public PrincipalDetail(String username, String password,
                           Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public PrincipalDetail(MemberVO vo) {
        super(vo.getEmail(), vo.getPassword(), vo.getAuthList().stream()
                .map(auth -> new SimpleGrantedAuthority(auth.getAuth())).collect(Collectors.toList()));
        this.memberVO = vo;
    }
}
