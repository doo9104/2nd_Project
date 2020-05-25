package com.doo9104.project.Board.domain.entity;

import com.doo9104.project.CommonEntity.TimeEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Board extends TimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Enumerated(EnumType.STRING)
    private BoardType boardtype;

    @Column(length = 10, nullable = false)
    private String writer;

    @Column(length = 100,nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT",nullable = false)
    private String content;

    @Column(columnDefinition = "TEXT")
    private String thumbnailsrc;

    @ColumnDefault("0")
    private int likeCount;

    @ColumnDefault("0")
    private int hitcount;

    @ColumnDefault("0")
    private int commentcount;





    @JsonIgnore
    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY, cascade = CascadeType.ALL) // 불필요하게 양쪽 테이블을 조회 하지 않도록 양쪽 모두 '지연 로딩' 방식 설정
    private List<Board_Comment> boardcomments;


    @Builder
    public Board(BoardType boardtype,String title, String content, String thumbnailsrc, String writer) {
        this.boardtype = boardtype;
        this.title = title;
        this.content = content;
        this.thumbnailsrc = thumbnailsrc;
        this.writer = writer;
    }

    // getter & setter

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void SetBoardId(Long id) {
        this.id = id;
    }


}

