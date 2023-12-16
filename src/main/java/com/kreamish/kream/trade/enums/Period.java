package com.kreamish.kream.trade.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Period {
    ONE_MONTH("1"),
    THREE_MONTHS("3"),
    SIX_MONTHS("6"),
    TWELVE_MONTHS("12"),
    ALL("all");

    private final String value;

    public static Period of(String value) {
        for (Period period : Period.values()) {
            if (period.value.equals(value)) {
                return period;
            }
        }

        throw new IllegalArgumentException("일치하는 기간이 존재하지 않습니다. 가능한 기간은 1,3,6,12 개월 및 전체 기간입니다");
    }
}
