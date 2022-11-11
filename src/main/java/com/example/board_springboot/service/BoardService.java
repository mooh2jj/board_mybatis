package com.example.board_springboot.service;

import com.example.board_springboot.common.Criteria;
import com.example.board_springboot.domain.AttachVO;
import com.example.board_springboot.domain.BoardVO;

import java.util.List;

public interface BoardService {
    List<BoardVO> getAll(Criteria cri);

    /**
     * 게시글 총 카운트
     * @param cri
     * @return int count
     */
    int totalCount(Criteria cri);

    BoardVO get(Long id);

    /**
     * 상세보기 후 조회수 증가
     * @param id
     * @return
     */
    int updateHit(Long id);

    void remove(Long id);

    boolean modify(BoardVO board);

    void register(BoardVO board);

    /**
     * DB에서 등록한 카테고리 리스트
     * @return
     */
    List<String> getCategoryList();

    List<AttachVO> getAttachList(Long boardId);

    /**
     * 게시글당 등록한 비밀번호 가져오기
     * @param boardId
     * @return
     */
    String getPassword(Long boardId);
}
