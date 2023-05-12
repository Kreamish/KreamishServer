package com.kreamish.kream.repository;

import com.kreamish.kream.entity.Brand;
import com.kreamish.kream.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Long>, ItemQueryRepository {
}
