package com.example.board_springboot.dto;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) // --- 1
@Target(ElementType.PARAMETER) // --- 2
public @interface UserInfo {
}
