package com.example.board_springboot.domain;

import lombok.Data;

@Data
public class AttachVO {

    private String uuid;
    private String uploadPath;
    private String fileName;
    private boolean image;

    private Long boardId;
}
