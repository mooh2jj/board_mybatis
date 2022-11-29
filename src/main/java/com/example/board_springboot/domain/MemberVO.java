package com.example.board_springboot.domain;

import com.example.board_springboot.domain.auth.RoleType;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class MemberVO {

    private Long id;

    private String name;

    private String email;

    private String password;

    private Timestamp createdAt;

    private RoleType role;
//    private List<AuthVO> authList;

    @Builder
    public MemberVO(String name, String email, String password, Timestamp createdAt, RoleType role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.createdAt = createdAt;
//        this.authList = authList;
        this.role = role;
    }
}
