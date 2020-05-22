package com.doo9104.project.Board_Other.dto;

import com.doo9104.project.Board_Other.domain.entity.Board_Other;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class OtherDto {

    private Long id;
    private String writer;
    private String title;
    private String content;
    private String thumbnailsrc;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    @Builder
    public OtherDto(String title, String content, String writer, String thumbnailsrc, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.thumbnailsrc = thumbnailsrc;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }



    public Board_Other toEntity() {
        return Board_Other.builder()
                .writer(writer)
                .title(title)
                .content(content)
                .thumbnailsrc(thumbnailsrc)
                .build();
    }



}
