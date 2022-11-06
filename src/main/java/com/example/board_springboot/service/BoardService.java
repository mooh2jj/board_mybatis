package com.example.board_springboot.service;

import com.example.board_springboot.common.Criteria;
import com.example.board_springboot.domain.BoardVO;

import java.util.List;

public interface BoardService {
    List<BoardVO> getAll(Criteria cri);

    int totalCount(Criteria cri);

    BoardVO get(Long id);

    int updateHit(Long id);

    void remove(Long id);

    boolean modify(BoardVO board);

    void register(BoardVO board);

    List<String> getCategoryList();
}
