package com.kreamish.kream.item.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Getter
@Setter
public class ItemListResponseDto {
    @JsonProperty("item_pages")
    Page<ItemListResponsePageDto> itemPages;
}
