package com.doo9104.project.Board_Other.dto;


import com.doo9104.project.Board_Other.domain.entity.Board_Other;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class OtherListResponseDto {
    private Long id;
    private String title;
    private String writer;
    private LocalDateTime modifiedDate;

    public OtherListResponseDto(Board_Other entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.writer = entity.getWriter();
        this.modifiedDate = entity.getModifiedDate();
    }
}
