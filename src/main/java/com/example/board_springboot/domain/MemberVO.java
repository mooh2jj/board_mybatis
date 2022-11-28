package com.example.board_springboot.domain;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class MemberVO {

    private Long id;

    private String name;

    private String email;

    private String password;

    private Timestamp createdAt;

}
