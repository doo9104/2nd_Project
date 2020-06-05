package com.doo9104.project.Shop.domain.entity;

import com.doo9104.project.Shop.domain.entity.Item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Getter
@Setter
@Entity
@Table(name = "ORDER_ITEM")
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "ORDER_ITEM_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ITEM_ID")
    private Item item; // 상품

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_ID")
    private Order order; // 주문

    private int orderPrice; // 가격
    private int quantity; // 수량


    // 생성 메소드
    // 주문상품,가격,수량 정보를 사용해서 주문 상품 엔티티를 생성하고
    // item.removeStock(quantity)를 호출해서 주문수량만큼 재고를 줄인다
    public static OrderItem createOrderItem(Item item, int orderPrice, int quantity) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setQuantity(quantity);

        item.removeStock(quantity);
        return orderItem;
    }

    // 비즈니스

    // 주문 취소
    // 취소한 주문 수량만큼 상품의 재고를 증가시킨다
    public void cancel() {
        getItem().addStock(quantity);
    }

    // 주문 가격 조회
    // 주문 가격에 수량을 곱한 값을 반환
    public  int getTotalPrice() {
        return getOrderPrice() * getQuantity();
    }

}
