package com.example.board_springboot.service;

import com.example.board_springboot.common.Criteria;
import com.example.board_springboot.domain.BoardVO;
import com.example.board_springboot.mapper.AttachMapper;
import com.example.board_springboot.mapper.BoardMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardMapper boardMapper;
    private final AttachMapper attachMapper;

    @Override
    public List<BoardVO> getAll(Criteria cri) {
        List<BoardVO> all = boardMapper.getAll(cri);
        log.info("all : {}", all);
        return all;
    }

    @Override
    public int totalCount(Criteria cri) {
        int total = boardMapper.totalCount(cri);
        log.info("boardService total : {}", total);
        return total;
    }

    @Override
    public BoardVO get(Long id) {
        BoardVO boardVO = boardMapper.get(id);
        log.info("get boardVO: {}", boardVO);
        return boardVO;
    }

    @Override
    public int updateHit(Long id) {
        int updateHit = boardMapper.updateHit(id);
        log.info("updateHit: {}", updateHit);
        return updateHit;
    }

    @Override
    public void remove(Long id) {
        boolean result = boardMapper.remove(id);
        log.info("remove result : {}", result);
    }

    @Override
    public boolean modify(BoardVO board) {
        boolean result = boardMapper.modify(board);
        log.info("modify result: {}", result);
        return result;
    }

    @Override
    public boolean register(BoardVO board) {
        boolean result = boardMapper.register(board);
        log.info("register result: {}", result);

//        boardMapper.insertSelectKey(board);
//        if (board.getAttachList() == null || board.getAttachList().size() == 0) {
//            return false;
//        }
//
//        board.getAttachList().forEach(attach -> {
//            attach.setBoardId(board.getId());
//            attachMapper.insert(attach);
//        });
        return result;
    }

    @Override
    public List<String> getCategoryList() {
        List<String> categoryList = boardMapper.getCategoryList();
        log.info("getCategoryList: {}", categoryList);
        return categoryList;
    }
}
