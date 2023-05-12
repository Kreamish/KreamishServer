package com.kreamish.kream.repository;

import com.kreamish.kream.entity.CategoryDetail;
import com.kreamish.kream.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryDetailRepository extends JpaRepository<CategoryDetail, Long>, ItemQueryRepository {
}
