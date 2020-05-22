package com.doo9104.project.Board_Other.controller;

import com.doo9104.project.Board_Other.dto.OtherPostResponseDto;
import com.doo9104.project.Board_Other.service.Board_OtherService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequiredArgsConstructor
public class OtherDisplayApiController {

    private final Board_OtherService boardOtherService;


    @GetMapping("/other")
    public String other_show(@PageableDefault(
            size = 6 ) Pageable pageable, Model model) {
        model.addAttribute("posts",boardOtherService.findBoardList(pageable));

        return "/pets/other/pet_other";
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/other/post")
    public String other_post() {
        return "/pets/other/post";
    }

    @GetMapping("/other/{id}")
    public String other_view(@PathVariable Long id, Model model, HttpServletResponse response, HttpServletRequest request) {

        Cookie cookies[] = request.getCookies();
        Map mapCookie = new HashMap();
        if(request.getCookies() != null){
            for (int i = 0; i < cookies.length; i++) {
                Cookie obj = cookies[i];
                mapCookie.put(obj.getName(),obj.getValue());
            }
        }

        String cookie_hit_count = (String) mapCookie.get("hit_count");
        String new_cookie_hit_count = "|" + id;

        if ( StringUtils.indexOfIgnoreCase(cookie_hit_count, new_cookie_hit_count) == -1 ) {
            Cookie cookie = new Cookie("hit_count", cookie_hit_count + new_cookie_hit_count);
            cookie.setMaxAge(60*60*24); // 초단위
            response.addCookie(cookie);

            boardOtherService.HitCountUp(id);
        }

        OtherPostResponseDto dto = boardOtherService.findById(id);
        model.addAttribute("post",dto);
        return "/pets/other/view";
    }

    @GetMapping("/other/modify/{id}")
    public String updateView(@PathVariable Long id, Model model) {
        OtherPostResponseDto dto = boardOtherService.findById(id);

        model.addAttribute("post",dto);

        return "/pets/other/modify";
    }

    @GetMapping("/other/search")
    public String searchBoardList(@RequestParam(value = "keyword") String keyword,
                                  @RequestParam(value = "type") String type,
                                  @PageableDefault(size = 6 ) Pageable pageable,
                                  Model model) {
        model.addAttribute("posts",boardOtherService.searchBoardList(pageable,type,keyword));
        model.addAttribute("type",type);
        model.addAttribute("keyword",keyword);

        return "/pets/other/pet_other";
    }


}
