package com.example.board_springboot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .authorizeHttpRequests()
                .antMatchers("/board/list", "/member/**", "/resources/**" )
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
//                .httpBasic();
                .formLogin()
                .loginPage("/member/loginForm")
                .loginProcessingUrl("/member/login")
                .defaultSuccessUrl("/board/list");

    }
}
