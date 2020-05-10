package com.doo9104.project.web.controller;


import com.doo9104.project.domain.entity.User;
import com.doo9104.project.domain.entity.UserRepository;
import com.doo9104.project.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Map;


@RequiredArgsConstructor
@RestController
public class AuthController {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;


    // 회원가입
    @PostMapping("/join")
    public String join(@RequestBody Map<String, String> user) {
        return userRepository.save(User.builder()
                .email(user.get("email"))
                .userid(user.get("userid"))
                .nickname(user.get("nickname"))
                .password(passwordEncoder.encode(user.get("password")))
                .roles(Collections.singletonList("ROLE_USER")) // 최초 가입시 USER 로 설정
                .build()).getUserid();
    }

    // 로그인
    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> user, HttpServletResponse response) {
            //String , ResponseEntity<?>
        User member = userRepository.findByUserid(user.get("userid"))
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 ID 입니다."));

        if (!passwordEncoder.matches(user.get("password"), member.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

        String token = jwtUtil.createToken(member.getUserid(), member.getRoles());
        response.setHeader("X-AUTH-TOKEN", token);

        Cookie setCookie = new Cookie("X-AUTH-TOKEN", token); // 쿠키 이름을 name으로 생성
        setCookie.setMaxAge(60*60*24); // 기간을 하루로 지정
        response.addCookie(setCookie);

        return token;
        /*return ResponseEntity.ok(jwtUtil.createToken(member.getUserid(), member.getRoles()));*/
    }


}
