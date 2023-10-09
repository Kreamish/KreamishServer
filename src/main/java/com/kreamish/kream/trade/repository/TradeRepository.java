package com.kreamish.kream.trade.repository;

import com.kreamish.kream.trade.entity.Trade;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TradeRepository extends JpaRepository<Trade, Long>, TradeQueryRepository {

    @Query("select t from Trade t join fetch t.sale s join fetch s.itemSizes i "
        + "where i.itemSizesId in :itemSizesIds")
    List<Trade> findTradeByItemSizesIdList(List<Long> itemSizesIds);
}
