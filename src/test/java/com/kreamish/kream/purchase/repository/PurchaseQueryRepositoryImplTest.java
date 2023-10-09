package com.kreamish.kream.purchase.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PurchaseQueryRepositoryImplTest {

    @Autowired
    PurchaseRepository purchaseRepository;

    @Test
    void findTheHighestPriceByItemId() {
        purchaseRepository.findTheHighestPriceByItemId(1L);
    }
}