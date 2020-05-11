package com.doo9104.project.domain.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Getter
@NoArgsConstructor
@Entity
public class Comment_Dog extends TimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1000,columnDefinition = "TEXT",nullable = false)
    private String content;

    @Column(length = 15, nullable = false)
    private String writer;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Board_Dog board_dog;

    @Transient
    private int likecount;


    // getter & setter

    public void SetCommentId(Board_Dog board_dog) {
        this.board_dog = board_dog;
    }

    public void SetCommentContent(String content) {
        this.content = content;
    }

}
