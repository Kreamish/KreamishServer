package com.kreamish.kream.sale.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PendingSaleResponseDto {

    private List<PendingSaleDto> pendingSaleDtoList;

    public static PendingSaleResponseDto of(List<PendingSaleDto> pendingSales) {
        return new PendingSaleResponseDto(pendingSales);
    }
}
