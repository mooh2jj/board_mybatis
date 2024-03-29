package com.example.board_springboot.config;

import com.example.board_springboot.domain.auth.PrincipalDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PrincipalDetailsService principalDetailsService;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.httpBasic().disable();

        http
                .csrf().disable()
                .authorizeHttpRequests()
                .antMatchers( "/board/list", "/member/**", "/resources/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                // ajax 처리때문에 주석처리
//                .formLogin()
//                .loginPage("/member/loginForm")
//                .loginProcessingUrl("/member/login")
//                .defaultSuccessUrl("/board/list")
//                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/member/customLogout"))
                .logoutSuccessUrl("/member/loginForm")
                .invalidateHttpSession(true) // 로그아웃 이후 세션 전체 삭제 여부
                .deleteCookies("JSESSIONID") // 세션과 쿠키를 지우는
        ;
        http.rememberMe()
                .tokenValiditySeconds(3600)
                .userDetailsService(principalDetailsService)
        ;

    }
/*
    // static 폴더에 있어야 기능, 지금 정적 파일소스들은 resouces내 기반되어 있어 사용하지 못함.
    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }*/

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(principalDetailsService)
                .passwordEncoder(passwordEncoder());
    }
}
