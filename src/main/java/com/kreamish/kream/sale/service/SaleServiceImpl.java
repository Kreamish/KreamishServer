package com.kreamish.kream.sale.service;

import com.kreamish.kream.login.LoginMemberInfo;
import com.kreamish.kream.sale.dto.BuyNowPriceDto;
import com.kreamish.kream.sale.entity.Sale;
import com.kreamish.kream.sale.repository.SaleRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;

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
