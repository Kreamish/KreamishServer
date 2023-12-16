package com.kreamish.kream.datarunner;

import static com.kreamish.kream.common.entity.DealStatus.COMPLETE;
import static com.kreamish.kream.common.entity.DealStatus.PENDING;
import static com.kreamish.kream.datarunner.DefaultData.ITEM_SIZES1_WITH_ITEM1;
import static com.kreamish.kream.datarunner.DefaultData.ITEM_SIZES2_WITH_ITEM1;
import static com.kreamish.kream.datarunner.DefaultData.ITEM_SIZES3_WITH_ITEM1;
import static com.kreamish.kream.datarunner.DefaultData.ITEM_SIZES4_WITH_ITEM1;
import static com.kreamish.kream.datarunner.DefaultData.ITEM_SIZES5_WITH_ITEM1;
import static com.kreamish.kream.datarunner.DefaultData.MEMBER1;
import static com.kreamish.kream.datarunner.DefaultData.MEMBER2;

import com.kreamish.kream.common.entity.DealStatus;
import com.kreamish.kream.itemsizes.entity.ItemSizes;
import com.kreamish.kream.member.entity.Member;
import com.kreamish.kream.purchase.entity.Purchase;
import com.kreamish.kream.purchase.repository.PurchaseRepository;
import com.kreamish.kream.sale.entity.Sale;
import com.kreamish.kream.sale.repository.SaleRepository;
import com.kreamish.kream.trade.entity.Trade;
import com.kreamish.kream.trade.repository.TradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Profile("test & default-data & trade-data")
@Component
@Order(2)
@RequiredArgsConstructor
public class TradeData implements ApplicationRunner {

    public static Purchase PURCHASE1;
    public static Purchase PURCHASE2;
    public static Purchase PURCHASE3;
    public static Purchase PURCHASE4;
    public static Purchase PURCHASE5;
    public static Purchase PURCHASE6;
    public static Purchase PURCHASE7;
    public static Purchase PURCHASE8;
    public static Purchase PURCHASE9;
    public static Purchase PURCHASE10;
    public static Sale SALE1;
    public static Sale SALE2;
    public static Sale SALE3;
    public static Sale SALE4;
    public static Sale SALE5;
    public static Sale SALE6_NOT_TRADED_AND_THE_LOWEST_PRICE;
    public static Sale SALE7_THE_HIGHEST_PRICE;
    public static Trade TRADE1_WITH_PURCHASE1_SALE1;
    public static Trade TRADE2_WITH_PURCHASE2_SALE2;
    public static Trade TRADE3_WITH_PURCHASE3_SALE3;
    public static Trade TRADE4_WITH_PURCHASE4_SALE4;
    public static Trade TRADE5_WITH_PURCHASE5_SALE5;
    public static int i = 0;
    private final PurchaseRepository purchaseRepository;
    private final SaleRepository saleRepository;
    private final TradeRepository tradeRepository;

    @Override
    public void run(ApplicationArguments args) {
        // purchase
        PURCHASE1 = savePurchase(ITEM_SIZES1_WITH_ITEM1, MEMBER1, 1000L, COMPLETE);
        PURCHASE2 = savePurchase(ITEM_SIZES2_WITH_ITEM1, MEMBER1, 2000L, COMPLETE);
        PURCHASE3 = savePurchase(ITEM_SIZES3_WITH_ITEM1, MEMBER1, 3000L, COMPLETE);
        PURCHASE4 = savePurchase(ITEM_SIZES4_WITH_ITEM1, MEMBER1, 4000L, COMPLETE);
        PURCHASE5 = savePurchase(ITEM_SIZES5_WITH_ITEM1, MEMBER1, 5000L, COMPLETE);

        PURCHASE6 = savePurchase(ITEM_SIZES1_WITH_ITEM1, MEMBER1, 5000L, PENDING);
        PURCHASE7 = savePurchase(ITEM_SIZES1_WITH_ITEM1, MEMBER1, 5000L, PENDING);
        PURCHASE8 = savePurchase(ITEM_SIZES1_WITH_ITEM1, MEMBER1, 7000L, PENDING);

        PURCHASE9 = savePurchase(ITEM_SIZES2_WITH_ITEM1, MEMBER1, 7000L, PENDING);
        PURCHASE10 = savePurchase(ITEM_SIZES2_WITH_ITEM1, MEMBER1, 9000L, PENDING);

        // sale
        SALE1 = saveSale(ITEM_SIZES1_WITH_ITEM1, MEMBER2, 1000L, COMPLETE);
        SALE2 = saveSale(ITEM_SIZES2_WITH_ITEM1, MEMBER2, 2000L, COMPLETE);
        SALE3 = saveSale(ITEM_SIZES3_WITH_ITEM1, MEMBER2, 3000L, COMPLETE);
        SALE4 = saveSale(ITEM_SIZES4_WITH_ITEM1, MEMBER2, 4000L, COMPLETE);
        SALE5 = saveSale(ITEM_SIZES5_WITH_ITEM1, MEMBER2, 5000L, COMPLETE);

        SALE6_NOT_TRADED_AND_THE_LOWEST_PRICE = saveSale(ITEM_SIZES1_WITH_ITEM1, MEMBER2, 1500L,
            PENDING);
        SALE7_THE_HIGHEST_PRICE = saveSale(ITEM_SIZES1_WITH_ITEM1, MEMBER2, 6000L, PENDING);

        // trade
        TRADE1_WITH_PURCHASE1_SALE1 = saveTrade(PURCHASE1, SALE1, PURCHASE1.getPurchasePrice());
        TRADE2_WITH_PURCHASE2_SALE2 = saveTrade(PURCHASE2, SALE2, PURCHASE2.getPurchasePrice());
        TRADE3_WITH_PURCHASE3_SALE3 = saveTrade(PURCHASE3, SALE3, PURCHASE3.getPurchasePrice());
        TRADE4_WITH_PURCHASE4_SALE4 = saveTrade(PURCHASE4, SALE4, PURCHASE4.getPurchasePrice());
        TRADE5_WITH_PURCHASE5_SALE5 = saveTrade(PURCHASE5, SALE5, PURCHASE5.getPurchasePrice());
    }

    private Purchase savePurchase(ItemSizes itemSizes, Member member, Long purchasePrice,
        DealStatus purchaseStatus) {
        return purchaseRepository.save(
            Purchase.of(itemSizes, member, purchasePrice, purchaseStatus));
    }

    private Sale saveSale(ItemSizes itemSizes, Member member, Long salePrice,
        DealStatus saleStatus) {
        return saleRepository.save(Sale.of(itemSizes, member, salePrice, saleStatus));
    }

    private Trade saveTrade(Purchase purchase, Sale sale, Long tradePrice) {
        return tradeRepository.save(Trade.of(purchase, sale, tradePrice));
    }
}
