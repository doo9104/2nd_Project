package com.doo9104.project.Board_Dog.service;

import com.doo9104.project.Board_Dog.domain.entity.Board_DogRepository;
import com.doo9104.project.Board_Dog.domain.entity.Board_Dog;
import com.doo9104.project.Board_Dog.domain.entity.Board_Dog_Like;
import com.doo9104.project.Board_Dog.domain.entity.Board_Dog_LikeRepository;
import com.doo9104.project.Board_Dog.domain.entity.Comment_DogRepository;
import com.doo9104.project.CommonEntity.IsUse;
import com.doo9104.project.Board_Dog.dto.DogDto;
import com.doo9104.project.Board_Dog.dto.DogListResponseDto;
import com.doo9104.project.Board_Dog.dto.DogPostResponseDto;
import com.doo9104.project.Board_Dog.dto.DogUpdateRequestDto;
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
public class Board_DogService {

    private final Board_DogRepository boardDogRepository;
    private final Comment_DogRepository commentDogRepository;
    private final UserRepository userRepository;

    private final Board_Dog_LikeRepository boardDogLikeRepository;




    @Transactional
    public Long save(DogDto dogDto) {
        return boardDogRepository.save(dogDto.toEntity()).getId();
    }


    public Page<Board_Dog> findBoardList(Pageable pageable) {
        pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1, pageable.getPageSize(),new Sort(Sort.Direction.DESC, "id"));

        return boardDogRepository.findAll(pageable);
    }


    @Transactional
    public Long update(Long id, DogUpdateRequestDto dogUpdateRequestDto) {
        Board_Dog boardDog = boardDogRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(("해당 게시물이 없습니다.")));
        boardDog.update(dogUpdateRequestDto.getTitle(), dogUpdateRequestDto.getContent());
        return id;
    }


    public DogPostResponseDto findById(Long id) {
        Board_Dog entity = boardDogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));

        return new DogPostResponseDto(entity);
    }

    @Transactional(readOnly = true) // 트랜잭션 범위는 유효하되 조회기능만 남겨둠(조회속도 향상)
    public List<DogListResponseDto> findAllDesc() {
        return boardDogRepository.findAllDesc().stream()
                .map(DogListResponseDto::new)
                .collect(Collectors.toList());
    }


    @Transactional
    public void delete(Long id) {
        Board_Dog boardDog = boardDogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물이 없습니다."));

        boardDogRepository.delete(boardDog);
    }



      public void toggleLike(Long boardId, Long userId) {
        Board_Dog_Like preLike = boardDogLikeRepository.findByBoardDog_IdAndUser_Id(boardId,userId);
        Board_Dog_Like newOrModLike = null;
        if(preLike == null){ // 처음 추천일때
            newOrModLike = this.newLike(boardId,userId);
        }else{ // 이미 추천했을때
            newOrModLike = this.modifyLike(preLike);
        }
        boardDogLikeRepository.save(newOrModLike);
    }

    private Board_Dog_Like newLike(Long boardId, Long userId){
        Board_Dog_Like newLike = new Board_Dog_Like();
        Board_Dog board_dog = new Board_Dog();
        final User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다."));

        board_dog.SetBoardId(boardId);

        newLike.setBoardDog(board_dog);
        newLike.setUser(user);
        newLike.setIsUse(IsUse.Y);
        boardDogRepository.LikeUp(boardId);

        return newLike;
    }

    private Board_Dog_Like modifyLike(Board_Dog_Like preLike){
        Board_Dog_Like modLike = null;
        if(preLike.getIsUse().equals(IsUse.Y)) {
            boardDogRepository.LikeDown(preLike.getBoardDog().getId());
        } else {
            boardDogRepository.LikeUp(preLike.getBoardDog().getId());
        }
        IsUse inverseIsUse = preLike.getIsUse().inverse();
        modLike = preLike;
        modLike.setIsUse(inverseIsUse);
        return modLike;
    }


    // 게시글 좋아요 갯수 가져오기
    public int getLikeCount(Long boardId) {
        return boardDogRepository.getLikeCount(boardId);
    }

    // 검색
    public Page<Board_Dog> searchBoardList(Pageable pageable,String type,String keyword) {
        pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1, pageable.getPageSize(),new Sort(Sort.Direction.DESC, "id"));

        if (type.equals("T")) {
            return boardDogRepository.findByTitleContains(pageable,keyword);
        } else if (type.equals("C")) {
            return boardDogRepository.findByContentContains(pageable,keyword);
        } else if (type.equals("TC")) {
            return boardDogRepository.findByTitleContainsOrContentContains(pageable,keyword,keyword);
        } else if (type.equals("W")){
            return boardDogRepository.findByWriterContains(pageable,keyword);
        } else { // 다른값 들어오면 전체 글 가져오기
            return boardDogRepository.findAll(pageable);
        }
    }

    // 조회수 증가
    public void HitCountUp(Long id) {
        boardDogRepository.HitCountUp(id);
    }

}
