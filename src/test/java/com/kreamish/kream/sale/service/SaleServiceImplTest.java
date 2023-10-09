package com.kreamish.kream.sale.service;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.kreamish.kream.login.LoginMemberInfo;
import com.kreamish.kream.sale.entity.Sale;
import com.kreamish.kream.sale.repository.SaleRepository;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class SaleServiceImplTest {

    @Mock
    SaleRepository saleRepository;
    @InjectMocks
    SaleServiceImpl saleServiceImpl;

    private static Stream<Arguments> provideArgs() {
        return Stream.of(
            Arguments.of(1L, null, 1, 0),
            Arguments.of(0L, null, 1, 0),
            Arguments.of(1L, 1L, 0, 1)
        );
    }

    @ParameterizedTest
    @DisplayName("로그인 유무 및 아이템 id 제공 여부 조건문 참/불 판별 테스트")
    @MethodSource("provideArgs")
    void ONLY_RETURN_TRUE_WHEN_LOGGED_IN_TRUE_AND_ITEM_SIZES_ID_NOT_NULL(Long MemberId,
        Long itemSizesId, int findByItemCnt, int findByItemSizesCnt) {
        final Long anyItemId = 1L;
        final Optional<Sale> anyLowestPrice = Optional.empty();
        final LoginMemberInfo loginMemberinfo = new LoginMemberInfo(MemberId);

        when(saleRepository.findTheLowestPriceByItemId(anyLong())).thenReturn(anyLowestPrice);
        when(saleRepository.findTheLowestPriceByItemSizesId(anyLong())).thenReturn(anyLowestPrice);

        saleServiceImpl.getBuyNowPrice(anyItemId, itemSizesId, loginMemberinfo);

        verify(saleRepository, times(findByItemCnt)).findTheLowestPriceByItemId(anyLong());
        verify(saleRepository, times(findByItemSizesCnt)).findTheLowestPriceByItemSizesId(
            anyLong());
    }

}