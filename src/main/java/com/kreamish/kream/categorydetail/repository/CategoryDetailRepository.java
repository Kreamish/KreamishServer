package com.kreamish.kream.categorydetail.repository;

import com.kreamish.kream.categorydetail.entity.CategoryDetail;
import com.kreamish.kream.repository.ItemQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryDetailRepository extends JpaRepository<CategoryDetail, Long>, ItemQueryRepository {
    List<CategoryDetail> findAllByCategoryId(List<Long> searchCategoryIdList);
}
