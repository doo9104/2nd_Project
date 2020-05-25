package com.doo9104.project.Board.domain.repository;

import com.doo9104.project.Board.domain.entity.Board;
import com.doo9104.project.Board.domain.entity.BoardType;
import com.doo9104.project.Board.domain.entity.TopView;
import com.doo9104.project.Board.dto.TopViewDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// Board_Dog 클래스로 DB를 접근하게 해줌
public interface Board_Repository extends JpaRepository<Board, Long> {
    @Query("SELECT p FROM Board p ORDER BY p.id DESC")
    List<Board> findAllDesc();

    @Query("SELECT p FROM Board p WHERE boardtype = :#{#boardtype.toString()} ORDER BY p.id DESC")
    List<Board> findAllByBoardtypeDesc(@Param("boardtype") BoardType boardtype);

    Page<Board> findAllByBoardtype(Pageable pageable,BoardType type);


    // 좋아요 증가
    @Modifying
    @Transactional
    @Query("UPDATE Board p set p.likeCount = p.likeCount + 1 where p.id = :boardId")
    void LikeUp(@Param("boardId") Long boardId);

    // 좋아요 취소
    @Modifying
    @Transactional
    @Query("UPDATE Board p set p.likeCount = p.likeCount - 1 where p.id = :boardId")
    void LikeDown(@Param("boardId") Long boardId);

    // 좋아요 개수
    @Query("SELECT likeCount FROM Board WHERE id = :boardId")
    int getLikeCount(@Param("boardId") Long boardId);

    // 조회수 증가
    @Modifying
    @Transactional
    @Query("UPDATE Board p set p.hitcount = p.hitcount + 1 where p.id = :boardId")
    void HitCountUp(@Param("boardId") Long boardId);

    /* 검색 */
    Page<Board> findByTitleContainsAndBoardtype(Pageable pageable, String keyword,BoardType type); // T
    Page<Board> findByContentContainsAndBoardtype(Pageable pageable, String keyword,BoardType type); // C
    Page<Board> findByTitleContainsOrContentContainsAndBoardtype(Pageable pageable, String keyword1, String keyword2,BoardType type);// TC
    Page<Board> findByWriterContainsAndBoardtype(Pageable pageable, String keyword,BoardType type); // W


    /* 스케쥴러 */
    List<Board> findTop6ByOrderByHitcountDesc();


}
