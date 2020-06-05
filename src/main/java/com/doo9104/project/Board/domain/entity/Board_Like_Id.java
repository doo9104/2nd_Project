package com.doo9104.project.Board.domain.entity;

import java.io.Serializable;

public class Board_Like_Id implements Serializable {
    private Long board;
    private Long user;

    public Board_Like_Id(){}
    public Board_Like_Id(Long board, Long user) {
        super();
        this.board = board;
        this.user = user;
    }
}
