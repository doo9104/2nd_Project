package com.doo9104.project.Board_Other.domain.entity;


import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

// Comment_Other 클래스로 DB를 접근하게 해줌
public interface Comment_OtherRepository extends CrudRepository<Comment_Other,Long> {

    @Query("SELECT r FROM Comment_Other r where r.board_other = ?1 AND r.id > 0 ORDER BY r.id ASC")
    List<Comment_Other> getCommentsOfBoard(Board_Other board_other);

    @Modifying
    @Transactional
    @Query("UPDATE Board_Other p set p.commentcount = p.commentcount + 1 where p.id = :boardId")
    void CommentCountUp(@Param("boardId") Long boardId);


    @Modifying
    @Transactional
    @Query("UPDATE Board_Other p set p.commentcount = p.commentcount - 1 where p.id = :boardId")
    void CommentCountDown(@Param("boardId") Long boardId);

    int countById(Long id);

}
