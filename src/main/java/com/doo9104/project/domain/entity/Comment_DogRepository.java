package com.doo9104.project.domain.entity;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


import java.util.List;

// Comment_Dog 클래스로 DB를 접근하게 해줌
public interface Comment_DogRepository extends CrudRepository<Comment_Dog,Long> {

    @Query("SELECT r FROM Comment_Dog r where r.board_dog = ?1 AND r.id > 0 ORDER BY r.id ASC")
    List<Comment_Dog> getCommentsOfBoard(Board_Dog board_dog);

}
