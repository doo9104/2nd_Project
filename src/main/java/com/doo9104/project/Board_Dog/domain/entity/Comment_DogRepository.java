package com.doo9104.project.Board_Dog.domain.entity;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

// Comment_Dog 클래스로 DB를 접근하게 해줌
public interface Comment_DogRepository extends CrudRepository<Comment_Dog,Long> {

    @Query("SELECT r FROM Comment_Dog r where r.board_dog = ?1 AND r.id > 0 ORDER BY r.id ASC")
    List<Comment_Dog> getCommentsOfBoard(Board_Dog board_dog);

    @Modifying
    @Transactional
    @Query("UPDATE Board_Dog p set p.commentcount = p.commentcount + 1 where p.id = :boardId")
    void CommentCountUp(@Param("boardId") Long boardId);


    @Modifying
    @Transactional
    @Query("UPDATE Board_Dog p set p.commentcount = p.commentcount - 1 where p.id = :boardId")
    void CommentCountDown(@Param("boardId") Long boardId);

    int countById(Long id);

}
