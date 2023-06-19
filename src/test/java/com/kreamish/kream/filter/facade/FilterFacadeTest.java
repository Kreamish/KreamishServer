package com.kreamish.kream.filter.facade;

import static org.assertj.core.api.Assertions.assertThat;

import com.kreamish.kream.brand.dto.BrandDto;
import com.kreamish.kream.brand.entity.Brand;
import com.kreamish.kream.brand.repository.BrandRepository;
import com.kreamish.kream.category.entity.Category;
import com.kreamish.kream.category.repository.CategoryRepository;
import com.kreamish.kream.categorydetail.entity.CategoryDetail;
import com.kreamish.kream.categorydetail.repository.CategoryDetailRepository;
import com.kreamish.kream.filter.dto.CategoriesFilterResultDto;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
//@TestPropertySource(properties = {"spring.config.location = classpath:application-local.yaml"})
class FilterFacadeTest {

    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    CategoryDetailRepository categoryDetailRepository;
    @Autowired
    FilterFacade filterFacade;
    @Autowired
    BrandRepository brandRepository;

    @Test
    void SUCCESS_SHOULD_GET_CATEGORIES() {
        //given
        List<Category> srcCategory = categoryRepository.findAll();
        List<CategoryDetail> srcCategoryDetail = categoryDetailRepository.findAll();

        //when
        List<CategoriesFilterResultDto> dstFilterResult = filterFacade.getCategories();

        int dstTotalCnt = dstFilterResult.stream()
            .mapToInt(filterResult -> filterResult.getSimpleCategoryDetailList()
                .size()).sum();

        //then
        assertThat(dstFilterResult).isNotNull();
        assertThat(dstFilterResult.size()).isEqualTo(srcCategory.size());
        assertThat(dstTotalCnt).isEqualTo(srcCategoryDetail.size());
    }

    @Test
    void SUCCESS_SHOULD_GET_BRAND() {
        //given
        List<Brand> srcBrand = brandRepository.findAll();
        List<BrandDto> srcBrandDto = srcBrand.stream()
            .map(brand -> BrandDto.of(brand))
            .collect(Collectors.toList());

        //when
        List<BrandDto> drcBrandDto = filterFacade.getBrand();

        //then
        assertThat(srcBrandDto).isNotNull();
        assertThat(srcBrandDto.size()).isEqualTo(drcBrandDto.size());
        assertThat(srcBrandDto).isEqualTo(drcBrandDto);
    }
}