package com.doo9104.project.Board.domain.entity;


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
public class Board_Comment extends TimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1000,columnDefinition = "TEXT",nullable = false)
    private String content;

    @Column(length = 15, nullable = false)
    private String writer;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;



    // getter & setter

    public void SetCommentId(Board board) {
        this.board = board;
    }

    public void SetCommentContent(String content) {
        this.content = content;
    }


}
