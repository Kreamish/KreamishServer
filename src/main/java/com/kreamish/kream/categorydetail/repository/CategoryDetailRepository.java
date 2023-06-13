package com.kreamish.kream.categorydetail.repository;

import com.kreamish.kream.categorydetail.entity.CategoryDetail;
import com.kreamish.kream.legacy.repository.ItemQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryDetailRepository extends JpaRepository<CategoryDetail, Long>,
    ItemQueryRepository {

}
