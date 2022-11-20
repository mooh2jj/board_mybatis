package com.example.board_springboot.dto;

import lombok.Data;

@Data
public class PageDTO {

    private int total;            // 총 카운트=게시글 총 갯수
    private int startPage;        // 페이지에 보여질 시작페이지번호
    private int endPage;          // 페이지에 보여질 끝 페이지번호
    private int realEnd;          // 제일 마지막 페이지번호
    private boolean prev, next;   // 페이지 이전, 다음 있을지 불린값

    /* 현재페이지 번호(pageNum), 행 표시 수(amount), 검색 키워드(keyword), 검색 종류(type)*/
    private Criteria cri;

    // 페이지 계산식
    public PageDTO(Criteria cri, int total) {
        this.cri = cri;
        this.total = total;

        /* 페이지 끝 번호 */
        this.endPage = (int) Math.ceil(cri.getPageNum()/ 10.0) * 10;
        /* 페이지 시작 번호 */
        this.startPage = this.endPage - 9;

        /* 전체 마지막 페이지 번호 */
        this.realEnd = (int) Math.ceil((double) this.total / (double) cri.getAmount());

        /* 페이지 끝 번호 유효성 체크 */
        if(this.realEnd < endPage) {
            this.endPage = this.realEnd;
        }

        /* 이전 버튼 값 초기화 */
        this.prev = this.startPage > 1;
        /* 다음 버튼 값 초기화 */
        this.next = this.endPage < this.realEnd;

    }
}
