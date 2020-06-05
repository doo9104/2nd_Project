package com.doo9104.project.Shop.Controller;

import com.doo9104.project.Shop.Service.ItemService;
import com.doo9104.project.Shop.domain.entity.Item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ItemPostApiController {

    private final ItemService itemService;

    //@PutMapping("/item")
    @PostMapping("/item")
    public String main(@RequestBody Item item) {
        System.out.println("item : " + item.getId());
        System.out.println("item : " + item.getName());
        System.out.println("item : " + item.getDescription());
        System.out.println("item : " + item.getCategory());
        System.out.println("item : " + item.getPrice());
        System.out.println("item : " + item.getStockQuantity());

        itemService.saveItem(item);

        return "/shop/register";
    }
}
