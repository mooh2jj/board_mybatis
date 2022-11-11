package com.example.board_springboot.mapper;

import com.example.board_springboot.common.Criteria;
import com.example.board_springboot.domain.BoardVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
    List<BoardVO> getAll(Criteria cri);

    int totalCount(Criteria cri);

    BoardVO get(Long id);

    int updateHit(Long id);

    boolean remove(Long id);

    boolean modify(BoardVO board);

    boolean register(BoardVO board);

    List<String> getCategoryList();

    /**
     * board insert 후 boardId 리턴
     * selectKey 리턴값은 parameterType(board) 객체에 넘어간다
     * @param board
     * @return
     */
    Long registerWithSelectKey(BoardVO board);

    String findPassword(Long boardId);

    /**
     * 파일 존재 true 등록
     * @param id
     */
    void registerFileYN(Long id);

    /**
     * 기존 파일 유무 false 등록
     * @param id
     */
    void removeFileYN(Long id);

    int getCount(Long id);
}
