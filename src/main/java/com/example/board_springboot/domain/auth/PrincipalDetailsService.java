package com.example.board_springboot.domain.auth;

import com.example.board_springboot.common.exception.CustomException;
import com.example.board_springboot.common.exception.ErrorCode;
import com.example.board_springboot.domain.MemberVO;
import com.example.board_springboot.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final MemberMapper memberMapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("loadUserByUsername email: {}", email);
        MemberVO member = memberMapper.read(email);
        log.info("loadUserByUsername memberVO: {}", member);
        if (member == null) {
//            throw new UsernameNotFoundException(email);
            throw new CustomException(ErrorCode.NOT_FOUND_USER);
        }
        return new PrincipalDetail(member);
    }
}
