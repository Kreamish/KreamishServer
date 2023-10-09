package com.kreamish.kream.price.facade;

import com.kreamish.kream.item.dto.RecentTradePriceInfoResponseDto;
import com.kreamish.kream.login.LoginMemberInfo;
import com.kreamish.kream.price.dto.TotalPriceInfoResponseDto;
import com.kreamish.kream.purchase.dto.SaleNowPriceDto;
import com.kreamish.kream.purchase.service.PurchaseService;
import com.kreamish.kream.sale.dto.BuyNowPriceDto;
import com.kreamish.kream.sale.service.SaleService;
import com.kreamish.kream.trade.service.TradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
@Transactional(readOnly = true)
public class PriceFacadeImpl implements PriceFacade {

    private final TradeService tradeService;
    private final PurchaseService purchaseService;
    private final SaleService saleService;

    @Override
    public TotalPriceInfoResponseDto getTotalPriceInfo(Long itemId, Long itemSizesId,
        LoginMemberInfo loginMemberInfo) {
        final RecentTradePriceInfoResponseDto recentTradePriceInfo = tradeService.getRecentTradePriceInfo(
            itemId, itemSizesId, loginMemberInfo);
        final SaleNowPriceDto saleNowPriceDto = purchaseService.getSaleNowPrice(
            itemId, itemSizesId, loginMemberInfo);
        final BuyNowPriceDto buyNowPriceDto = saleService.getBuyNowPrice(itemId,
            itemSizesId, loginMemberInfo);

        return TotalPriceInfoResponseDto.of(itemId, itemSizesId, recentTradePriceInfo,
            buyNowPriceDto,
            saleNowPriceDto);
    }
}
