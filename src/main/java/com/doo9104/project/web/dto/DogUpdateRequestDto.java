package com.doo9104.project.web.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class DogUpdateRequestDto {
    private String title;
    private String content;

    @Builder
    public DogUpdateRequestDto(String title,String content) {
        this.title = title;
        this.content = content;
    }
}
