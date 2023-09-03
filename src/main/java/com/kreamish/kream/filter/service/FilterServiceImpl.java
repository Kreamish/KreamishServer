package com.kreamish.kream.filter.service;

import com.kreamish.kream.brand.dto.BrandDto;
import com.kreamish.kream.filter.dto.BrandFilterResponseDto;
import com.kreamish.kream.filter.dto.ItemSizesFilterResponseDto;
import com.kreamish.kream.itemsizes.dto.ItemSizeDto;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Service
@Transactional
public class FilterServiceImpl implements FilterService {

    @Override
    public List<BrandFilterResponseDto> convertToBrandFilterList(List<BrandDto> allBrand) {
        Map<Character, BrandFilterResponseDto> firstLetter2BrandFilterResponseDto = getFirstLetter2BrandFilter(
            allBrand);

        return firstLetter2BrandFilterResponseDto
            .values()
            .stream()
            .sorted(Comparator.comparing(BrandFilterResponseDto::getBrandFirstLetter))
            .collect(Collectors.toList());
    }

    @Override
    public List<ItemSizesFilterResponseDto> convertToItemSizesFilterList(
        List<ItemSizeDto> itemSizes) {

        Map<String, ItemSizesFilterResponseDto> category2ItemSizesFilterResponseDto = getCategory2ItemSizesFilter(
            itemSizes);

        return toSortedList(
            category2ItemSizesFilterResponseDto);
    }

    private Map<Character, BrandFilterResponseDto> getFirstLetter2BrandFilter(
        List<BrandDto> allBrand) {
        Map<Character, BrandFilterResponseDto> firstLetter2BrandFilterResponseDto = new HashMap<>();

        for (BrandDto brandDto : allBrand) {
            Character firstLetter = brandDto.getFirstLetter();

            addBrandOrDefault(firstLetter2BrandFilterResponseDto, brandDto, firstLetter);
        }
        return firstLetter2BrandFilterResponseDto;
    }

    private void addBrandOrDefault(
        Map<Character, BrandFilterResponseDto> firstLetter2BrandFilterResponseDto,
        BrandDto brandDto, Character firstLetter) {
        if (firstLetter2BrandFilterResponseDto.containsKey(firstLetter)) {
            firstLetter2BrandFilterResponseDto
                .get(firstLetter)
                .getBrandDtoList()
                .add(brandDto);
        } else {
            firstLetter2BrandFilterResponseDto.put(firstLetter,
                BrandFilterResponseDto.of(firstLetter, brandDto));
        }
    }

    private void addItemOrDefault(
        Map<String, ItemSizesFilterResponseDto> category2ItemSizesFilterResponseDto,
        ItemSizeDto itemSizeDto, String category) {
        if (category2ItemSizesFilterResponseDto.containsKey(category)) {
            category2ItemSizesFilterResponseDto
                .get(category)
                .getSizes()
                .add("size", itemSizeDto.getSize());
        } else {
            MultiValueMap<String, String> size2value = new LinkedMultiValueMap<>();
            size2value.add("size", itemSizeDto.getSize());

            category2ItemSizesFilterResponseDto.put(category,
                ItemSizesFilterResponseDto.of(category, size2value));
        }
    }


    private List<ItemSizesFilterResponseDto> toSortedList(
        Map<String, ItemSizesFilterResponseDto> category2ItemSizesFilterResponseDto) {
        List<ItemSizesFilterResponseDto> collect = category2ItemSizesFilterResponseDto
            .values()
            .stream()
            .collect(Collectors.toList());

        collect.forEach(dto -> dto.sort());
        return collect;
    }

    private Map<String, ItemSizesFilterResponseDto> getCategory2ItemSizesFilter(
        List<ItemSizeDto> itemSizes) {
        Map<String, ItemSizesFilterResponseDto> category2ItemSizesFilterResponseDto = new HashMap<>();

        for (ItemSizeDto itemSizeDto : itemSizes) {
            String category = itemSizeDto.getCategory();

            addItemOrDefault(category2ItemSizesFilterResponseDto, itemSizeDto, category);
        }

        return category2ItemSizesFilterResponseDto;
    }
}
