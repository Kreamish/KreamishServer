package com.kreamish.kream.item.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemDetailResponseDto {
    private String name;
    private String subName;
    // ToDo : item detail에서 브랜드 or 컬렉션으로 검색하는 기능.
    private String brandName;
    private String modelCode;
    private Long releasePrice;
    private LocalDate releaseDate;
    private String representativeColor;
}
