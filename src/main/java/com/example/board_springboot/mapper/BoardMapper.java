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

    Long registerWithSelectKey(BoardVO board);
}
