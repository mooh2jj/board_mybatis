package com.example.board_springboot.controller;

import com.example.board_springboot.common.Criteria;
import com.example.board_springboot.common.PageDTO;
import com.example.board_springboot.domain.BoardVO;
import com.example.board_springboot.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/board/list")
    public String list(Model model, Criteria cri) {
        log.info("/board/list");
        log.info("cri: {}", cri);

        int total = boardService.totalCount(cri);
        log.info("total: {}", total);

        PageDTO pageDTO = new PageDTO(cri, total);   // default

        model.addAttribute("list", boardService.getAll(cri));

        log.info("pageDTO: {}", pageDTO);
        model.addAttribute("pageMaker", pageDTO);

        return "board/list";
    }

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

    @GetMapping("/board/modifyForm")
    public String modifyForm(
            @RequestParam("id") Long id,
            @ModelAttribute("cri") Criteria cri, Model model
    ) {
        BoardVO board = boardService.get(id);
        log.info("modifyForm 이동 board: {}", board);
        model.addAttribute("board", board);

        return "/board/modifyForm";
    }

    @PostMapping("/board/modify")
    public String modify(BoardVO board,
                         @ModelAttribute("cri") Criteria cri
    ) {
        log.info("/board/modify board: " + board);
        boardService.modify(board);
        return "redirect:/board/list" + cri.getListLink();
    }

    @PostMapping("/board/remove")
    public String remove(
            @RequestParam("id") Long id,
            Criteria cri
    ) {
        log.info("/board/remove id: {}", id);
        boardService.remove(id);
        return "redirect:/board/list" + cri.getListLink();
    }

    @GetMapping("/board/write")
    public String write(
            @ModelAttribute("cri") Criteria cri,
            Model model
    ) {
        List<String> categoryList = boardService.getCategoryList();
        log.info("/board/write 페이지 이동 categoryList: {}", categoryList);
        model.addAttribute("categories", categoryList);
        return "board/write";
    }

    @PostMapping("/board/register")
    public String register(BoardVO board) {
        log.info("/board/register board: {}", board);

        // 파일첨부 로직 추가
/*        if (board.getAttachList() != null) {
            board.getAttachList().forEach(
                    attach -> log.info("attach: {}", attach)
            );
        }*/
        boardService.register(board);

        return "redirect:/board/list";
    }
}
