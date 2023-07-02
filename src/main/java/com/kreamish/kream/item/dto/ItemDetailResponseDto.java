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
    private Long recentPrice;
    private String modelCode;
    private LocalDate releaseDate;
    private String representativeColor;
}
