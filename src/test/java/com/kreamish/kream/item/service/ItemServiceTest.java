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
import java.util.List;
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

        //given
        Brand brand = Brand.of("brand");
        Category category = Category.of("category");
        CategoryDetail categoryDetail = CategoryDetail.of(category, "category-detail");

        em.persist(brand);
        em.persist(category);
        em.persist(categoryDetail);

        List<Object> forSave = new ArrayList<>();

        Item item1 = Item.builder()
            .name("item1")
            .subName("itemSubname1")
            .brand(brand)
            .category(category)
            .categoryDetail(categoryDetail)
            .build();

        Item item2 = Item.builder()
            .name("item2")
            .subName("itemSubname2")
            .brand(brand)
            .category(category)
            .categoryDetail(categoryDetail)
            .build();

        Item item3 = Item.builder()
            .name("item3")
            .subName("itemSubname3")
            .brand(brand)
            .category(category)
            .categoryDetail(categoryDetail)
            .build();

        Item item4 = Item.builder()
            .name("item4")
            .subName("itemSubname4")
            .brand(brand)
            .category(category)
            .categoryDetail(categoryDetail)
            .build();

        forSave.add(item1);
        forSave.add(item2);
        forSave.add(item3);
        forSave.add(item4);

        ItemSizes itemSizes1 = ItemSizes.builder()
            .item(item1)
            .size("size1")
            .build();

        ItemSizes itemSizes2 = ItemSizes.builder()
            .item(item2)
            .size("size2")
            .build();

        ItemSizes itemSizes3 = ItemSizes.builder()
            .item(item3)
            .size("size3")
            .build();

        ItemSizes itemSizes4 = ItemSizes.builder()
            .item(item4)
            .size("size4")
            .build();

        forSave.add(itemSizes1);
        forSave.add(itemSizes2);
        forSave.add(itemSizes3);
        forSave.add(itemSizes4);

        Sale sale1 = Sale.builder()
            .itemSizes(itemSizes1)
            .saleStatus(DealStatus.PENDING)
            .salePrice(100000L)
            .build();

        Sale sale2 = Sale.builder()
            .itemSizes(itemSizes2)
            .saleStatus(DealStatus.PENDING)
            .salePrice(200000L)
            .build();

        Sale sale3 = Sale.builder()
            .itemSizes(itemSizes3)
            .saleStatus(DealStatus.PENDING)
            .salePrice(99999L)
            .build();

        Sale sale4 = Sale.builder()
            .itemSizes(itemSizes3)
            .saleStatus(DealStatus.COMPLETE)
            .salePrice(123456L)
            .build();

        Sale sale5 = Sale.builder()
            .itemSizes(itemSizes4)
            .saleStatus(DealStatus.COMPLETE)
            .salePrice(123456L)
            .build();

        Sale sale6 = Sale.builder()
            .itemSizes(itemSizes4)
            .saleStatus(DealStatus.PENDING)
            .salePrice(200001L)
            .build();

        forSave.add(sale1);
        forSave.add(sale2);
        forSave.add(sale3);
        forSave.add(sale4);
        forSave.add(sale5);
        forSave.add(sale6);

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

        Long item1Id = item1.getItemId();
        Long item2Id = item2.getItemId();

        assertThat(dto.getItemPages()).extracting("itemId")
            .contains(item1Id, item2Id);
    }
}