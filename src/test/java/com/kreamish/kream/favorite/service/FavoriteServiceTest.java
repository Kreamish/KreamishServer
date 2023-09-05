package com.kreamish.kream.favorite.service;

import static com.kreamish.kream.TestDataRunner.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.kreamish.kream.favorite.dto.ItemFavoriteResponseDto;
import com.kreamish.kream.favorite.dto.MemberFavoriteListResponseDto;
import com.kreamish.kream.favorite.enity.Favorite;
import com.kreamish.kream.item.entity.Item;
import com.kreamish.kream.itemsizes.entity.ItemSizes;
import com.kreamish.kream.member.entity.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@ActiveProfiles({"test", "data"})
class FavoriteServiceTest {

    @MockBean
    private FavoriteService favoriteService;

    @PersistenceContext
    private EntityManager em;

    @Test
    @DisplayName("성공: 기본 관심 상품 생성")
    void SUCCESS_NORMAL_CREATE() {
        //given
        Member member = MEMBER2;
        ItemSizes itemSizes = ITEMSIZES1_WITH_ITEM2;

        //when
        Long favoriteId = favoriteService.register(member.getMemberId(), itemSizes.getItemSizesId());

        //then
        Favorite findFavorite = em.find(Favorite.class, favoriteId);
        assertThat(findFavorite)
            .isNotNull();
    }

    @Test
    @DisplayName("실패: 이미 존재하는 관심 상품")
    void FAILED_CREATE_ALREADY_EXISTS_ITEMSIZES_FAVORITE() {
        //given: FAVORITE1_WITH_MEMBER1_ITEMSIZES1
        Member member = MEMBER1;
        ItemSizes itemSizes = ITEMSIZES1_WITH_ITEM2;

        //when, then
        assertThrows(
            IllegalArgumentException.class,
            () -> favoriteService.register(member.getMemberId(), itemSizes.getItemSizesId())
        );
    }

    @Test
    @DisplayName("성공: 단순 사용자의 관심 상품 찾기")
    void SUCCESS_NORMAL_INQUIRY_BY_MEMBER() {
        //given
        Member member = MEMBER1;
        ItemSizes itemSizes = ITEMSIZES1_WITH_ITEM2;
        Favorite favorite1 = FAVORITE1_WITH_MEMBER1_ITEMSIZES1;
        Favorite favorite2 = FAVORITE2_WITH_MEMBER1_ITEMSIZES2;

        //when
        MemberFavoriteListResponseDto favoriteDto = favoriteService.inQueryByMemberId(member.getMemberId());

        //then
        assertThat(favoriteDto).isNotNull()
            .extracting("favoriteDetailList").isNotNull();
        assertThat(favoriteDto.getFavoriteDetailList().size()).isEqualTo(1);

        assertThat(favoriteDto.getFavoriteDetailList())
            .extracting("favoriteId")
            .containsExactly(favorite1.getFavoriteId(), favorite2.getFavoriteId());
    }

    @Test
    @DisplayName("성공: 관심 상품 제거")
    void SUCCESS_NORMAL_DELETE() {
        //given
        Favorite favorite = FAVORITE1_WITH_MEMBER1_ITEMSIZES1;

        //when
        favoriteService.remove(favorite.getFavoriteId());

        //then
        assertThat(em.find(Favorite.class, favorite.getFavoriteId())).isNull();
    }

    @Test
    @DisplayName("실패: 존재하지 않는 FAVORITE 에 대한 상품 제거")
    void FAILED_DELETE_NOT_EXISTS_FAVORITE() {
        //given
        Long notExistsFavoriteId = 98765412345L;

        //when, then
        assertThrows(
            NoSuchElementException.class,
            () -> favoriteService.remove(notExistsFavoriteId)
        );
    }

    @Test
    @DisplayName("성공: 상품으로 관심 상품 조회")
    void SUCCESS_NORMAL_INQUIRY_BY_ITEM() {
        //given
        Item item = ITEM2_WITH_BRAND1_CATEGORY1_DETAIL1;

        Favorite favorite1 = FAVORITE1_WITH_MEMBER1_ITEMSIZES1;
        Favorite favorite2 = FAVORITE2_WITH_MEMBER1_ITEMSIZES2;

        //when
        ItemFavoriteResponseDto favoriteDto = favoriteService.inQueryByItemId(item.getItemId());

        //then
        assertThat(favoriteDto).isNotNull()
            .extracting("favoriteDetailList").isNotNull();
        assertThat(favoriteDto.getFavoriteDetailList()).size().isEqualTo(1);

        assertThat(favoriteDto.getFavoriteDetailList())
            .extracting("favoriteId")
            .containsExactly(favorite1.getFavoriteId(), favorite2.getFavoriteId());
    }
}