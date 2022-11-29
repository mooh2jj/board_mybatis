package com.example.board_springboot.domain;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.List;

@Data
public class BoardVO {

    private Long id;

    @NotBlank
    private String category;
    @NotBlank
/*    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{4,15}$",
            message= "비밀번호는 4글자 이상, 16글자 미만 그리고 영문/숫자/특수문자 포함이어야 합니다.")*/
    private String password;

    @NotBlank
/*    @Size(min = 3, max = 4, message = "작성자은 3글자 이상, 5글자 미만이어야 합니다.")*/
    private String writer;

    @NotBlank
    @Size(min = 4, max = 99, message = "제목은 4글자 이상, 100글자 미만이어야 합니다.")
    private String title;

    @NotBlank
    @Size(min = 4, max = 1999, message = "내용은 4글자 이상, 2000글자 미만이어야 합니다.")
    private String content;


    private int hit;
    private boolean fileYN;

    /**
     * issue: Timestamp -> LocalDateTime : log4j 로깅으로 오류뜸.
     */
    private Timestamp createdAt;
    private Timestamp updatedAt;

    private List<AttachVO> attachList;

}
