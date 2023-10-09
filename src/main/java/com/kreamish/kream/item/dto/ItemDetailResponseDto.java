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
    private String brandName;
    private String modelCode;
    private Long releasePrice;
    private LocalDate releaseDate;
    private String representativeColor;
}
