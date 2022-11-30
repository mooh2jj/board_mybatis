package com.example.board_springboot.domain.auth;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthVO {

    private String username;
    private String auth;

    @Builder
    public AuthVO(String username, String auth) {
        this.username = username;
        this.auth = auth;
    }
}
