package com.doo9104.project.Board.dto;

import com.doo9104.project.Board.domain.entity.Board;
import com.doo9104.project.Board.domain.entity.BoardType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class BoardDto {

    private Long id;
    private BoardType boardtype;
    private String writer;
    private String title;
    private String content;
    private String thumbnailsrc;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    @Builder
    public BoardDto(BoardType boardtype,String title, String content, String writer, String thumbnailsrc, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.boardtype = boardtype;
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.thumbnailsrc = thumbnailsrc;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }



    public Board toEntity() {
        return Board.builder()
                .boardtype(boardtype)
                .writer(writer)
                .title(title)
                .content(content)
                .thumbnailsrc(thumbnailsrc)
                .build();
    }



}
