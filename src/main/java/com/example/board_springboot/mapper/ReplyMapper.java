package com.example.board_springboot.mapper;

import com.example.board_springboot.domain.ReplyVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReplyMapper {
    int insert(ReplyVO reply);

    List<ReplyVO> getList(Long boardId);
}
