package com.doo9104.project.Board.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class BoardUpdateRequestDto {
    private String title;
    private String content;
    private String writer;

    @Builder
    public BoardUpdateRequestDto(String title, String content, String writer) {
        this.title = title;
        this.content = content;
        this.writer = writer;
    }
}
