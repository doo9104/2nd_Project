package com.doo9104.project.Board_Dog.dto;

import com.doo9104.project.Board_Dog.domain.entity.Board_Dog;
import lombok.Getter;

@Getter
public class DogPostResponseDto {

    private Long id;
    private String title;
    private String content;
    private String writer;
    private String thumbnailsrc;
    private int likeCount;

    public DogPostResponseDto(Board_Dog entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.thumbnailsrc = entity.getThumbnailsrc();
        this.writer = entity.getWriter();
        this.likeCount = entity.getLikeCount();
    }
}
