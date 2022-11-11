package com.example.board_springboot.service;

import com.example.board_springboot.common.exception.CustomException;
import com.example.board_springboot.common.exception.ErrorCode;
import com.example.board_springboot.domain.ReplyVO;
import com.example.board_springboot.mapper.BoardMapper;
import com.example.board_springboot.mapper.ReplyMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {

    private final BoardMapper boardMapper;
    private final ReplyMapper replyMapper;

    @Override
    @Transactional
    public int insert(ReplyVO reply) {
        if (reply == null) {
            throw new CustomException(ErrorCode.NO_FOUND_ENTITY);
        }
        int result = replyMapper.insert(reply);
        log.info("reply insert result: {}", result);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReplyVO> getList(Long boardId) {
        log.info("boardId: {}", boardId);

        if (boardMapper.findById(boardId) == null) {
            throw new CustomException(ErrorCode.NO_FOUND_ENTITY);
        }

        List<ReplyVO> replyList = replyMapper.findListByBoardId(boardId);
        log.info("replyList: {}", replyList);
        return replyList;
    }
}
