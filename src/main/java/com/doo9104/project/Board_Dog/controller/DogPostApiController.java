package com.doo9104.project.Board_Dog.controller;

import com.doo9104.project.Board_Dog.service.Board_DogService;
import com.doo9104.project.Board_Dog.dto.DogDto;
import com.doo9104.project.Board_Dog.dto.DogUpdateRequestDto;
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
@RequestMapping("/dog/")
@RestController
public class DogPostApiController {

    private final Board_DogService boardDogService;


    // 글 등록
    @PostMapping("/post")
    public Long post(@RequestBody DogDto dogDto) {
        return boardDogService.save(dogDto);
    }


    // 글 수정
    @PreAuthorize("#dogUpdateRequestDto.getWriter() == authentication.principal.username")
    @PutMapping("/post/{id}")
    public Long update(@PathVariable Long id, @RequestBody DogUpdateRequestDto dogUpdateRequestDto) {
        return boardDogService.update(id, dogUpdateRequestDto);
    }

    // 글 삭제
    @DeleteMapping("/post/{id}")
    public Long delete(@PathVariable Long id) {
        boardDogService.delete(id);
        return id;
    }

    //@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    // 좋아요 클릭
    @PostMapping("/{id}/like")
    public int toggleLike(@PathVariable Long id, @RequestBody User user) {

        boardDogService.toggleLike(id,user.getId());
        return boardDogService.getLikeCount(id);
    }





}
