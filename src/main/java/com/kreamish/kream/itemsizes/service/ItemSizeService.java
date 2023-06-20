package com.kreamish.kream.itemsizes.service;

import com.kreamish.kream.itemsizes.dto.ItemSizeDto;
import com.kreamish.kream.itemsizes.entity.ItemSizes;
import com.kreamish.kream.itemsizes.repository.ItemSizesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ItemSizeService {

    private final ItemSizesRepository itemSizesRepository;
    @Transactional(readOnly = true)
    public List<ItemSizeDto> getItemSizes() {
        List<ItemSizes> allItemSizes = itemSizesRepository.findAll();

        return allItemSizes.stream()
                .map(itemSizes -> ItemSizeDto.of(itemSizes))
                .collect(Collectors.toSet()).stream()
                .collect(Collectors.toList());
    }
}
