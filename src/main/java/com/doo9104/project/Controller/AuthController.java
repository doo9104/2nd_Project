package com.doo9104.project.Controller;


import com.doo9104.project.CommonEntity.IsUse;
import com.doo9104.project.CommonEntity.User;
import com.doo9104.project.CommonEntity.UserRepository;
import com.doo9104.project.Service.EmailService;
import com.doo9104.project.Util.JwtUtil;
import com.doo9104.project.Util.TempKey;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
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
    private final EmailService emailService;



    // 회원가입
    @PostMapping("/join")
    public String join(@RequestBody Map<String, String> user) {
        // 받아온 이메일로 인증메일을 보낸다
        String mailSubject = "PSMP - 회원가입 인증 이메일";
        String mailReceiver = user.get("email");
        String authkey = new TempKey().getKey(50,false);
        String mailContent = new StringBuffer("<h1>메일인증</h1><br>")
                .append("<a href='http://localhost:8080/emailConfirm?email=")
                .append(user.get("email"))
                .append("&userid=")
                .append(user.get("userid"))
                .append("&authkey=")
                .append(authkey)
                .append("' target='_blank'>이메일 인증</a>")
                .toString();

        try {
            emailService.sendMail(mailSubject,mailReceiver,mailContent);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return userRepository.save(User.builder()
                .email(user.get("email"))
                .userid(user.get("userid"))
                .nickname(user.get("nickname"))
                .authkey(authkey)
                .isUse(IsUse.N) // 이메일 인증 전에는 N으로
                .password(passwordEncoder.encode(user.get("password")))
                .roles(Collections.singletonList("ROLE_USER")) // 최초 가입시 USER 로 설정
                .build()).getUserid();
    }

    @GetMapping("/emailConfirm")
    public void emailConfirm(@RequestParam(value = "email")String email,
                             @RequestParam(value = "userid")String userid,
                             @RequestParam(value = "authkey")String authkey) {
        //아이디&이메일로 확인후
        // authkey 일치하면 isUse Y로
        User member = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 이메일 입니다."));

        if(authkey.equals(member.getAuthkey())) {
            // isUse를 Y로 변경하고 authkey를 비운다
            member.accountActive(member.getIsUse(),authkey);
            userRepository.save(member);
        } else {
            System.out.println("다름");
        }

    }


    // 로그인
    @PostMapping("/loginprocess")
    public String login(@RequestBody Map<String, String> user, HttpServletRequest request,HttpServletResponse response) {
            //String , ResponseEntity<?>

        User member = userRepository.findByUserid(user.get("userid"))
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 ID 입니다."));

        if(member.getIsUse().equals(IsUse.N)) { return "NotAuth"; }
        else if (!passwordEncoder.matches(user.get("password"), member.getPassword())) {
            return "WrongInfo";
        }

        String token = jwtUtil.createToken(member.getUserid(), member.getRoles());
        response.setHeader("X-AUTH-TOKEN", token);

        Cookie setCookie = new Cookie("X-AUTH-TOKEN", token); // 쿠키 생성
        setCookie.setMaxAge(60*60*24); // 기간을 하루로 지정
        response.addCookie(setCookie);
        return user.get("prevpage");
        /*return ResponseEntity.ok(jwtUtil.createToken(member.getUserid(), member.getRoles()));*/
    }


}
