package com.kreamish.kream;

import com.kreamish.kream.category.dto.CategoriesDto;
import com.kreamish.kream.category.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {"spring.config.location = classpath:application-local.yaml"})
class KreamApplicationTests {

    @Autowired
    CategoryService categoryService;
    @Test
    void contextLoads() {
        CategoriesDto items = categoryService.getAllCategories();
        System.out.println(items);

    }

}
