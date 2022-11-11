package com.example.board_springboot.common.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


public class EncodePasswordUtils {
    /* 순환참조 안될려면 이렇게 */
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
