package com.doo9104.project.Board.dto;

import com.doo9104.project.Board.domain.entity.Board;
import lombok.Getter;

@Getter
public class BoardPostResponseDto {

    private Long id;
    private String title;
    private String content;
    private String writer;
    private String thumbnailsrc;
    private int likeCount;

    public BoardPostResponseDto(Board entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.thumbnailsrc = entity.getThumbnailsrc();
        this.writer = entity.getWriter();
        this.likeCount = entity.getLikeCount();
    }
}
