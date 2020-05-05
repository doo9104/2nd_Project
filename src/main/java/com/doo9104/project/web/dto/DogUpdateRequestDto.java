package com.doo9104.project.web.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class DogUpdateRequestDto {
    private String title;
    private String content;
    private String writer;

    @Builder
    public DogUpdateRequestDto(String title,String content,String writer) {
        this.title = title;
        this.content = content;
        this.writer = writer;
    }
}
