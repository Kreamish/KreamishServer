package com.kreamish.kream.filter.service;

import com.kreamish.kream.brand.dto.BrandDto;
import com.kreamish.kream.filter.dto.BrandFilterResponseDto;
import com.kreamish.kream.filter.dto.ItemSizesFilterResponseDto;
import com.kreamish.kream.itemsizes.dto.ItemSizeDto;
import java.util.List;

public interface FilterService {

    List<BrandFilterResponseDto> convertToBrandFilterList(List<BrandDto> allBrand);

    List<ItemSizesFilterResponseDto> convertToItemSizesFilterList(List<ItemSizeDto> itemSizes);
}
