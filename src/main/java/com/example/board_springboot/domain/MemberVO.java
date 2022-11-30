package com.example.board_springboot.domain;

import com.example.board_springboot.domain.auth.AuthType;
import com.example.board_springboot.domain.auth.AuthVO;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class MemberVO {

    private Long id;

    private String name;

    private String email;

    private String password;

    private Timestamp createdAt;

    private AuthType auth;

    private List<AuthVO> authList = new ArrayList<>();

    @Builder
    public MemberVO(String name, String email, String password, Timestamp createdAt, AuthType auth) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.createdAt = createdAt;
        this.auth = auth;
    }
    public void addMemberRole(AuthVO authVO) {
        authList.add(authVO);
    }
}
