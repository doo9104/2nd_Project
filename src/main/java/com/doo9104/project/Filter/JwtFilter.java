package com.doo9104.project.Filter;


import com.doo9104.project.Util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;


@RequiredArgsConstructor
public class JwtFilter extends GenericFilterBean {

    private final JwtUtil jwtUtil;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        // 헤더에서 JWT 를 받아옵니다.
        String token = jwtUtil.resolveToken((HttpServletRequest) request);

        // 헤더에없으면 쿠키에서
        if(token == null) {
            Cookie[] cookies = ((HttpServletRequest) request).getCookies();
            if(cookies!=null){ // cookie에서 토큰 파싱
                token = Arrays.stream(cookies).filter(cookie -> cookie.getName().equals("X-AUTH-TOKEN")).findFirst().map(Cookie::getValue).orElse(null);
            }
        }

        // 유효한 토큰인지 확인합니다.
        if (token != null && jwtUtil.validateToken(token)) {
            // 토큰이 유효하면 토큰으로부터 유저 정보를 받아옵니다.
            Authentication authentication = jwtUtil.getAuthentication(token);
            // SecurityContext 에 Authentication 객체를 저장합니다.
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }





}