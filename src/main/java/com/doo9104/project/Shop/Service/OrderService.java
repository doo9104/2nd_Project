package com.doo9104.project.Shop.Service;

import com.doo9104.project.CommonEntity.User;
import com.doo9104.project.CommonEntity.UserRepository;
import com.doo9104.project.Shop.domain.entity.Delivery;
import com.doo9104.project.Shop.domain.entity.Item.Item;
import com.doo9104.project.Shop.domain.entity.Order;
import com.doo9104.project.Shop.domain.entity.OrderItem;
import com.doo9104.project.Shop.domain.entity.OrderStatus;
import com.doo9104.project.Shop.domain.repository.ItemRepository;
import com.doo9104.project.Shop.domain.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class OrderService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final ItemService itemService;

    // 주문
    public Long order(Long userId, Long itemId, int quantity) {
        User user = new User();
        Item item = new Item();
        // 엔티티 조회
        Optional<User> Optionaluser = userRepository.findById(userId);
        if (Optionaluser.isPresent()) {
            user = Optionaluser.get();
        }

        /*Optional<Item> Optionalitem = itemService.findById(itemId);
        if (Optionalitem.isPresent()) {
            item = Optionalitem.get();
        }*/

        // 배송정보 생성
        Delivery delivery = new Delivery(user.getAddress());

        // 주문상품 정보 생성
        OrderItem orderItem =
                OrderItem.createOrderItem(item,1,quantity);

        // 주문 생성
        Order order = Order.createOrder(user,delivery,orderItem);

        // 주문 저장
        orderRepository.save(order);
        return order.getId();
    }


    // 주문 취소
    public void cancelOrder(Long orderId) {
        // 주문 엔티티 조회
        Optional<Order> Optionalorder = orderRepository.findById(orderId);
        if (Optionalorder.isPresent()) {
            Order order = Optionalorder.get();
            order.cancel();
        }
    }

    // 주문 조회
    public List<Order> findOrders(User user, OrderStatus orderStatus) {
        return orderRepository.findAllByUserAndStatus(user, orderStatus);
    }



}
