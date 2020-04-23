package com.doo9104.project.domain.entity;

import com.doo9104.project.domain.entity.Board_Dog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

// Board_Dog 클래스로 DB를 접근하게 해줌
public interface Board_DogRepository extends JpaRepository<Board_Dog, Long> {
    @Query("SELECT p FROM Board_Dog p ORDER BY p.id DESC")
    List<Board_Dog> findAllDesc();
}
