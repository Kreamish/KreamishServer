package com.kreamish.kream.trade.converter;

import com.kreamish.kream.trade.enums.Period;
import org.springframework.core.convert.converter.Converter;

public class PeriodConverter implements Converter<String, Period> {

    @Override
    public Period convert(String source) {
        return Period.of(source);
    }
}
