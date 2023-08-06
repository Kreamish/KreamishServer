package com.kreamish.kream.filter.facade;

import com.kreamish.kream.brand.dto.BrandDto;
import com.kreamish.kream.brand.service.BrandServiceImpl;
import com.kreamish.kream.category.dto.CategoryDto;
import com.kreamish.kream.category.service.CategoryServiceImpl;
import com.kreamish.kream.categorydetail.dto.CategoryDetailDto;
import com.kreamish.kream.categorydetail.service.CategoryDetailServiceImpl;
import com.kreamish.kream.collection.dto.CollectionDto;
import com.kreamish.kream.collection.service.CollectionServiceImpl;
import com.kreamish.kream.filter.dto.CategoriesFilterResultDto;
import com.kreamish.kream.itemsizes.dto.ItemSizeDto;
import com.kreamish.kream.itemsizes.service.ItemSizeServiceImpl;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
@Transactional(readOnly = true)
public class FilterFacade {

    private final CategoryServiceImpl categoryServiceImpl;
    private final CategoryDetailServiceImpl categoryDetailServiceImpl;
    private final BrandServiceImpl brandServiceImpl;
    private final CollectionServiceImpl collectionServiceImpl;
    private final ItemSizeServiceImpl itemSizeServiceImpl;


    public List<CategoriesFilterResultDto> getCategories() {
        List<CategoryDto> categoryDtoList = categoryServiceImpl.getAllCategories()
            .getCategoryDtoList();
        List<CategoryDetailDto> categoryDetailDtoList = categoryDetailServiceImpl.getAllCategoryDetails()
            .getCategoryDetailDtoList();

        return categoryDtoList.stream()
            .map(categoryDto -> CategoriesFilterResultDto.of(categoryDto, categoryDetailDtoList))
            .collect(Collectors.toList());
    }

    public List<BrandDto> getBrand() {
        return brandServiceImpl.getAllBrand();
    }

    public List<CollectionDto> getCollections() {
        return collectionServiceImpl.getAllCollections();
    }

    public List<ItemSizeDto> getItemSizes() {
        return itemSizeServiceImpl.getItemSizes();
    }
}
