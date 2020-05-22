package com.doo9104.project.Board_Other.dto;

import com.doo9104.project.Board_Other.domain.entity.Board_Other;
import lombok.Getter;

@Getter
public class OtherPostResponseDto {

    private Long id;
    private String title;
    private String content;
    private String writer;
    private String thumbnailsrc;
    private int likeCount;

    public OtherPostResponseDto(Board_Other entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.thumbnailsrc = entity.getThumbnailsrc();
        this.writer = entity.getWriter();
        this.likeCount = entity.getLikeCount();
    }
}
