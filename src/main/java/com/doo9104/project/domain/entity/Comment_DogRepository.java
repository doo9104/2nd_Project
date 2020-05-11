package com.doo9104.project.domain.entity;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

// Comment_Dog 클래스로 DB를 접근하게 해줌
public interface Comment_DogRepository extends CrudRepository<Comment_Dog,Long> {

    @Query("SELECT r FROM Comment_Dog r where r.board_dog = ?1 AND r.id > 0 ORDER BY r.id ASC")
    List<Comment_Dog> getCommentsOfBoard(Board_Dog board_dog);

    //@Query(value = "SELECT count(*) from comment_dog t1 JOIN like_comment_dog t2 ON t1.board_dog_id = t2.board_id WHERE t1.id = :commentId and t2.is_use='Y'", nativeQuery = true)
    @Query(value = "SELECT count(*) from like_comment_dog where board_id= :boardId and comment_id= :commentId and is_use='Y'",nativeQuery = true)
    int findAllReceivedLikeCount(@Param("boardId")int boardId, @Param("commentId")int commentId);

}
