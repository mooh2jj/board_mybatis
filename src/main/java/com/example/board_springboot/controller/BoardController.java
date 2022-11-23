package com.example.board_springboot.controller;

import com.example.board_springboot.domain.MemberVO;
import com.example.board_springboot.dto.Criteria;
import com.example.board_springboot.dto.PageDTO;
import com.example.board_springboot.domain.AttachVO;
import com.example.board_springboot.domain.BoardVO;
import com.example.board_springboot.dto.PasswordRequest;
import com.example.board_springboot.dto.UserInfo;
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
     * @param model 뷰단과 바인딩할 데이터
     * @param cri 검색+페이징 파라미터
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
     * @param id 게시글 seq
     * @param cri 검색+페이징 파라미터
     * @param model 뷰단 바인딩 객체
     */
    @GetMapping("/board/get")
    public void get(
            @RequestParam("id") Long id,
            @ModelAttribute("cri") Criteria cri, Model model
    ) {
        BoardVO board = boardService.get(id);
        log.info("/board/get board: {}", board);

        boardService.updateHit(id);

        model.addAttribute("board", board);
    }

    /**
     * 게시글 수정페이지 이동
     * @param id 게시글 seq
     * @param cri 검색+페이징 파라미터
     * @param model 뷰단 바인딩 객체
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
     * @param board 게시글
     * @param cri 검색+페이징 파라미터
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
     * @param id 게시글 seq
     * @param password 게시글 비밀번호
     * @param cri 검색+페이징 파라미터
     * @return 삭제후 목록페이지 이동
     */
    @PostMapping("/board/remove")
    public String remove(
            @RequestParam("id") Long id,
            @RequestParam("password") String password,
            Criteria cri
    ) {
        log.info("/board/remove id: {}", id);
        log.info("/board/remove password: {}", password);
        log.info("/board/remove cri: {}", cri);
        boardService.remove(id, password);
        return "redirect:/board/list" + cri.getListLink();
    }

    /**
     * 게시글 등록페이지 이동
     * @param cri 검색+페이징 파라미터
     * @param model 뷰단 바인딩 객체
     * @return register 페이지 이동
     */
    @GetMapping("/board/register")
    public String register(
            @ModelAttribute("cri") Criteria cri,
            Model model,
            @UserInfo MemberVO memberVO
    ) {
        List<String> categoryList = boardService.getCategoryList();
        log.info("/board/register 페이지 이동 categoryList: {}", categoryList);
        model.addAttribute("categories", categoryList);
        return "board/register";
    }

    /**
     * 게시글 등록 작업
     * @param board 게시글
     * @return 등록 후 목록페이지 이동
     */
    @PostMapping("/board/register")
    public String register(@Valid BoardVO board) {
        log.info("/board/register board: {}", board);

        // 파일첨부 로직 확인
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
     * @param boardId 게시글 seq
     * @return ResponseBody in 파일첨부 리스트
     */
    @GetMapping("/board/getAttachList")
    @ResponseBody
    public ResponseEntity<List<AttachVO>> getAttachList(Long boardId) {

        log.info("getAttachList boardId" + boardId);
        return new ResponseEntity<>(boardService.getAttachList(boardId), HttpStatus.OK);
    }

    /**
     * 게시글 당 비밀번호 정보 확인
     * @param request 게시글 seq, 입력한 비밀번호
     * @return 비밀번호 매칭 유무
     */
    @PostMapping("/board/checkPassword")
    public ResponseEntity<Object> checkPassword(
            @RequestBody PasswordRequest request
    ) {
        log.info("/board/checkPassword request: {}", request);

        boolean result = boardService.checkPassword(request);
        log.info("checkPassword, result {}", result);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
