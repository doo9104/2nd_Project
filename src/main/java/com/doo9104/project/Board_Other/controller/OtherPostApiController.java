package com.doo9104.project.Board_Other.controller;

import com.doo9104.project.Board_Other.dto.OtherDto;
import com.doo9104.project.Board_Other.dto.OtherUpdateRequestDto;
import com.doo9104.project.Board_Other.service.Board_OtherService;
import com.doo9104.project.CommonEntity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/other/")
@RestController
public class OtherPostApiController {

    private final Board_OtherService boardOtherService;


    // 글 등록
    @PostMapping("/post")
    public Long post(@RequestBody OtherDto otherDto) {
        return boardOtherService.save(otherDto);
    }


    // 글 수정
    @PreAuthorize("#otherUpdateRequestDto.getWriter() == authentication.principal.username")
    @PutMapping("/post/{id}")
    public Long update(@PathVariable Long id, @RequestBody OtherUpdateRequestDto otherUpdateRequestDto) {
        return boardOtherService.update(id, otherUpdateRequestDto);
    }

    // 글 삭제
    @DeleteMapping("/post/{id}")
    public Long delete(@PathVariable Long id) {
        boardOtherService.delete(id);
        return id;
    }

    //@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    // 좋아요 클릭
    @PostMapping("/{id}/like")
    public int toggleLike(@PathVariable Long id, @RequestBody User user) {

        boardOtherService.toggleLike(id,user.getId());
        return boardOtherService.getLikeCount(id);
    }





}
