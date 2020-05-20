package com.doo9104.project.domain.entity;

import com.doo9104.project.domain.entity.Board_Dog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// Board_Dog 클래스로 DB를 접근하게 해줌
public interface Board_DogRepository extends JpaRepository<Board_Dog, Long> {
    @Query("SELECT p FROM Board_Dog p ORDER BY p.id DESC")
    List<Board_Dog> findAllDesc();

    Page<Board_Dog> findAll(Pageable pageable);

    @Modifying
    @Transactional
    @Query("UPDATE Board_Dog p set p.likeCount = p.likeCount + 1 where p.id = :boardId")
    void LikeUp(@Param("boardId") Long boardId);

    @Modifying
    @Transactional
    @Query("UPDATE Board_Dog p set p.likeCount = p.likeCount - 1 where p.id = :boardId")
    void LikeDown(@Param("boardId") Long boardId);

    @Query("SELECT likeCount FROM Board_Dog WHERE id = :boardId")
    int getLikeCount(@Param("boardId") Long boardId);


    /* 검색 */
    Page<Board_Dog> findByTitleContains(Pageable pageable,String keyword); // T
    Page<Board_Dog> findByContentContains(Pageable pageable,String keyword); // C
    Page<Board_Dog> findByTitleContainsOrContentContains(Pageable pageable,String keyword1,String keyword2);// TC
    Page<Board_Dog> findByWriterContains(Pageable pageable,String keyword); // W


}
