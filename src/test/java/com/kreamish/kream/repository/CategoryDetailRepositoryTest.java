package com.kreamish.kream.repository;

import com.kreamish.kream.entity.Category;
import com.kreamish.kream.entity.CategoryDetail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class CategoryDetailRepositoryTest {
    String categoryName = "test category name";
    String categoryDetailName = "test category_detail name";
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    CategoryDetailRepository categoryDetailRepository;
    @BeforeEach
    void setup() {
        categoryDetailRepository.deleteAll();
        categoryRepository.deleteAll();
    }
    @Test
    @DisplayName("성공: create category")
    void SUCCESS_CREATE_CATEGORY() {
        Category category = Category.of(categoryName);
        Category savedCategory = categoryRepository.save(category);

        CategoryDetail categoryDetail = CategoryDetail.of(savedCategory,categoryDetailName);
        CategoryDetail savedCategoryDetail = categoryDetailRepository.save(categoryDetail);

        assertThat(savedCategoryDetail).isNotNull();
        assertThat(savedCategoryDetail).isEqualTo(categoryDetail);
    }
    @Test
    @DisplayName("실패: create category. duplicated category_detail name")
    void FAIL_CREATE_CATEGORY() {
        //given
        Category category = Category.of(categoryName);
        Category savedCategory = categoryRepository.save(category);

        CategoryDetail categoryDetail = CategoryDetail.of(savedCategory,categoryDetailName);
        categoryDetailRepository.save(categoryDetail);

        CategoryDetail duplicatedCategoryDetail = CategoryDetail.of(savedCategory,categoryDetailName);
        //when
        assertThatThrownBy(() ->
                categoryDetailRepository.save(duplicatedCategoryDetail))
                .isInstanceOf(DataIntegrityViolationException.class);
    }
}