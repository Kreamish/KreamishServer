package com.kreamish.kream.repository;

import com.kreamish.kream.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long>, ItemQueryRepository {

}
