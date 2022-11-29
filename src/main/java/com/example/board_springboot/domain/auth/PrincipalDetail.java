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

    private MemberVO member;

    public PrincipalDetail(String email, String password, Collection<? extends GrantedAuthority> authorities) {
        super(email, password, authorities);
    }
    public PrincipalDetail(MemberVO member) {

        super(member.getEmail(), member.getPassword(), member.getAuthList().stream()
                .map(auth -> new SimpleGrantedAuthority(auth.getAuth()))
                .collect(Collectors.toList()));

        this.member = member;
    }



}
