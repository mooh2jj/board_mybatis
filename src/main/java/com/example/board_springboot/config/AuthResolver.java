package com.example.board_springboot.config;

import com.example.board_springboot.common.exception.CustomException;
import com.example.board_springboot.common.exception.ErrorCode;
import com.example.board_springboot.domain.MemberVO;
import com.example.board_springboot.dto.UserInfo;
import com.example.board_springboot.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthResolver implements HandlerMethodArgumentResolver {

    private final MemberService memberService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(UserInfo.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        UserInfo userInfo = parameter.getParameterAnnotation(UserInfo.class);

        String name = request.getParameter("name");
        String password = request.getParameter("password");

        MemberVO memberVO = new MemberVO();
        memberVO.setName(name);
        memberVO.setPassword(password);
        MemberVO member = memberService.getMember(memberVO);
        log.info("resolveArgument member: {}", member);

        if (userInfo == null || member == null) {
            throw new CustomException(ErrorCode.NO_FOUND_ENTITY);
        }

        return member;
    }
}
