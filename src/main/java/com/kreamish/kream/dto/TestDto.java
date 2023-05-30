package com.kreamish.kream.dto;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TestDto {

    @Parameter(
        name = "이름",
        description = "찾고자 하는 상품 이름",
        required = true
    )
    private String name;
}
