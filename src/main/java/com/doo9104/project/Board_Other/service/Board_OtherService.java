package com.doo9104.project.Board_Other.service;


import com.doo9104.project.Board_Other.domain.entity.Board_Other;
import com.doo9104.project.Board_Other.domain.entity.Board_OtherRepository;
import com.doo9104.project.Board_Other.domain.entity.Board_Other_Like;
import com.doo9104.project.Board_Other.domain.entity.Board_Other_LikeRepository;
import com.doo9104.project.Board_Other.domain.entity.Comment_OtherRepository;
import com.doo9104.project.Board_Other.dto.OtherDto;
import com.doo9104.project.Board_Other.dto.OtherListResponseDto;
import com.doo9104.project.Board_Other.dto.OtherPostResponseDto;
import com.doo9104.project.Board_Other.dto.OtherUpdateRequestDto;
import com.doo9104.project.CommonEntity.IsUse;
import com.doo9104.project.CommonEntity.User;
import com.doo9104.project.CommonEntity.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.*;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class Board_OtherService {

    private final Board_OtherRepository boardOtherRepository;
    private final Comment_OtherRepository commentOtherRepository;
    private final UserRepository userRepository;

    private final Board_Other_LikeRepository boardOtherLikeRepository;




    @Transactional
    public Long save(OtherDto otherDto) {
        return boardOtherRepository.save(otherDto.toEntity()).getId();
    }


    public Page<Board_Other> findBoardList(Pageable pageable) {
        pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1, pageable.getPageSize(),new Sort(Sort.Direction.DESC, "id"));

        return boardOtherRepository.findAll(pageable);
    }


    @Transactional
    public Long update(Long id, OtherUpdateRequestDto otherUpdateRequestDto) {
        Board_Other boardOther = boardOtherRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(("해당 게시물이 없습니다.")));
        boardOther.update(otherUpdateRequestDto.getTitle(), otherUpdateRequestDto.getContent());
        return id;
    }


    public OtherPostResponseDto findById(Long id) {
        Board_Other entity = boardOtherRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));

        return new OtherPostResponseDto(entity);
    }

    @Transactional(readOnly = true) // 트랜잭션 범위는 유효하되 조회기능만 남겨둠(조회속도 향상)
    public List<OtherListResponseDto> findAllDesc() {
        return boardOtherRepository.findAllDesc().stream()
                .map(OtherListResponseDto::new)
                .collect(Collectors.toList());
    }


    @Transactional
    public void delete(Long id) {
        Board_Other boardOther = boardOtherRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물이 없습니다."));

        boardOtherRepository.delete(boardOther);
    }



      public void toggleLike(Long boardId, Long userId) {
        Board_Other_Like preLike = boardOtherLikeRepository.findByBoardOther_IdAndUser_Id(boardId,userId);
        Board_Other_Like newOrModLike = null;
        if(preLike == null){ // 처음 추천일때
            newOrModLike = this.newLike(boardId,userId);
        }else{ // 이미 추천했을때
            newOrModLike = this.modifyLike(preLike);
        }
        boardOtherLikeRepository.save(newOrModLike);
    }

    private Board_Other_Like newLike(Long boardId, Long userId){
        Board_Other_Like newLike = new Board_Other_Like();
        Board_Other board_other = new Board_Other();
        final User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다."));

        board_other.SetBoardId(boardId);

        newLike.setBoardOther(board_other);
        newLike.setUser(user);
        newLike.setIsUse(IsUse.Y);
        boardOtherRepository.LikeUp(boardId);

        return newLike;
    }

    private Board_Other_Like modifyLike(Board_Other_Like preLike){
        Board_Other_Like modLike = null;
        if(preLike.getIsUse().equals(IsUse.Y)) {
            boardOtherRepository.LikeDown(preLike.getBoardOther().getId());
        } else {
            boardOtherRepository.LikeUp(preLike.getBoardOther().getId());
        }
        IsUse inverseIsUse = preLike.getIsUse().inverse();
        modLike = preLike;
        modLike.setIsUse(inverseIsUse);
        return modLike;
    }


    // 게시글 좋아요 갯수 가져오기
    public int getLikeCount(Long boardId) {
        return boardOtherRepository.getLikeCount(boardId);
    }

    // 검색
    public Page<Board_Other> searchBoardList(Pageable pageable,String type,String keyword) {
        pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1, pageable.getPageSize(),new Sort(Sort.Direction.DESC, "id"));

        if (type.equals("T")) {
            return boardOtherRepository.findByTitleContains(pageable,keyword);
        } else if (type.equals("C")) {
            return boardOtherRepository.findByContentContains(pageable,keyword);
        } else if (type.equals("TC")) {
            return boardOtherRepository.findByTitleContainsOrContentContains(pageable,keyword,keyword);
        } else if (type.equals("W")){
            return boardOtherRepository.findByWriterContains(pageable,keyword);
        } else { // 다른값 들어오면 전체 글 가져오기
            return boardOtherRepository.findAll(pageable);
        }
    }

    // 조회수 증가
    public void HitCountUp(Long id) {
        boardOtherRepository.HitCountUp(id);
    }

}
