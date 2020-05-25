package com.doo9104.project.Board.controller;

import com.doo9104.project.Board.domain.entity.Board;
import com.doo9104.project.Board.domain.entity.BoardType;
import com.doo9104.project.Board.service.Board_Service;
import com.doo9104.project.Board.dto.BoardDto;
import com.doo9104.project.Board.dto.BoardUpdateRequestDto;
import com.doo9104.project.CommonEntity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class BoardPostApiController {

    private final Board_Service board_service;


    // 글 등록
    @PostMapping("{type}/post")
    public Long post(@RequestBody BoardDto boardDto,@PathVariable BoardType type) {
        return board_service.save(boardDto);
    }


    // 글 수정
    @PreAuthorize("#boardUpdateRequestDto.getWriter() == authentication.principal.username")
    @PutMapping("{type}/post/{id}")
    public Long update(@PathVariable Long id, @RequestBody BoardUpdateRequestDto boardUpdateRequestDto,@PathVariable BoardType type) {
        return board_service.update(id, boardUpdateRequestDto);
    }

    // 글 삭제
    @DeleteMapping("{type}/post/{id}")
    public Long delete(@PathVariable Long id,@PathVariable BoardType type) {
        board_service.delete(id);
        return id;
    }

    //@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    // 좋아요 클릭
    @PostMapping("{type}/{id}/like")
    public int toggleLike(@PathVariable Long id, @RequestBody User user,@PathVariable BoardType type) {

        board_service.toggleLike(id,user.getId());
        return board_service.getLikeCount(id);
    }





}
