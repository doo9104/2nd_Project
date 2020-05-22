package com.doo9104.project.Board_Other.domain.entity;

import com.doo9104.project.CommonEntity.IsUse;
import org.springframework.data.jpa.repository.JpaRepository;


public interface Board_Other_LikeRepository extends JpaRepository<Board_Other_Like, Board_Other_Like_Id> {
    Board_Other_Like findAllByBoardOther_Id(Long boardId);
    Board_Other_Like findByBoardOther_IdAndUser_Id(Long boardId, Long userId);
    Board_Other_Like findByBoardOther_IdAndUser_IdAndIsUse(Long boardId, Long userId, IsUse isUse);

}
