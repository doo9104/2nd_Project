package com.doo9104.project.Board_Other.domain.entity;


import com.doo9104.project.CommonEntity.TimeEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Getter
@NoArgsConstructor
@Entity
public class Comment_Other extends TimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1000,columnDefinition = "TEXT",nullable = false)
    private String content;

    @Column(length = 15, nullable = false)
    private String writer;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Board_Other board_other;



    // getter & setter

    public void SetCommentId(Board_Other board_other) {
        this.board_other = board_other;
    }

    public void SetCommentContent(String content) {
        this.content = content;
    }


}
