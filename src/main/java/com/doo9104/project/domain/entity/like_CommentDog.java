package com.doo9104.project.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class like_CommentDog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int DogCommentLikeId;

    private int boardId;

    private int commentId;

    private int memberId;

    @Enumerated(EnumType.STRING)
    private IsUse isUse;
}
