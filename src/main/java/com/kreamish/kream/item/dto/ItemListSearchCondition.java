package com.kreamish.kream.item.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemListSearchCondition {
    @JsonProperty("category-ids")
    private List<Long> categoryIds;
    @JsonProperty("brand-ids")
    private List<Long> brandIds;
    @JsonProperty("collection-ids")
    private List<Long> collectionIds;
    @JsonProperty("min-price")
    private Long minPrice;
    @JsonProperty("max-price")
    private Long maxPrice;
    @JsonProperty("sizes")
    private List<String> sizes;
    @JsonProperty("page")
    private Integer page;
    @JsonProperty("page-size")
    private Integer pageSize;
}
