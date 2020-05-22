package com.doo9104.project.Board_Other.domain.entity;

import java.io.Serializable;

public class Board_Other_Like_Id implements Serializable {
    private Long boardOther;
    private Long user;

    public Board_Other_Like_Id(){}
    public Board_Other_Like_Id(Long boardOther, Long user) {
        super();
        this.boardOther = boardOther;
        this.user = user;
    }
}
