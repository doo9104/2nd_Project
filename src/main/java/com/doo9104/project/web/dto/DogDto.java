package com.doo9104.project.web.dto;

import com.doo9104.project.domain.entity.Board_Dog;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class DogDto {

    private Long id;
    private String writer;
    private String title;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    @Builder
    public DogDto(String title, String content, String writer, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }


    public Board_Dog toEntity() {
        return Board_Dog.builder()
                .writer(writer)
                .title(title)
                .content(content)
                .build();
    }



}
