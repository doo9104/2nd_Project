package com.doo9104.project.domain.entity;


import org.springframework.data.jpa.repository.JpaRepository;

public interface like_CommentDogRepository extends JpaRepository<like_CommentDog, Integer> {
    like_CommentDog findByBoardIdAndCommentIdAndMemberId(int boardId,int commentId,int memberId);
    like_CommentDog findByBoardIdAndCommentIdAndMemberIdAndIsUse(int boardId, int commentId, int memberId, IsUse isUse);
}
