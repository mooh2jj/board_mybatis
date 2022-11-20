package com.example.board_springboot.dto;

import lombok.Data;
import org.springframework.web.util.UriComponentsBuilder;

@Data
public class Criteria {
    /* 현재 페이지 번호 */
    private int pageNum;

    /* 페이지 표시 개수 */
    private int amount;

    /* mysql limit offset 용 (pageNum-1)*amount */
    private int startNum;

    /* 검색 타입 */
    private String type;

    /* 검색 키워드 */
    private String keyword;

    /* Criteria 생성자 */
    public Criteria(int pageNum, int amount) {
        this.pageNum = pageNum;
        this.amount = amount;
        this.startNum = (pageNum - 1) * amount;
    }

    /* Criteria 기본 생성자 */
    public Criteria() {
        this(1, 10);
    }

    /* 검색 타입 데이터 배열 변환 */
    public String[] getTypeArr() {
        return type == null ? new String[]{} : type.split("");
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
        this.startNum = (pageNum - 1) * this.amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
        this.startNum = (this.pageNum - 1) * amount;
    }

    // hidden 정보를 controller단에서도 주기위해 만든 uri 빌더 메서드
    public String getListLink() {
        UriComponentsBuilder builder = UriComponentsBuilder.fromPath("")
                .queryParam("pageNum", this.pageNum)
                .queryParam("amount", this.getAmount())
                .queryParam("type", this.getType())
                .queryParam("keyword", this.getKeyword());

        return builder.toUriString();

    }


}
