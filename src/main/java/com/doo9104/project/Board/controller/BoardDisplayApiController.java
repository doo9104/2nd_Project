package com.doo9104.project.Board.controller;

import com.doo9104.project.Board.domain.entity.Board;
import com.doo9104.project.Board.service.Board_Service;
import com.doo9104.project.Board.dto.BoardPostResponseDto;
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
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequiredArgsConstructor
public class BoardDisplayApiController {

    private final Board_Service board_service;


    @GetMapping("/{type}")
    public String showList(@PageableDefault(
            size = 6 ) Pageable pageable, Model model, @PathVariable Board.BoardType type) {
        model.addAttribute("posts",board_service.findBoardList(pageable,type));
        return "/board/list";
    }

    //@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/{type}/post")
    public String dog_post(@PathVariable Board.BoardType type) {
        return "/board/post";
    }

    // 게시판에 맞춰서 쿠키 이름 재설정
    @GetMapping("/{type}/{id}")
    public String dog_view(@PathVariable Long id, @PathVariable Board.BoardType type,Model model, HttpServletResponse response, HttpServletRequest request) {

        Cookie cookies[] = request.getCookies();
        Map mapCookie = new HashMap();
        if(request.getCookies() != null){
            for (int i = 0; i < cookies.length; i++) {
                Cookie obj = cookies[i];
                mapCookie.put(obj.getName(),obj.getValue());
            }
        }

        String cookie_hit_count = (String) mapCookie.get(type + "_hit");
        String new_cookie_hit_count = "|" + id;

        if ( StringUtils.indexOfIgnoreCase(cookie_hit_count, new_cookie_hit_count) == -1 ) {
            Cookie cookie = new Cookie(type+"_hit", cookie_hit_count + new_cookie_hit_count);
            cookie.setMaxAge(60*60*24); // 초단위
            response.addCookie(cookie);

            board_service.HitCountUp(id);
        }

        BoardPostResponseDto dto = board_service.findById(id);
        model.addAttribute("post",dto);
        return "/board/view";
    }

    @GetMapping("/{type}/modify/{id}")
    public String updateView(@PathVariable Long id, @PathVariable Board.BoardType type, Model model) {
        BoardPostResponseDto dto = board_service.findById(id);

        model.addAttribute("post",dto);

        return "/board/modify";
    }

    @GetMapping("/{type}/search")
    public String searchBoardList(@RequestParam(value = "keyword") String keyword,
                                  @RequestParam(value = "type") String searchtype,
                                  @PageableDefault(size = 6 ) Pageable pageable,
                                  @PathVariable Board.BoardType type,
                                  Model model) {
        model.addAttribute("posts",board_service.searchBoardList(pageable,searchtype,keyword,type));
        model.addAttribute("searchtype",searchtype);
        model.addAttribute("keyword",keyword);

        return "/board/list";
    }


}
