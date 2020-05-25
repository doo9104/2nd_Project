package com.doo9104.project.Board.domain.repository;

import com.doo9104.project.Board.domain.entity.Board;
import com.doo9104.project.Board.domain.entity.Board_Comment;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

// Comment 클래스로 DB를 접근하게 해줌
public interface Board_CommentRepository extends CrudRepository<Board_Comment,Long> {

    @Query("SELECT r FROM Board_Comment r where r.board = ?1 AND r.id > 0 ORDER BY r.id ASC")
    List<Board_Comment> getCommentsOfBoard(Board board);

    @Modifying
    @Transactional
    @Query("UPDATE Board p set p.commentcount = p.commentcount + 1 where p.id = :boardId")
    void CommentCountUp(@Param("boardId") Long boardId);


    @Modifying
    @Transactional
    @Query("UPDATE Board p set p.commentcount = p.commentcount - 1 where p.id = :boardId")
    void CommentCountDown(@Param("boardId") Long boardId);

    int countById(Long id);

}
