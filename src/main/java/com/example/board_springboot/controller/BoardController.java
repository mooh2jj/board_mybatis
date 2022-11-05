package com.example.board_springboot.controller;

import com.example.board_springboot.common.Criteria;
import com.example.board_springboot.common.PageDTO;
import com.example.board_springboot.domain.BoardVO;
import com.example.board_springboot.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/list")
    public String list(Model model, Criteria cri) {
        log.info("/list");
        log.info("cri: {}", cri);

        int total = boardService.totalCount(cri);
        log.info("total: {}", total);

        PageDTO pageDTO = new PageDTO(cri, total);   // default

        model.addAttribute("list", boardService.getAll(cri));

        log.info("pageDTO: {}", pageDTO);
        model.addAttribute("pageMaker", pageDTO);

        return "board/list";
    }

    @GetMapping("/get")
    public void get(
            @RequestParam("id") Long id,
            @ModelAttribute("cri") Criteria cri, Model model
    ) {
        log.info("/get");
        model.addAttribute("updateHit", boardService.updateHit(id));
        model.addAttribute("board", boardService.get(id));
    }

    @PostMapping("/modify")
    public String modify(BoardVO board,
                         @ModelAttribute("cri") Criteria cri
    ) {
        log.info("/modify board: " + board);
        boardService.modify(board);
        return "redirect:/board/list" + cri.getListLink();
    }

    @PostMapping("/remove")
    public String remove(
            @RequestParam("id") Long id,
            Criteria cri
    ) {
        log.info("/remove id: {}", id);
        boardService.remove(id);
        return "redirect:/board/list" + cri.getListLink();
    }

    @GetMapping("/write")
    public String write(@ModelAttribute("cri") Criteria cri) {
        log.info("write 페이지 이동");
        return "board/write";
    }

    @PostMapping("/register")
    public String register(BoardVO board) {
        log.info("register board: {}", board);

        // 파일첨부 로직 추가
        if (board.getAttachList() != null) {
            board.getAttachList().forEach(
                    attach -> log.info("attach: {}", attach)
            );
        }
        boardService.register(board);

        return "redirect:/board/list";
    }
}
