package com.kreamish.kream.item.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemListResponsePageDto {
    private Long itemId;
    private String brandName;
    private String name;
    private String subName;
    private Long recentPrice;
    private Long likeCount;
    private Long commentCount;
    private String imageUrl;
}
