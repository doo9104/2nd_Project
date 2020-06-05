package com.doo9104.project.Board.domain.repository;

import com.doo9104.project.Board.domain.entity.TopView;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TopViewRepository extends JpaRepository<TopView, Long> {
    List<TopView> findAll();
}
