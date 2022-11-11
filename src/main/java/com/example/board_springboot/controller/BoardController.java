package com.example.board_springboot.controller;

import com.example.board_springboot.common.Criteria;
import com.example.board_springboot.common.PageDTO;
import com.example.board_springboot.domain.AttachVO;
import com.example.board_springboot.domain.BoardVO;
import com.example.board_springboot.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    /**
     * 게시판 목록
     * @param model
     * @param cri
     * @return 게시판 리스트
     */
    @GetMapping("/board/list")
    public String list(Model model, Criteria cri) {
        log.info("/board/list cri: {}", cri);

        int total = boardService.totalCount(cri);
        log.info("total: {}", total);

        PageDTO pageDTO = new PageDTO(cri, total);   // default
        model.addAttribute("list", boardService.getAll(cri));

        log.info("pageDTO: {}", pageDTO);
        model.addAttribute("pageMaker", pageDTO);

        return "/board/list";
    }

    /**
     * 게시글 상세보기
     * @param id
     * @param cri
     * @param model
     */
    @GetMapping("/board/get")
    public void get(
            @RequestParam("id") Long id,
            @ModelAttribute("cri") Criteria cri, Model model
    ) {
        BoardVO board = boardService.get(id);
        log.info("/board/get board: {}", board);
        // 조회수도 함께 증가
        boardService.updateHit(id);

        model.addAttribute("board", board);
    }

    /**
     * 게시글 수정페이지 이동
     * @param id
     * @param cri
     * @param model
     * @return modify 페이지
     */
    @GetMapping("/board/modify")
    public String modifyForm(
            @RequestParam("id") Long id,
            @ModelAttribute("cri") Criteria cri, Model model
    ) {
        BoardVO board = boardService.get(id);
        log.info("modify 페이지 이동 board: {}", board);
        model.addAttribute("board", board);

        return "/board/modify";
    }

    /**
     * 게시글 수정하기 작업
     * @param board
     * @param cri
     * @return 수정후 목록페이지 이동
     */
    @PostMapping("/board/modify")
    public String modify(
            @Valid BoardVO board,
            @ModelAttribute("cri") Criteria cri
    ) {
        log.info("/board/modify board: " + board);
        // 파일첨부 로직 추가
        if (board.getAttachList() != null) {
            board.getAttachList().forEach(
                    attach -> log.info("modify attach: {}", attach)
            );
        }
        boardService.modify(board);
        return "redirect:/board/list" + cri.getListLink();
    }

    /**
     * 게시글 삭제 작업
     * @param id
     * @param cri
     * @return 삭제후 목록페이지 이동
     */
    @PostMapping("/board/remove")
    public String remove(
            @RequestParam("id") Long id,
            Criteria cri
    ) {
        log.info("/board/remove id: {}", id);
        boardService.remove(id);
        return "redirect:/board/list" + cri.getListLink();
    }

    /**
     * 게시글 등록페이지 이동
     * @param cri
     * @param model
     * @return register 페이지 이동
     */
    @GetMapping("/board/register")
    public String write(
            @ModelAttribute("cri") Criteria cri,
            Model model
    ) {
        List<String> categoryList = boardService.getCategoryList();
        log.info("/board/register 페이지 이동 categoryList: {}", categoryList);
        model.addAttribute("categories", categoryList);
        return "board/register";
    }

    /**
     * 게시글 등록 작업
     * @param board
     * @return 등록 후 목록페이지 이동
     */
    @PostMapping("/board/register")
    public String register(@Valid BoardVO board) {
        log.info("/board/register board: {}", board);

        // 파일첨부 로직 추가
        if (board.getAttachList() != null) {
            board.getAttachList().forEach(
                    attach -> log.info("attach: {}", attach)
            );
        }
        log.info("board.getAttachList(): {}", board.getAttachList());
        boardService.register(board);

        return "redirect:/board/list";
    }

    /**
     * 게시글 당 파일첨부 리스트 가져오기
     * @param boardId
     * @return ResponseBody in 파일첨부 리스트
     */
    @GetMapping("/board/getAttachList")
    @ResponseBody
    public ResponseEntity<List<AttachVO>> getAttachList(Long boardId) {

        log.info("getAttachList boardId" + boardId);
        return new ResponseEntity<>(boardService.getAttachList(boardId), HttpStatus.OK);
    }

    /**
     * 게시글 당 비밀번호 가져오기
     * @param boardIdStr
     * @return ResponseBody in 비밀번호
     */
    @PostMapping("/board/getPassword")
    public ResponseEntity<String> getPassword(
            @RequestParam("boardId") String boardIdStr
    ) {
        log.info("/board/getPassword boardId: {}", boardIdStr);

        Long boardId = Long.parseLong(boardIdStr);
        String password = boardService.getPassword(boardId);
        log.info("getPassword, password {}", password);

        return new ResponseEntity<>(password, HttpStatus.OK);
    }
}
