package com.doo9104.project.Board_Other.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class OtherUpdateRequestDto {
    private String title;
    private String content;
    private String writer;

    @Builder
    public OtherUpdateRequestDto(String title, String content, String writer) {
        this.title = title;
        this.content = content;
        this.writer = writer;
    }
}
