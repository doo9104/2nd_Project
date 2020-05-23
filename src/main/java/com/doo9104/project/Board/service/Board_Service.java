package com.doo9104.project.Board.service;

import com.doo9104.project.Board.domain.entity.Board_Repository;
import com.doo9104.project.Board.domain.entity.Board;
import com.doo9104.project.Board.domain.entity.Board_Like;
import com.doo9104.project.Board.domain.entity.Board_LikeRepository;
import com.doo9104.project.CommonEntity.IsUse;
import com.doo9104.project.Board.dto.BoardDto;
import com.doo9104.project.Board.dto.BoardListResponseDto;
import com.doo9104.project.Board.dto.BoardPostResponseDto;
import com.doo9104.project.Board.dto.BoardUpdateRequestDto;
import com.doo9104.project.CommonEntity.User;
import com.doo9104.project.CommonEntity.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class Board_Service {

    private final Board_Repository boardRepository;
    private final UserRepository userRepository;

    private final Board_LikeRepository boardLikeRepository;




    @Transactional
    public Long save(BoardDto boardDto) {
        return boardRepository.save(boardDto.toEntity()).getId();
    }


    // 게시판 리스트
    public Page<Board> findBoardList(Pageable pageable,Board.BoardType type) {
        pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1, pageable.getPageSize(),new Sort(Sort.Direction.DESC, "id"));
        System.out.println("!!type : " + type.name());
        return boardRepository.findAllByBoardtype(pageable,type);
    }

    // 게시글 수정
    @Transactional
    public Long update(Long id, BoardUpdateRequestDto boardUpdateRequestDto) {
        Board boardDog = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(("해당 게시물이 없습니다.")));
        boardDog.update(boardUpdateRequestDto.getTitle(), boardUpdateRequestDto.getContent());
        return id;
    }

    // 아이디로 찾기
    public BoardPostResponseDto findById(Long id) {
        Board entity = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));

        return new BoardPostResponseDto(entity);
    }

    // 안쓰면 삭제혀라
    @Transactional(readOnly = true) // 트랜잭션 범위는 유효하되 조회기능만 남겨둠(조회속도 향상)
    public List<BoardListResponseDto> findAllDesc() {
        return boardRepository.findAllDesc().stream()
                .map(BoardListResponseDto::new)
                .collect(Collectors.toList());
    }

    // 게시글 삭제
    @Transactional
    public void delete(Long id) {
        Board boardDog = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물이 없습니다."));

        boardRepository.delete(boardDog);
    }


    //좋아요 토글
    public void toggleLike(Long boardId, Long userId) {
        Board_Like preLike = boardLikeRepository.findByBoard_IdAndUser_Id(boardId,userId);
        Board_Like newOrModLike = null;
        if(preLike == null){ // 처음 추천일때
            newOrModLike = this.newLike(boardId,userId);
        }else{ // 이미 추천했을때
            newOrModLike = this.modifyLike(preLike);
        }
          boardLikeRepository.save(newOrModLike);
    }

    private Board_Like newLike(Long boardId, Long userId){
        Board_Like newLike = new Board_Like();
        Board board = new Board();
        final User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다."));

        board.SetBoardId(boardId);

        newLike.setBoard(board);
        newLike.setUser(user);
        newLike.setIsUse(IsUse.Y);
        boardRepository.LikeUp(boardId);

        return newLike;
    }

    private Board_Like modifyLike(Board_Like preLike){
        Board_Like modLike = null;
        if(preLike.getIsUse().equals(IsUse.Y)) {
            boardRepository.LikeDown(preLike.getBoard().getId());
        } else {
            boardRepository.LikeUp(preLike.getBoard().getId());
        }
        IsUse inverseIsUse = preLike.getIsUse().inverse();
        modLike = preLike;
        modLike.setIsUse(inverseIsUse);
        return modLike;
    }


    // 게시글 좋아요 갯수 가져오기
    public int getLikeCount(Long boardId) {
        return boardRepository.getLikeCount(boardId);
    }

    // 검색
    public Page<Board> searchBoardList(Pageable pageable, String type, String keyword, Board.BoardType boardtype) {
        pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1, pageable.getPageSize(),new Sort(Sort.Direction.DESC, "id"));

        if (type.equals("T")) {
            return boardRepository.findByTitleContainsAndBoardtype(pageable,keyword,boardtype);
        } else if (type.equals("C")) {
            return boardRepository.findByContentContainsAndBoardtype(pageable,keyword,boardtype);
        } else if (type.equals("TC")) {
            return boardRepository.findByTitleContainsOrContentContainsAndBoardtype(pageable,keyword,keyword,boardtype);
        } else if (type.equals("W")){
            return boardRepository.findByWriterContainsAndBoardtype(pageable,keyword,boardtype);
        } else { // 다른값 들어오면 전체 글 가져오기
            return boardRepository.findAll(pageable);
        }
    }

    // 조회수 증가
    public void HitCountUp(Long id) {
        boardRepository.HitCountUp(id);
    }

}
