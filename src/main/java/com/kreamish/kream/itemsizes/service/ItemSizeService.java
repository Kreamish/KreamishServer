package com.kreamish.kream.itemsizes.service;

import com.kreamish.kream.itemsizes.dto.ItemSizeDto;
import com.kreamish.kream.itemsizes.entity.ItemSizes;
import com.kreamish.kream.itemsizes.repository.ItemSizesRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemSizeService {
    
    private final ItemSizesRepository itemSizesRepository;

    public List<ItemSizeDto> getItemSizes() {
        List<ItemSizes> allItemSizes = itemSizesRepository.findAll();

        return allItemSizes.stream()
            .map(itemSizes -> ItemSizeDto.of(itemSizes))
            .collect(Collectors.toSet()).stream()
            .collect(Collectors.toList());
    }
}
