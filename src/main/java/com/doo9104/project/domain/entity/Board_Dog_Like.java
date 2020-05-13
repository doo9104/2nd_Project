package com.doo9104.project.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Board_Dog_Like{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardLikeId;

    private Long boardId;

    private String userId;

    @Enumerated(EnumType.STRING)
    private IsUse isUse;

}
