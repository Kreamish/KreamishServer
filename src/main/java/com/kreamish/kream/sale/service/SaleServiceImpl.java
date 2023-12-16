package com.kreamish.kream.sale.service;

import com.kreamish.kream.login.LoginMemberInfo;
import com.kreamish.kream.sale.dto.BuyNowPriceDto;
import com.kreamish.kream.sale.dto.PendingSaleDto;
import com.kreamish.kream.sale.dto.PendingSaleResponseDto;
import com.kreamish.kream.sale.entity.Sale;
import com.kreamish.kream.sale.repository.SaleRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;

    @Override
    public PendingSaleResponseDto getPendingSalesByItemId(Long itemId) {
        final List<PendingSaleDto> pendingSales = saleRepository.findSaleByDealStatusAndItemId(
            itemId);
        return PendingSaleResponseDto.of(pendingSales);
    }

    @Override
    public PendingSaleResponseDto getPendingSaleByItemSizesId(Long itemSizesId) {
        final List<PendingSaleDto> pendingSales = saleRepository.findSaleByDealStatusAndItemSizesId(
            itemSizesId);
        return PendingSaleResponseDto.of(pendingSales);
    }

    private boolean isNotLoggedInUserOrAllSizeBuyNowPrice(Long itemSizesId,
        LoginMemberInfo loginMemberInfo) {
        return loginMemberInfo.isNotLoggedIn() || (!loginMemberInfo.isNotLoggedIn()
            && itemSizesId == null);
    }

    @Override
    public BuyNowPriceDto getBuyNowPrice(Long itemId, Long itemSizesId,
        LoginMemberInfo loginMemberInfo) {
        Optional<Sale> theLowestPriceById =
            isNotLoggedInUserOrAllSizeBuyNowPrice(itemSizesId, loginMemberInfo) ?
                saleRepository.findTheLowestPriceByItemId(itemId) :
                saleRepository.findTheLowestPriceByItemSizesId(itemSizesId);

        return theLowestPriceById.isPresent() ?
            BuyNowPriceDto.of(theLowestPriceById.get()) :
            BuyNowPriceDto.empty();
    }
}
