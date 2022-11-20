package com.example.board_springboot.service;

import com.example.board_springboot.dto.Criteria;
import com.example.board_springboot.domain.AttachVO;
import com.example.board_springboot.domain.BoardVO;
import com.example.board_springboot.dto.PasswordRequest;

import java.util.List;

public interface BoardService {
    List<BoardVO> getAll(Criteria cri);

    /**
     * 게시글 총 카운트
     * @param cri 검색+페이징 파라미터
     * @return int count 값
     */
    int totalCount(Criteria cri);

    BoardVO get(Long id);

    /**
     * 상세보기 후 조회수 증가
     * @param id 게시글 seq
     * @return result 값 1, -1
     */
    int updateHit(Long id);

    void remove(Long id, String password);

    boolean modify(BoardVO board);

    void register(BoardVO board);

    /**
     * DB에서 등록한 카테고리 리스트
     * @return 카테고리 리스트
     */
    List<String> getCategoryList();

    List<AttachVO> getAttachList(Long boardId);

    /**
     * DB에 있는 암호환된 비밀번호 매칭확인
     * @param request boardId, 입력한 비밀번호
     * @return 비밀번호 확인 유무
     */
    boolean checkPassword(PasswordRequest request);
}
