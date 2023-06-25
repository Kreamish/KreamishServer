package com.kreamish.kream.item.service;

import com.kreamish.kream.item.dto.ItemListResponseDto;
import com.kreamish.kream.item.dto.ItemListResponsePageDto;
import com.kreamish.kream.item.dto.ItemListSearchCondition;
import com.kreamish.kream.item.entity.Item;
import com.kreamish.kream.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    @Override
    public ItemListResponseDto findItemsByCondition(ItemListSearchCondition condition, PageRequest pageRequest) {
        ItemListResponseDto dto = new ItemListResponseDto();
        dto.setItemPages(itemRepository.findItemsByCondition(condition, pageRequest).map(this::convert));
        return dto;
    }

    /**
     * recentPrice, likeCount, commentCount 추후 업데이트 예정.
     * recentPrice 의 경우 다소 복잡할 것으로 생각
     */
    private ItemListResponsePageDto convert(Item entity) {
        return ItemListResponsePageDto.builder()
            .itemId(entity.getItemId())
            .brandName(entity.getBrand().getName())
            .name(entity.getName())
            .subName(entity.getSubName())
            .recentPrice(null)
            .likeCount(null)
            .commentCount(null)
            .imageUrl(entity.getImageUrl())
            .build();
    }
}
