package com.doo9104.project.domain.entity;

import org.springframework.data.jpa.repository.JpaRepository;


public interface Board_Dog_LikeRepository extends JpaRepository<Board_Dog_Like, Long> {

    Board_Dog_Like findByBoardIdAndUserId(Long boardId,String userId);
    Board_Dog_Like findByBoardIdAndUserIdAndIsUse(Long boardId,String userId,IsUse isUse);
}
