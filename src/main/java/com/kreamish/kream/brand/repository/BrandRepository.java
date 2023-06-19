package com.kreamish.kream.brand.repository;

import com.kreamish.kream.brand.entity.Brand;
import com.kreamish.kream.legacy.repository.ItemQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Long>, ItemQueryRepository {

}
