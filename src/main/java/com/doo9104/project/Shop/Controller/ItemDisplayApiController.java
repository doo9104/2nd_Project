package com.doo9104.project.Shop.Controller;

import com.doo9104.project.Shop.Service.ItemService;
import com.doo9104.project.Shop.domain.entity.Item.Item;
import com.doo9104.project.Shop.dto.ShopViewResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class ItemDisplayApiController {

    private final ItemService itemService;

    @GetMapping("/item")
    public String main() {

        return "/shop/register";
    }

    @GetMapping("/shoplist")
    public String showList(@PageableDefault(
            size = 9 ) Pageable pageable, Model model) {
       // model.addAttribute("items",itemService.findItems());
        model.addAttribute("items",itemService.findShopList(pageable));
        return "/shop/list";
    }

    @GetMapping("/item/{id}")
    public String viewItem(@PathVariable Long id,Model model) {
        System.out.println(itemService.findById(id));
        ShopViewResponseDto dto = itemService.findById(id);
        model.addAttribute("items",dto);
        return "/shop/view";
    }
}
