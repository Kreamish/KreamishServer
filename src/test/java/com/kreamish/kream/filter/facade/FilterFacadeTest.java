package com.kreamish.kream.filter.facade;

import com.kreamish.kream.category.dto.CategoryDto;
import com.kreamish.kream.category.entity.Category;
import com.kreamish.kream.category.repository.CategoryRepository;
import com.kreamish.kream.categorydetail.dto.CategoryDetailDto;
import com.kreamish.kream.categorydetail.entity.CategoryDetail;
import com.kreamish.kream.categorydetail.repository.CategoryDetailRepository;
import com.kreamish.kream.filter.dto.CategoriesFilterResultDto;
import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
//@TestPropertySource(properties = {"spring.config.location = classpath:application-local.yaml"})
class FilterFacadeTest {

    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    CategoryDetailRepository categoryDetailRepository;
    @Autowired
    FilterFacade filterFacade;
    List<CategoryDto> categoryDtoList = new ArrayList<>();
    List<CategoryDetailDto> categoryDetailDtoList = new ArrayList<>();

    @Test
    void getCategories() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryDetail> categoryDetails = categoryDetailRepository.findAll();

        List<CategoriesFilterResultDto> filterResultList = filterFacade.getCategories();

        int totalCategoryDetailCnt = filterResultList.stream()
            .mapToInt(filterResult -> filterResult.getSimpleCategoryDetailList()
                .size()).sum();

        Assertions.assertThat(filterResultList).isNotNull();
        Assertions.assertThat(filterResultList.size()).isEqualTo(categories.size());
        Assertions.assertThat(totalCategoryDetailCnt).isEqualTo(categoryDetails.size());
    }
}