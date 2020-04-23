package com.doo9104.project.web.dto;

import com.doo9104.project.domain.entity.Board_Dog;
import lombok.Getter;

@Getter
public class DogPostResponseDto {

    private Long id;
    private String title;
    private String content;
    private String writer;

    public DogPostResponseDto(Board_Dog entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.writer = entity.getWriter();
    }
}
