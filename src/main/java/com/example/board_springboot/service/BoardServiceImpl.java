package com.example.board_springboot.service;

import com.example.board_springboot.common.Criteria;
import com.example.board_springboot.common.exception.CustomException;
import com.example.board_springboot.common.exception.ErrorCode;
import com.example.board_springboot.domain.AttachVO;
import com.example.board_springboot.domain.BoardVO;
import com.example.board_springboot.mapper.AttachMapper;
import com.example.board_springboot.mapper.BoardMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardMapper boardMapper;
    private final AttachMapper attachMapper;

    @Override
    @Transactional(readOnly = true)
    public List<BoardVO> getAll(Criteria cri) {
        List<BoardVO> all = boardMapper.getAll(cri);

        if (all == null) {
            throw new CustomException(ErrorCode.NO_FOUND_ENTITY);
        }
        log.info("all : {}", all);
        return all;
    }

    @Override
    @Transactional(readOnly = true)
    public int totalCount(Criteria cri) {
        int total = boardMapper.totalCount(cri);
        log.info("boardService total : {}", total);
        return total;
    }

    @Override
    @Transactional(readOnly = true)
    public BoardVO get(Long id) {
        BoardVO board = boardMapper.get(id);

        log.info("get board: {}", board);
        return board;
    }

    @Override
    @Transactional
    public int updateHit(Long id) {
        BoardVO board = boardMapper.get(id);
        validateEntity(board);
        int updateHit = boardMapper.updateHit(id);
        log.info("updateHit: {}", updateHit);
        return updateHit;
    }

    @Override
    @Transactional
    public void remove(Long id) {
        BoardVO board = boardMapper.get(id);
        validateEntity(board);
        
        boolean result = boardMapper.remove(id);
        log.info("remove result : {}", result);
    }

    @Override
    @Transactional
    public boolean modify(BoardVO board) {
        log.info("modify......" + board);
        validateEntity(board);

        attachMapper.deleteAll(board.getId());

        boardMapper.removeFileYN(board.getId());
        boolean result = boardMapper.modify(board);
        log.info("modify result: {}", result);

        // 게시판 수정 후 attach insert 가능하게 처리
        if (result && board.getAttachList() != null && board.getAttachList().size() != 0) {
            board.getAttachList().forEach(attach -> {
                attach.setBoardId(board.getId());
                attachMapper.insert(attach);
                log.info("modify attach: {}", attach);
            });
            boardMapper.registerFileYN(board.getId());
        }
        return result;
    }

    @Override
    @Transactional
    public void register(BoardVO board) {
        validateEntity(board);
        
        long result = boardMapper.registerWithSelectKey(board);
        log.info("register result: {}", result);

        if (board.getAttachList() != null && board.getAttachList().size() != 0) {
            board.getAttachList().forEach(attach -> {
                attach.setBoardId(board.getId());
                attachMapper.insert(attach);
            });
            boardMapper.registerFileYN(board.getId());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getCategoryList() {
        List<String> categoryList = boardMapper.getCategoryList();
        if (categoryList == null) {
            throw new CustomException(ErrorCode.NO_FOUND_ENTITY);
        }
        
        log.info("getCategoryList: {}", categoryList);
        return categoryList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AttachVO> getAttachList(Long boardId) {
        log.info("get Attach list by boardId: {}", boardId);
        BoardVO board = boardMapper.get(boardId);
        validateEntity(board);
        
        List<AttachVO> attachList = attachMapper.findByBoardId(boardId);
        log.info("attachList: {}", attachList);
        return attachList;
    }

    @Override
    @Transactional(readOnly = true)
    public String getPassword(Long boardId) {
        log.info("get Password boardId: {}", boardId);
        
        BoardVO board = boardMapper.get(boardId);
        validateEntity(board);
        
        String password = boardMapper.findPassword(boardId);
        log.info("password: {}", password);
        return password;
    }

    /**
     * 게시글 검증
     * 1) 엔티티 존재하는지 체크
     * 2) 기존 엔티티 등록했는지 중복체크
     * @param board
     */
    private void validateEntity(BoardVO board) {
        if (board == null) {
            throw new CustomException(ErrorCode.NO_FOUND_ENTITY);
        }
        if (boardMapper.getCount(board.getId()) > 1) {
            throw new CustomException(ErrorCode.DUPLICATED_ENTITY);
        }

    }
}
