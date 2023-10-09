package com.kreamish.kream.item.service;

import com.kreamish.kream.item.dto.ItemDetailResponseDto;
import com.kreamish.kream.item.dto.ItemListResponseDto;
import com.kreamish.kream.item.dto.ItemListSearchCondition;
import com.kreamish.kream.item.dto.LastTradedPricePerItemSizeDto;
import com.kreamish.kream.item.entity.Item;
import com.kreamish.kream.login.LoginMemberInfo;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.PageRequest;

public interface ItemService {

    ItemListResponseDto findItemsByCondition(ItemListSearchCondition condition,
        PageRequest pageRequest);

    ItemDetailResponseDto findItemById(Long itemId);

    Optional<Item> getItemById(Long itemId);

    List<LastTradedPricePerItemSizeDto> getLastTradedPrice(Long itemId, Long itemSizesId,
        LoginMemberInfo loginMemberInfo);
}