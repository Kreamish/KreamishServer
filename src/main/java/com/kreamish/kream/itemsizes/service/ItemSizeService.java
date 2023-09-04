package com.kreamish.kream.itemsizes.service;

import com.kreamish.kream.itemsizes.dto.ItemSizeDto;
import com.kreamish.kream.itemsizes.entity.ItemSizes;
import java.util.List;
import java.util.Optional;

public interface ItemSizeService {

    List<ItemSizeDto> getItemSizes();

    Optional<ItemSizes> findById(Long itemSizesId);
}
