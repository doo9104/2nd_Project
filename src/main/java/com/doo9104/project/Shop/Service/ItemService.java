package com.doo9104.project.Shop.Service;

import com.doo9104.project.Shop.domain.entity.Item.Item;
import com.doo9104.project.Shop.domain.repository.ItemRepository;
import com.doo9104.project.Shop.dto.ShopViewResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class ItemService {

    private final ItemRepository itemRepository;

    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    // 전체 리스트
    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    // 페이징 리스트
    public Page<Item> findShopList(Pageable pageable) {
        pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1, pageable.getPageSize(),new Sort(Sort.Direction.DESC, "id"));
        return itemRepository.findAll(pageable);
    }


    // 널이 아닌 값을 포함하거나 포함하지 않을 수있는 컨테이너 오브젝트.
    // 값이 있으면 isPresent ()는 true를 반환하고 get ()은 값을 반환합니다.
    //
    // OrElse () (값이 없으면 기본값을 반환 함) 및 ifPresent ()
    // (값이 있으면 코드 블록을 실행 함)와 같이 포함 된 값의 존재 여부에 의존하는 추가 메소드가 제공됩니다.
    public ShopViewResponseDto findById(Long itemId) {
        Item entity = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + itemId));
        //return itemRepository.findById(itemId);
        return new ShopViewResponseDto(entity);
    }
}
