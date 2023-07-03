package com.kreamish.kream.filter.facade;

import static org.assertj.core.api.Assertions.assertThat;

import com.kreamish.kream.brand.dto.BrandDto;
import com.kreamish.kream.brand.entity.Brand;
import com.kreamish.kream.brand.repository.BrandRepository;
import com.kreamish.kream.category.entity.Category;
import com.kreamish.kream.category.repository.CategoryRepository;
import com.kreamish.kream.categorydetail.entity.CategoryDetail;
import com.kreamish.kream.categorydetail.repository.CategoryDetailRepository;
import com.kreamish.kream.collection.dto.CollectionDto;
import com.kreamish.kream.collection.entity.Collection;
import com.kreamish.kream.collection.repository.CollectionRepository;
import com.kreamish.kream.filter.dto.CategoriesFilterResultDto;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.kreamish.kream.itemsizes.dto.ItemSizeDto;
import com.kreamish.kream.itemsizes.entity.ItemSizes;
import com.kreamish.kream.itemsizes.repository.ItemSizesRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
//@TestPropertySource(properties = {"spring.config.location = classpath:application-local.yaml"})
class FilterFacadeTest {

    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    CategoryDetailRepository categoryDetailRepository;
    @Autowired
    FilterFacade filterFacade;
    @Autowired
    BrandRepository brandRepository;
    @Autowired
    CollectionRepository collectionRepository;
    @Autowired
    ItemSizesRepository itemSizesRepository;

    @Test
    void SUCCESS_SHOULD_GET_CATEGORIES() {
        //given
        List<Category> srcCategory = categoryRepository.findAll();
        List<CategoryDetail> srcCategoryDetail = categoryDetailRepository.findAll();

        //when
        List<CategoriesFilterResultDto> dstFilterResult = filterFacade.getCategories();

        int dstTotalCnt = dstFilterResult.stream()
            .mapToInt(filterResult -> filterResult.getSimpleCategoryDetailList()
                .size()).sum();

        //then
        assertThat(dstFilterResult).isNotNull();
        assertThat(dstFilterResult.size()).isEqualTo(srcCategory.size());
        assertThat(dstTotalCnt).isEqualTo(srcCategoryDetail.size());
    }

    @Test
    void SUCCESS_SHOULD_GET_BRAND() {
        //given
        List<Brand> srcBrand = brandRepository.findAll();
        List<BrandDto> srcBrandDto = srcBrand.stream()
            .map(BrandDto::of)
            .collect(Collectors.toList());

        //when
        List<BrandDto> drcBrandDto = filterFacade.getBrand();

        //then
        assertThat(srcBrandDto).isNotNull();
        assertThat(srcBrandDto.size()).isEqualTo(drcBrandDto.size());
        assertThat(srcBrandDto).isEqualTo(drcBrandDto);
    }

    @Test
    void SUCCESS_SHOULD_GET_COLLECTIONS() {
        //given
        List<Collection> srcCollections = collectionRepository.findAll();
        List<CollectionDto> srcCollectionsDto = srcCollections.stream()
            .map(CollectionDto::of)
            .collect(Collectors.toList());

        //when
        List<CollectionDto> dstCollectionsDto = filterFacade.getCollections();

        //then
        assertThat(srcCollectionsDto).isNotNull();
        assertThat(srcCollectionsDto.size()).isEqualTo(dstCollectionsDto.size());
        assertThat(srcCollectionsDto).isEqualTo(dstCollectionsDto);
    }

    @Test
    void SUCCESS_SHOULD_GET_ITEM_SIZES() {
        //given
        List<ItemSizes> srcItemSizes = itemSizesRepository.findAll();
        List<ItemSizeDto> srcItemSizesDto = srcItemSizes.stream()
            .map(ItemSizeDto::of).distinct().collect(Collectors.toList());

        //when
        List<ItemSizeDto> dstItemSizesDto = filterFacade.getItemSizes();

        //then
        assertThat(srcItemSizes).isNotNull();
        assertThat(srcItemSizesDto.size()).isEqualTo(dstItemSizesDto.size());
        assertThat(srcItemSizesDto).isEqualTo(dstItemSizesDto);
    }
}