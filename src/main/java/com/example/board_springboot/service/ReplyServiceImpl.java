package com.example.board_springboot.service;

import com.example.board_springboot.domain.ReplyVO;
import com.example.board_springboot.mapper.ReplyMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {

    private final ReplyMapper replyMapper;

    @Override
    public int insert(ReplyVO reply) {
        int result = replyMapper.insert(reply);
        log.info("reply insert result: {}", result);
        return result;
    }

    @Override
    public List<ReplyVO> getList(Long boardId) {
        log.info("boardId: {}", boardId);
        List<ReplyVO> replyList = replyMapper.getList(boardId);
        log.info("replyList: {}", replyList);
        return replyList;
    }
}
