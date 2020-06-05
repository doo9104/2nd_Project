package com.doo9104.project.Shop.domain.entity.Item;

import com.doo9104.project.Board.domain.entity.BoardType;
import com.doo9104.project.Exception.NotEnoughStockException;
import com.doo9104.project.Shop.domain.entity.Category;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Item {

    @Id @GeneratedValue
    @Column(name = "ITEM_ID")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column
    @Enumerated(EnumType.STRING)
    private BoardType animaltype;

    //////////////////

    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;
        if (restStock < 0 ) {
            throw new NotEnoughStockException("재고가 더 필요합니다");
        }
        this.stockQuantity = restStock;
    }

}
