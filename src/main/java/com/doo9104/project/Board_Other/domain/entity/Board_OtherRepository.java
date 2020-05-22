package com.doo9104.project.Board_Other.domain.entity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// Board_Other 클래스로 DB를 접근하게 해줌
public interface Board_OtherRepository extends JpaRepository<Board_Other, Long> {
    @Query("SELECT p FROM Board_Other p ORDER BY p.id DESC")
    List<Board_Other> findAllDesc();

    Page<Board_Other> findAll(Pageable pageable);

    // 좋아요 증가
    @Modifying
    @Transactional
    @Query("UPDATE Board_Other p set p.likeCount = p.likeCount + 1 where p.id = :boardId")
    void LikeUp(@Param("boardId") Long boardId);

    // 좋아요 취소
    @Modifying
    @Transactional
    @Query("UPDATE Board_Other p set p.likeCount = p.likeCount - 1 where p.id = :boardId")
    void LikeDown(@Param("boardId") Long boardId);

    // 좋아요 개수
    @Query("SELECT likeCount FROM Board_Other WHERE id = :boardId")
    int getLikeCount(@Param("boardId") Long boardId);

    // 조회수 증가
    @Modifying
    @Transactional
    @Query("UPDATE Board_Other p set p.hitcount = p.hitcount + 1 where p.id = :boardId")
    void HitCountUp(@Param("boardId") Long boardId);

    /* 검색 */
    Page<Board_Other> findByTitleContains(Pageable pageable, String keyword); // T
    Page<Board_Other> findByContentContains(Pageable pageable, String keyword); // C
    Page<Board_Other> findByTitleContainsOrContentContains(Pageable pageable, String keyword1, String keyword2);// TC
    Page<Board_Other> findByWriterContains(Pageable pageable, String keyword); // W


}
