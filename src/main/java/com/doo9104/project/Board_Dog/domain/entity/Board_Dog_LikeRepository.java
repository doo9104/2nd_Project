package com.doo9104.project.Board_Dog.domain.entity;

import com.doo9104.project.CommonEntity.IsUse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/*
public interface Board_Dog_LikeRepository extends JpaRepository<Board_Dog_Like, Long> {

    Board_Dog_Like findByBoardIdAndUserId(Long boardId,String userId);
    Board_Dog_Like findByBoardIdAndUserIdAndIsUse(Long boardId, String userId, IsUse isUse);
}
*/

public interface Board_Dog_LikeRepository extends JpaRepository<Board_Dog_Like, Board_Dog_Like_Id> {
    Board_Dog_Like findAllByBoardDog_Id(Long boardId);
    Board_Dog_Like findByBoardDog_IdAndUser_Id(Long boardId,Long userId);
    Board_Dog_Like findByBoardDog_IdAndUser_IdAndIsUse(Long boardId,Long userId, IsUse isUse);

}
