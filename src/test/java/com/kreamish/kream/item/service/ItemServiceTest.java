package com.kreamish.kream.item.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.kreamish.kream.category.entity.Category;
import com.kreamish.kream.categorydetail.entity.CategoryDetail;
import com.kreamish.kream.item.dto.ItemListResponseDto;
import com.kreamish.kream.item.dto.ItemListSearchCondition;
import com.kreamish.kream.item.entity.Item;
import com.kreamish.kream.item.entity.ItemSizes;
import com.kreamish.kream.legacy.entity.Brand;
import com.kreamish.kream.legacy.entity.DealStatus;
import com.kreamish.kream.legacy.entity.Sale;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class ItemServiceTest {

    @Autowired
    private ItemService itemService;

    @PersistenceContext
    private EntityManager em;

    @Test
    @DisplayName("가격 범위에 들어오는 상품들의 목록만 보여져야 한다.")
    void SUCCESS_ONLY_PRICE_CONDITION() {

        // base: brand 1개, category 1개, categoryDetail 1개
        // item 4개, itemSizes 4개 (1:1)

        // 상품   - 상품 크기   - 판매 정보(상태, 가격 범위에 들어오는 여부)
        // item1 - item1size - sale1(Pending, O)
        // item2 - item2size - sale2(Pending, O)
        // item3 - item3size - sale3(Pending, X), sale4(Complete, O)
        // item4 - item4size - sale5(Complete, O), sale6(Pending, X)

        List<Object> forSave = new ArrayList<>();

        //given
        Brand brand = Brand.of("brand");
        Category category = Category.of("category");
        CategoryDetail categoryDetail = CategoryDetail.of(category, "category-detail");

        List<Item> items = getDefaultItems(4, brand, category, categoryDetail);

        List<ItemSizes> itemSizes = getDefaultItemSizeOneToOne(items);

        List<Sale> customSales = getCustomSales(itemSizes);

        forSave.add(brand);
        forSave.add(category);
        forSave.add(categoryDetail);
        forSave.addAll(items);
        forSave.addAll(itemSizes);
        forSave.addAll(customSales);

        for (Object entity : forSave) {
            em.persist(entity);
        }

        //when
        long minPrice = 100000L;
        long maxPrice = 200000L;
        ItemListSearchCondition condition = ItemListSearchCondition.builder()
            .categoryIds(null)
            .brandIds(null)
            .collectionIds(null)
            .minPrice(minPrice)
            .maxPrice(maxPrice)
            .sizes(null)
            .build();

        ItemListResponseDto dto = itemService.findItemsByCondition(condition, PageRequest.of(0, 10));

        Long item1Id = items.get(0).getItemId();
        Long item2Id = items.get(1).getItemId();

        assertThat(dto.getItemPages()).extracting("itemId")
            .contains(item1Id, item2Id);
    }

    private List<Sale> getCustomSales(List<ItemSizes> itemSizes) {
        if (itemSizes == null || itemSizes.size() != 4) {
            throw new IllegalArgumentException("현재 테스트에선 4개의 itemSize 를 사용해야 함");
        }
        return new ArrayList<>(Arrays.asList(
            Sale.builder()
                .itemSizes(itemSizes.get(0))
                .saleStatus(DealStatus.PENDING)
                .salePrice(100000L)
                .build(),
            Sale.builder()
                .itemSizes(itemSizes.get(1))
                .saleStatus(DealStatus.PENDING)
                .salePrice(200000L)
                .build(),
            Sale.builder()
                .itemSizes(itemSizes.get(2))
                .saleStatus(DealStatus.PENDING)
                .salePrice(99999L)
                .build(),
            Sale.builder()
                .itemSizes(itemSizes.get(2))
                .saleStatus(DealStatus.COMPLETE)
                .salePrice(123456L)
                .build(),
            Sale.builder()
                .itemSizes(itemSizes.get(3))
                .saleStatus(DealStatus.COMPLETE)
                .salePrice(123456L)
                .build(),
            Sale.builder()
                .itemSizes(itemSizes.get(3))
                .saleStatus(DealStatus.PENDING)
                .salePrice(200001L)
                .build()
        ));
    }

    private List<ItemSizes> getDefaultItemSizeOneToOne(List<Item> items) {
        AtomicInteger ai = new AtomicInteger(0);
        return items.stream()
            .map(item -> {
                String sizeName = String.format("size%d", ai.incrementAndGet());
                return ItemSizes.builder()
                    .item(item)
                    .size(sizeName)
                    .build();
            })
            .collect(Collectors.toList());
    }

    private List<Item> getDefaultItems(
        @SuppressWarnings("SameParameterValue") int count,
        Brand brand, Category category, CategoryDetail categoryDetail
    ) {
        List<Item> result = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            String itemName = String.format("item%d", i);
            String itemSubName = String.format("itemSubname%d", i);
            result.add(
                Item.builder()
                    .name(itemName)
                    .subName(itemSubName)
                    .brand(brand)
                    .category(category)
                    .categoryDetail(categoryDetail)
                    .build()
            );
        }
        return result;
    }
}