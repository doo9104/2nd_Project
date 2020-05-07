package com.doo9104.project.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Board_Dog extends TimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 10, nullable = false)
    private String writer;

    @Column(length = 100,nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT",nullable = false)
    private String content;

    @JsonIgnore
    @OneToMany(mappedBy = "board_dog", fetch = FetchType.LAZY) // 불필요하게 양쪽 테이블을 조회 하지 않도록 양쪽 모두 '지연 로딩' 방식 설정
    private List<Comment_Dog> comments;


    @Builder
    public Board_Dog(String title, String content, String writer) {
        this.title = title;
        this.content = content;
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

