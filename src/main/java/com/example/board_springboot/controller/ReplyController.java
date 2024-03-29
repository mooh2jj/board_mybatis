package com.example.board_springboot.controller;

import com.example.board_springboot.domain.ReplyVO;
import com.example.board_springboot.service.reply.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

    /**
     * 댓글 입력
     * @param reply 게시글에 적힌 댓글
     * @return 성공여부
     */
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/reply/insert")
    public ResponseEntity<String> insert(@RequestBody ReplyVO reply) {
        log.info("reply insert replyVO: {}", reply);
        int result = replyService.insert(reply);
        log.info("reply insert result: {}", result);
        return result == 1 ? new ResponseEntity<>("success", HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * ajax 방식
     * 게시글 당 댓글 리스트 가져오기
     * @param boardId 게시글 seq
     * @return 댓글 리스트
     */
    @GetMapping("/reply/list/{id}")
    public ResponseEntity<List<ReplyVO>> list(@PathVariable("id") Long boardId) {
        log.info("reply list boardId: {}", boardId);
        List<ReplyVO> replyList = replyService.getList(boardId);

        log.info("replyList: {}", replyList);

        return new ResponseEntity<>(replyList, HttpStatus.OK);
    }
}
