package com.example.board_springboot.domain;

import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
public class ReplyVO {
    private int id;
    private long boardId;
    private String replyText;
    private String replyer;
    private Timestamp repliedAt;
}
