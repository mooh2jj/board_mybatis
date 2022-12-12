package com.example.board_springboot.service.reply;

import com.example.board_springboot.domain.ReplyVO;

import java.util.List;

public interface ReplyService {
    int insert(ReplyVO reply);

    List<ReplyVO> getList(Long boardId);
}
