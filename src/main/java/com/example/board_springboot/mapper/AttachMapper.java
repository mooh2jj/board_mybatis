package com.example.board_springboot.mapper;

import com.example.board_springboot.domain.AttachVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AttachMapper {
    List<AttachVO> findByBoardId(Long boardId);

    void insert(AttachVO vo);

    void delete(String uuid);

    /**
     * 게시글당 기존 파일 전부삭제
     * @param id
     */
    void deleteAll(Long id);
}
