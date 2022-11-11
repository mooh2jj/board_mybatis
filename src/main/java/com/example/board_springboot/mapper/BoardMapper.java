package com.example.board_springboot.mapper;

import com.example.board_springboot.common.Criteria;
import com.example.board_springboot.domain.BoardVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
    List<BoardVO> findAll(Criteria cri);

    int findTotalCount(Criteria cri);

    BoardVO findById(Long id);

    int updateHit(Long id);

    boolean remove(Long id);

    boolean modify(BoardVO board);

    boolean register(BoardVO board);

    List<String> findCategoryList();

    /**
     * board insert 후 boardId 리턴
     * selectKey 리턴값은 parameterType(board) 객체에 넘어간다
     * @param board 게시글
     * @return result 1, -1
     */
    Long registerWithSelectKey(BoardVO board);

    String findPassword(Long boardId);

    /**
     * 파일 존재 true 등록
     * @param id 게시글 seq
     */
    void registerFileYN(Long id);

    /**
     * 기존 파일 유무 false 등록
     * @param id 게시글 seq
     */
    void removeFileYN(Long id);

    int findCount(Long id);
}
