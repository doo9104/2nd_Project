package com.doo9104.project.service;

import com.doo9104.project.domain.entity.Comment_DogRepository;
import com.doo9104.project.domain.entity.IsUse;
import com.doo9104.project.domain.entity.like_CommentDog;
import com.doo9104.project.domain.entity.like_CommentDogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class Comment_DogService {
    private final like_CommentDogRepository likeCommentDogRepository;

    private final Comment_DogRepository comment_dogRepository;

    public void toggleLike(int boardid, int commentId, int memberId) {
        like_CommentDog preLike = likeCommentDogRepository.findByBoardIdAndCommentIdAndMemberId(boardid, commentId, memberId);
        like_CommentDog newOrModLike = null;

        if(preLike == null) { // 처음 추천이면 newLike 실행
             newOrModLike = this.newLike(boardid,commentId,memberId);
        } else { // 이미 추천했으면 modifyLike 실행
             newOrModLike = this.modifyLike(preLike);
        }
        likeCommentDogRepository.save(newOrModLike);
    }


    private like_CommentDog newLike(int boardid, int commentId, int memberId) {
        like_CommentDog newLike = new like_CommentDog();
        newLike.setBoardId(boardid);
        newLike.setCommentId(commentId);
        newLike.setMemberId(memberId);
        newLike.setIsUse(IsUse.Y);
        return newLike;
    }

    private like_CommentDog modifyLike(like_CommentDog preLike) {
        like_CommentDog modLike = null;
        IsUse inverseIsUse = preLike.getIsUse().inverse();
        modLike = preLike;
        modLike.setIsUse(inverseIsUse);
        return modLike;
    }


    public int findAllReceivedLikeCount(int boardId, int commentId) {
        System.out.println("서비스에서 boardId : " + boardId);
        System.out.println("서비스에서 commentId : " + commentId);

        int likeCount = comment_dogRepository.findAllReceivedLikeCount(boardId,commentId);
        return likeCount;
    }
}





