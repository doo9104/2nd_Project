package com.doo9104.project.Board_Cat.controller;


import com.doo9104.project.Board_Cat.dto.CatDto;
import com.doo9104.project.Board_Cat.dto.CatUpdateRequestDto;
import com.doo9104.project.Board_Cat.service.Board_CatService;
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
@RequestMapping("/cat/")
@RestController
public class CatPostApiController {

    private final Board_CatService boardCatService;


    // 글 등록
    @PostMapping("/post")
    public Long post(@RequestBody CatDto catDto) {
        return boardCatService.save(catDto);
    }


    // 글 수정
    @PreAuthorize("#catUpdateRequestDto.getWriter() == authentication.principal.username")
    @PutMapping("/post/{id}")
    public Long update(@PathVariable Long id, @RequestBody CatUpdateRequestDto catUpdateRequestDto) {
        return boardCatService.update(id, catUpdateRequestDto);
    }

    // 글 삭제
    @DeleteMapping("/post/{id}")
    public Long delete(@PathVariable Long id) {
        boardCatService.delete(id);
        return id;
    }

    //@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    // 좋아요 클릭
    @PostMapping("/{id}/like")
    public int toggleLike(@PathVariable Long id, @RequestBody User user) {

        boardCatService.toggleLike(id,user.getId());
        return boardCatService.getLikeCount(id);
    }


}
