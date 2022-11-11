package com.example.board_springboot.dto;

import lombok.Data;

@Data
public class PasswordRequest {

    private Long boardId;
    private String passwordVal;
}
