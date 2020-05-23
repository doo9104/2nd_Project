package com.doo9104.project.Board.dto;

import com.doo9104.project.Board.domain.entity.Board;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BoardListResponseDto {
    private Long id;
    private String title;
    private String writer;
    private LocalDateTime modifiedDate;

    public BoardListResponseDto(Board entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.writer = entity.getWriter();
        this.modifiedDate = entity.getModifiedDate();
    }
}
