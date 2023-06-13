package com.kreamish.kream.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.kreamish.kream.legacy.entity.Brand;
import com.kreamish.kream.legacy.repository.BrandRepository;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

@SpringBootTest
class BrandRepositoryTest {

    String name = "test brand name";

    @Autowired
    BrandRepository brandRepository;

    @BeforeEach
    void setup() {
        brandRepository.deleteAll();
    }

    @Test
    @DisplayName("성공: create brand")
    void SUCCESS_CREATE_BRAND() {
        //given
        Brand brand = Brand.of(name);

        //when
        Brand saved = brandRepository.save(brand);
        Optional<Brand> found = brandRepository.findById(saved.getBrandId());

        //then
        assertThat(found.isPresent()).isTrue();
        assertThat(found.get()).isEqualTo(saved);
    }

    @Test
    @DisplayName("실패: create brand. duplicated brand name")
    void FAIL_CREATE_BRAND() {
        //given
        Brand brand = Brand.of(name);
        Brand duplicatedBrand = Brand.of(name);

        //when
        Brand saved = brandRepository.save(brand);

        //then
        Assertions.assertThatThrownBy(() ->
                brandRepository.save(duplicatedBrand))
            .isInstanceOf(DataIntegrityViolationException.class);
    }
}