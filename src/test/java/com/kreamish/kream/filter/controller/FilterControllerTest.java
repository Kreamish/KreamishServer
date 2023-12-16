package com.kreamish.kream.filter.controller;

import static org.mockito.Mockito.doReturn;

import com.kreamish.kream.filter.dto.BrandFilterResponseDto;
import com.kreamish.kream.filter.dto.ItemSizesFilterResponseDto;
import com.kreamish.kream.filter.facade.FilterFacade;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.client.MockMvcWebTestClient;
import org.springframework.util.LinkedMultiValueMap;

@ExtendWith(MockitoExtension.class)
class FilterControllerTest {

    static List<ItemSizesFilterResponseDto> itemSizesFilterResponseDtoList;
    @Mock
    FilterFacade filterFacade;
    WebTestClient webTestClient;
    @InjectMocks
    FilterController filterController;

    @BeforeAll
    static void TEST_DATA_INIT() {
        ItemSizesFilterResponseDto itemSizesFilterResponseDto1 = ItemSizesFilterResponseDto.of("의류",
            new LinkedMultiValueMap<>() {
                {
                    add("size", "L");
                    add("size", "M");
                    add("size", "S");
                }
            });
        ItemSizesFilterResponseDto itemSizesFilterResponseDto2 = ItemSizesFilterResponseDto.of("신발",
            new LinkedMultiValueMap<>() {
                {
                    add("size", "210");
                    add("size", "220");
                    add("size", "225");
                }
            });
        itemSizesFilterResponseDtoList = new ArrayList<>() {{
            add(itemSizesFilterResponseDto1);
            add(itemSizesFilterResponseDto2);
        }};
    }

    @BeforeEach
    void SET_UP() {
        this.webTestClient = MockMvcWebTestClient
            .bindToController(filterController)
            .build();
    }

    @Test
    @DisplayName("성공: 모든 필터링 카테고리 목록 반환")
    void SUCCESS_SHOULD_RETURN_CATEGORY_DETAIL_LIST_FOR_FILTER() {
        doReturn(null).when(filterFacade).getCategories();

        webTestClient.get()
            .uri("/filters/categories")
            .exchange()
            .expectStatus().isOk()
            .expectBody()

            .jsonPath("$.success").isEqualTo(true)
            .jsonPath("$.error").doesNotExist();
    }

    @Test
    @DisplayName("성공: 모든 브랜드 리스트 반환")
    void SUCCESS_SHOULD_RETURN_BRAND_LIST_FOR_FILTER() {
        List<BrandFilterResponseDto> brandFilterResponseDtoList = Arrays.asList(
            new BrandFilterResponseDto("B",
                Collections.EMPTY_LIST), new BrandFilterResponseDto("B", Collections.EMPTY_LIST));

        doReturn(brandFilterResponseDtoList).when(filterFacade).getBrandFilterList();

        webTestClient.get()
            .uri("/filters/brand")
            .exchange()
            .expectStatus().isOk()
            .expectBody()

            .jsonPath("$.success").isEqualTo(true)

            .jsonPath("$.response.length()").isEqualTo(brandFilterResponseDtoList.size())

            .jsonPath("$.response[0].brandFirstLetter").hasJsonPath()

            .jsonPath("$.response[0].brandItems").hasJsonPath()

            .jsonPath("$.error").doesNotExist();
    }

    @Test
    @DisplayName("성공: 모든 컬렉션 리스트 반환")
    void SUCCESS_SHOULD_RETURN_COLLECTION_LIST_FOR_FILTER() {
        doReturn(null).when(filterFacade).getCollections();

        webTestClient.get()
            .uri("/filters/collections")
            .exchange()
            .expectStatus().isOk()
            .expectBody()

            .jsonPath("$.success").isEqualTo(true)
            .jsonPath("$.error").doesNotExist();
    }

    @Test
    @DisplayName("성공: 모든 아이템 사이즈 리스트 반환")
    void SUCCESS_SHOULD_RETURN_ITEM_SIZE_LIST_FOR_FILTER() {
        doReturn(itemSizesFilterResponseDtoList).when(filterFacade).getItemSizesFilterList();

        webTestClient.get()
            .uri("/filters/item-sizes")
            .exchange()
            .expectStatus().isOk()
            .expectBody()

            .jsonPath("$.success").isEqualTo(true)

            .jsonPath("$.response").isNotEmpty()

            .jsonPath("$.response.length()").isEqualTo(itemSizesFilterResponseDtoList.size())

            .jsonPath("$.response[0].sizeSection").hasJsonPath()

            .jsonPath("$.response[0].sizeItems").hasJsonPath()

            .jsonPath("$.error").doesNotExist();
    }
}