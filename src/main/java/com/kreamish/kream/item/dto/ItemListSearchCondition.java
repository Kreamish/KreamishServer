package com.kreamish.kream.item.dto;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemListSearchCondition {
    private List<Long> categoryIds;
    private List<Long> brandIds;
    private List<Long> collectionIds;
    private Long minPrice;
    private Long maxPrice;
    private List<String> sizes;
}
