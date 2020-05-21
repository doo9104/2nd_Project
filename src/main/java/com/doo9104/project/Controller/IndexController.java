package com.doo9104.project.Controller;

import com.doo9104.project.Board_Dog.service.Board_DogService;
import com.doo9104.project.Util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;


@RequiredArgsConstructor
@Controller
public class IndexController {

    private final Board_DogService boardDogService;
    private final JwtUtil jwtUtil;

    @GetMapping("/")
    public String index(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String curUser = authentication.getName();
        if(curUser.equals("anonymousUser")) {
            System.out.println("익명사용자입니다.");
            model.addAttribute("curUser", false);

        } else {
        System.out.println("현재 사용자 : " + curUser);
        model.addAttribute("curUser", curUser);
        }
        return "index";
    }

    @GetMapping("/login")
    public String login(HttpServletRequest request,Model model) {
        model.addAttribute("prevPage",request.getHeader("Referer"));
        return "login";
    }

    @GetMapping("/join")
    public String join() { return "join"; }



}
