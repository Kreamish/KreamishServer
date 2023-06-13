package com.kreamish.kream.category.repository;

import com.kreamish.kream.category.entity.Category;
import com.kreamish.kream.legacy.repository.ItemQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long>, ItemQueryRepository {

}
