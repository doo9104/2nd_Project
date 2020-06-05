package com.doo9104.project.Shop.domain.repository;

import com.doo9104.project.CommonEntity.User;
import com.doo9104.project.Shop.domain.entity.Order;
import com.doo9104.project.Shop.domain.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order,Long> {
    Optional<Order> findById(Long orderId);

    List<Order> findAllByUserAndStatus(User user, OrderStatus orderStatus);
}
