package com.kreamish.kream.purchase.repository;

import com.kreamish.kream.purchase.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
}
