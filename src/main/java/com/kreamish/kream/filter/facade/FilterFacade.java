package com.kreamish.kream.filter.facade;

import com.kreamish.kream.brand.dto.BrandDto;
import com.kreamish.kream.brand.service.BrandService;
import com.kreamish.kream.category.dto.CategoryDto;
import com.kreamish.kream.category.service.CategoryService;
import com.kreamish.kream.categorydetail.dto.CategoryDetailDto;
import com.kreamish.kream.categorydetail.service.CategoryDetailService;
import com.kreamish.kream.collection.dto.CollectionDto;
import com.kreamish.kream.collection.service.CollectionService;
import com.kreamish.kream.filter.dto.BrandFilterResponseDto;
import com.kreamish.kream.filter.dto.CategoriesFilterResponseDto;
import com.kreamish.kream.filter.dto.ItemSizesFilterResponseDto;
import com.kreamish.kream.filter.service.FilterService;
import com.kreamish.kream.itemsizes.dto.ItemSizeDto;
import com.kreamish.kream.itemsizes.service.ItemSizeService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
@Transactional(readOnly = true)
public class FilterFacade {

    private final CategoryService categoryService;
    private final CategoryDetailService categoryDetailService;
    private final BrandService brandService;
    private final CollectionService collectionService;
    private final ItemSizeService itemSizeService;
    private final FilterService filterService;

    public List<CategoriesFilterResponseDto> getCategories() {
        List<CategoryDto> categoryDtoList = categoryService.getAllCategories()
            .getCategoryDtoList();
        List<CategoryDetailDto> categoryDetailDtoList = categoryDetailService.getAllCategoryDetails()
            .getCategoryDetailDtoList();

        return categoryDtoList.stream()
            .map(categoryDto -> CategoriesFilterResponseDto.of(categoryDto, categoryDetailDtoList))
            .collect(Collectors.toList());
    }

    public List<BrandFilterResponseDto> getBrandFilterList() {
        List<BrandDto> allBrand = brandService.getAllBrand();

        return filterService.convertToBrandFilterList(allBrand);
    }

    public List<CollectionDto> getCollections() {
        return collectionService.getAllCollections();
    }

    public List<ItemSizesFilterResponseDto> getItemSizesFilterList() {
        List<ItemSizeDto> itemSizes = itemSizeService.getItemSizes();

        return filterService.convertToItemSizesFilterList(itemSizes);
    }
}
