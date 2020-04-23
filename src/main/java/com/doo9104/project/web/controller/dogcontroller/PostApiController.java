package com.doo9104.project.web.controller.dogcontroller;

import com.doo9104.project.service.Board_DogService;
import com.doo9104.project.web.dto.DogDto;
import com.doo9104.project.web.dto.DogPostResponseDto;
import com.doo9104.project.web.dto.DogUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/dog/")
@RestController
public class PostApiController {

    private final Board_DogService boardDogService;


    @PostMapping("/post")
    public Long post(@RequestBody DogDto dogDto) {
        return boardDogService.save(dogDto);
    }

    @PutMapping("/post/{id}")
    public Long update(@PathVariable Long id, @RequestBody DogUpdateRequestDto dogUpdateRequestDto) {
        return boardDogService.update(id, dogUpdateRequestDto);
    }

    @DeleteMapping("/post/{id}")
    public Long delete(@PathVariable Long id) {
        boardDogService.delete(id);
        return id;
    }


}
