package com.kreamish.kream.sale.repository;

import com.kreamish.kream.sale.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRepository extends JpaRepository<Sale, Long>, SaleQueryRepository {

}
