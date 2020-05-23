package com.doo9104.project.Board.domain.entity;

import com.doo9104.project.CommonEntity.IsUse;
import org.springframework.data.jpa.repository.JpaRepository;

/*
public interface Board_Dog_LikeRepository extends JpaRepository<Board_Dog_Like, Long> {

    Board_Dog_Like findByBoardIdAndUserId(Long boardId,String userId);
    Board_Dog_Like findByBoardIdAndUserIdAndIsUse(Long boardId, String userId, IsUse isUse);
}
*/

public interface Board_LikeRepository extends JpaRepository<Board_Like, Board_Like_Id> {
    Board_Like findAllByBoard_Id(Long boardId);
    Board_Like findByBoard_IdAndUser_Id(Long boardId, Long userId);
    Board_Like findByBoard_IdAndUser_IdAndIsUse(Long boardId, Long userId, IsUse isUse);

}
