package com.doo9104.project.web.controller;

import com.doo9104.project.service.Board_DogService;
import com.doo9104.project.web.dto.DogDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final Board_DogService boardDogService;

    @GetMapping("/")
    public String index() {
        return "index";
    }



}
