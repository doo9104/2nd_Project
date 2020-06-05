package com.doo9104.project.Shop.domain.repository;


import com.doo9104.project.Shop.domain.entity.Item.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item,Long> {
    Page<Item> findAll(Pageable pageable);
}
