package com.doo9104.project.Board_Dog.dto;

import com.doo9104.project.Board_Dog.domain.entity.Board_Dog;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class DogListResponseDto {
    private Long id;
    private String title;
    private String writer;
    private LocalDateTime modifiedDate;

    public DogListResponseDto(Board_Dog entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.writer = entity.getWriter();
        this.modifiedDate = entity.getModifiedDate();
    }
}
