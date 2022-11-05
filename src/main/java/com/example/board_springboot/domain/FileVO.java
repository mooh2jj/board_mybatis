package com.example.board_springboot.domain;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class FileVO {

    private Long id;
    private Long boardId;

    private String path;
    private String fileUUIDName;
    private String fileName;
    private long size;
    private Timestamp createdAt;

}
