package com.doo9104.project.web.controller.dogcontroller;

import com.doo9104.project.service.Board_DogService;
import com.doo9104.project.web.dto.DogPostResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class DisplayApiController {

    private final Board_DogService boardDogService;


    @GetMapping("/dog")
    public String dog_show(@PageableDefault(
            size = 6 ) Pageable pageable, Model model) {
        model.addAttribute("posts",boardDogService.findBoardList(pageable));

        return "/pets/dog/pet_dog";
    }

    @GetMapping("/dog/post")
    public String dog_post() {
        return "/pets/dog/post";
    }

    @GetMapping("/dog/{id}")
    public String dog_view(@PathVariable Long id,Model model) {
        DogPostResponseDto dto = boardDogService.findById(id);
        model.addAttribute("post",dto);
        return "/pets/dog/view";
    }

    @GetMapping("/dog/modify/{id}")
    public String updateView(@PathVariable Long id, Model model) {
        DogPostResponseDto dto = boardDogService.findById(id);

        model.addAttribute("post",dto);

        return "/pets/dog/modify";
    }

}
