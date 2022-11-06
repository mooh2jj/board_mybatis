package com.example.board_springboot.domain;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class BoardVO {
    private Long id;
    private String category;
    private String password;
    private String title;
    private String content;
    private String writer;
    private int hit;

    /**
     * Timestamp -> LocalDateTime
     */
    private Timestamp createdAt;
    private Timestamp updatedAt;

    private List<AttachVO> attachList;

}
