package com.kreamish.kream.purchase.repository;

import static com.kreamish.kream.TestDataRunner.ITEMSIZES_WITH_ITEM1;
import static com.kreamish.kream.TestDataRunner.MEMBER1;
import static com.kreamish.kream.TestDataRunner.ROLE_MEMBER;
import static org.assertj.core.api.Assertions.assertThat;

import com.kreamish.kream.common.entity.DealStatus;
import com.kreamish.kream.itemsizes.entity.ItemSizes;
import com.kreamish.kream.member.entity.Member;
import com.kreamish.kream.member.repository.MemberRepository;
import com.kreamish.kream.purchase.entity.Purchase;
import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
@ActiveProfiles({"test", "data"})
class PurchaseQueryRepositoryImplTest {

    @Autowired
    PurchaseRepository purchaseRepository;

    @Autowired
    MemberRepository memberRepository;

    ItemSizes itemSizes;
    Member member;
    Member another;
    List<Purchase> alreadyPersistedPurchase;

    @PostConstruct
    void afterConstructThenSaveEntities() {
        member = MEMBER1;
        itemSizes = ITEMSIZES_WITH_ITEM1;

        another = getAnotherTestMemberAndSave();

        alreadyPersistedPurchase = List.of(
            getTestPurchaseInstance(DealStatus.PENDING, another),
            getTestPurchaseInstance(DealStatus.COMPLETE, another),
            getTestPurchaseInstance(DealStatus.COMPLETE, member),
            getTestPurchaseInstance(DealStatus.PENDING, member)
        );

        memberRepository.save(another);
        purchaseRepository.saveAll(alreadyPersistedPurchase);
    }

    private Member getAnotherTestMemberAndSave() {
        Member anotherMember = Member.builder()
            .memberRole(ROLE_MEMBER)
            .email("re12io@naver.com")
            .password("password")
            .build();
        memberRepository.save(anotherMember);
        return anotherMember;
    }

    private Purchase getTestPurchaseInstance(DealStatus status, Member buyer) {
        return Purchase.builder()
            .purchaseStatus(status)
            .itemSizes(itemSizes)
            .member(buyer)
            .purchasePrice(new Random(System.currentTimeMillis()).nextLong())
            .build();
    }

    @Nested
    @DisplayName("findByMember querydsl 조회 테스트")
    class FIND_BY_MEMBER_TEST {

        static class FindByMemberTestCase {

            Boolean isComplete;
            Predicate<? super Purchase> expectedEvaluator;

            public FindByMemberTestCase(Boolean isComplete, Predicate<? super Purchase> expectedEvaluator) {
                this.isComplete = isComplete;
                this.expectedEvaluator = expectedEvaluator;
            }

            List<Purchase> getExpectedPurchases(List<Purchase> purchases, Member providedMember) {
                return purchases.stream()
                    .filter(expectedEvaluator)
                    .filter(i -> providedMember.equals(i.getMember()))
                    .collect(Collectors.toList());
            }
        }

        static Stream<FindByMemberTestCase> providePurchaseTestCase() {
            return Stream.of(
                new FindByMemberTestCase(null, purchase -> true),
                new FindByMemberTestCase(true,
                    purchase -> DealStatus.COMPLETE.equals(purchase.getPurchaseStatus())
                ),
                new FindByMemberTestCase(false,
                    purchase -> !DealStatus.COMPLETE.equals(purchase.getPurchaseStatus())
                )
            );
        }

        @ParameterizedTest
        @MethodSource("providePurchaseTestCase")
        @DisplayName("isComplete 여부에 따라, 필터링 되는 조건이 적용되어야 한다.")
        void FIND_BY_MEMBER_SHOULD_RETURN_FILTERED_BY_IS_COMPLETE_VALUE(FindByMemberTestCase findByMemberTestCase) {
            List<Purchase> result = purchaseRepository.findByMember(member, null);

            Long[] expectedPurchaseIds
                = findByMemberTestCase.getExpectedPurchases(alreadyPersistedPurchase, member)
                .stream()
                .map(Purchase::getPurchaseId)
                .toArray(Long[]::new);

            assertThat(result).isNotNull().extracting("purchaseId", Long.class)
                .contains(expectedPurchaseIds);
        }
    }
}