package com.example.board_springboot.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class BoardVO {
    private Long id;
    private String category;
    private String password;
    private String title;
    private String content;
    private String writer;
    private int hit;

    private boolean fileYn;
    private String fileUUID;

    /**
     * Timestamp -> LocalDateTime
     */
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private List<AttachVO> attachList;

}
