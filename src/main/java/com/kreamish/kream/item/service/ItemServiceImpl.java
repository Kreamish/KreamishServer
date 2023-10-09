package com.kreamish.kream.item.service;

import com.kreamish.kream.common.entity.BaseEntity;
import com.kreamish.kream.item.dto.ItemDetailResponseDto;
import com.kreamish.kream.item.dto.ItemListResponseDto;
import com.kreamish.kream.item.dto.ItemListResponsePageDto;
import com.kreamish.kream.item.dto.ItemListSearchCondition;
import com.kreamish.kream.item.dto.LastTradedPricePerItemSizeDto;
import com.kreamish.kream.item.entity.Item;
import com.kreamish.kream.item.repository.ItemRepository;
import com.kreamish.kream.item.repository.TradeRepository;
import com.kreamish.kream.itemsizes.entity.ItemSizes;
import com.kreamish.kream.itemsizes.repository.ItemSizesRepository;
import com.kreamish.kream.login.LoginMemberInfo;
import com.kreamish.kream.trade.entity.Trade;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    private final ItemSizesRepository itemSizesRepository;

    private final TradeRepository tradeRepository;

    @Override
    public List<LastTradedPricePerItemSizeDto> getLastTradedPrice(Long itemId, Long itemSizesId,
        LoginMemberInfo loginMemberInfo) {

        if (loginMemberInfo.isNotLoggedIn()) {
            // 가장 최근 가격
        } else {
            // item id, itemSizes id, member id
            // O      , X           , O => 모든 사이즈 별 최근 가격들
            // O      , O           , O => 특정 사이즈의 최근 거래 가격들
        }
        /*
            select
            from
            (select is.size, is.id
            from itemsizes is
            where is.item.id := {itemId}) is_list
            p join is_list on p.is.id = is_list.id



         */
        // item id, itemSizes id, member id
        // O      , X           , X => 아이템 사이즈 중 최저 즉시 구매가의 최근 가격들
        // O      , O           , X => 아이템 사이즈 중 최저 즉시 구매가의 최근 가격들

        // O      , X           , O => 모든 사이즈 별 최근 가격들
        // O      , O           , O => 특정 사이즈의 최근 거래 가격들

        return null;
    }

    @Override
    public Optional<Item> getItemById(Long itemId) {
        return itemRepository.findById(itemId);
    }

    @Override
    public ItemListResponseDto findItemsByCondition(ItemListSearchCondition condition,
        PageRequest pageRequest) {
        ItemListResponseDto dto = new ItemListResponseDto();
        dto.setItemPages(
            itemRepository.findItemsByCondition(condition, pageRequest).map(this::convert));
        return dto;
    }

    @Override
    public ItemDetailResponseDto findItemById(Long itemId) {
        Item item = itemRepository.findById(itemId)
            .orElseThrow(() -> new NoSuchElementException("item id로 해당 상품을 찾을 수 없습니다."));

        return ItemDetailResponseDto.builder()
            .name(item.getName())
            .subName(item.getSubName())
            .brandName(item.getBrand().getName())
            .modelCode(item.getModelCode())
            .releasePrice(item.getReleasePrice())
            .releaseDate(item.getReleaseDate())
            .representativeColor(item.getRepresentativeColor())
            .build();
    }

    /**
     * recentPrice, likeCount, commentCount 추후 업데이트 예정. recentPrice 의 경우 다소 복잡할 것으로 생각
     */
    private ItemListResponsePageDto convert(Item entity) {
        return ItemListResponsePageDto.builder()
            .itemId(entity.getItemId())
            .brandName(entity.getBrand().getName())
            .name(entity.getName())
            .subName(entity.getSubName())
            .recentPrice(getRecentPrice(entity))
            .likeCount(null)
            .commentCount(null)
            .imageUrl(entity.getImageUrl())
            .build();
    }

    private Long getRecentPrice(Item entity) {
        return tradeRepository.findTradeByItemSizesIdList(
            itemSizesRepository.findByItemId(entity.getItemId()).stream()
                .mapToLong(ItemSizes::getItemSizesId)
                .boxed()
                .toList()
        ).stream().max(
            Comparator.comparing(BaseEntity::getCreatedAt)
        ).map(Trade::getTradePrice).orElse(null);
    }
}
