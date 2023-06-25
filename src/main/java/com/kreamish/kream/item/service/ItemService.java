package com.kreamish.kream.item.service;

import com.kreamish.kream.item.dto.ItemListResponseDto;
import com.kreamish.kream.item.dto.ItemListSearchCondition;
import org.springframework.data.domain.PageRequest;

public interface ItemService {

    ItemListResponseDto findItemsByCondition(ItemListSearchCondition condition, PageRequest pageRequest);
}
