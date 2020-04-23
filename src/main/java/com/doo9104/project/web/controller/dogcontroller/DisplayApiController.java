package com.doo9104.project.web.controller.dogcontroller;

import com.doo9104.project.service.Board_DogService;
import com.doo9104.project.web.dto.DogPostResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class DisplayApiController {

    private final Board_DogService boardDogService;


    @GetMapping("/dog")
    public String dog_show(Model model) {
        model.addAttribute("posts",boardDogService.findAllDesc());
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
