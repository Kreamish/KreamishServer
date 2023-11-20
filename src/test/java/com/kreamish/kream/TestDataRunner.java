package com.kreamish.kream;

import com.kreamish.kream.brand.entity.Brand;
import com.kreamish.kream.brand.repository.BrandRepository;
import com.kreamish.kream.category.entity.Category;
import com.kreamish.kream.category.repository.CategoryRepository;
import com.kreamish.kream.categorydetail.entity.CategoryDetail;
import com.kreamish.kream.categorydetail.repository.CategoryDetailRepository;
import com.kreamish.kream.comment.entity.Comment;
import com.kreamish.kream.comment.repository.CommentRepository;
import com.kreamish.kream.common.entity.DealStatus;
import com.kreamish.kream.item.entity.Item;
import com.kreamish.kream.item.repository.ItemRepository;
import com.kreamish.kream.itemsizes.entity.ItemSizes;
import com.kreamish.kream.itemsizes.repository.ItemSizesRepository;
import com.kreamish.kream.member.entity.Member;
import com.kreamish.kream.member.entity.MemberRole;
import com.kreamish.kream.member.repository.MemberRepository;
import com.kreamish.kream.member.repository.MemberRoleRepository;
import com.kreamish.kream.sale.entity.Sale;
import com.kreamish.kream.sale.repository.SaleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("data & test")
@Component
@RequiredArgsConstructor
public class TestDataRunner implements ApplicationRunner {

    /*
        ToDo : 테스트가 끝나도 테스트 데이터가 메모리에 남아있는 문제.
     */
    public static Boolean isNotInitialized = true;
    public static MemberRole ROLE_MEMBER;
    public static MemberRole ROLE_ADMIN;
    public static Brand BRAND1_STARTED_WITH_CHAR_B;
    public static Brand BRAND2_STARTED_WITH_CHAR_B;
    public static Brand BRAND1_STARTED_WITH_CHAR_C;
    public static Brand BRAND1_STARTED_WITH_CHAR_D;
    public static Brand BRAND2_STARTED_WITH_CHAR_D;
    public static Brand BRAND1_STARTED_WITH_CHAR_E;

    public static Category CATEGORY1;
    public static CategoryDetail DETAIL1_WITH_CATEGORY1;
    public static Item ITEM1_WITH_BRAND1_CATEGORY1_DETAIL1_AND_COMMENT_CNT_IS_2;
    public static Item ITEM2_WITH_BRAND1_CATEGORY1_DETAIL1;
    public static ItemSizes ITEMSIZES_WITH_ITEM1;
    public static Sale SALE_WITH_ITEMSIZES_MEMBER1;
    public static Member MEMBER1;
    public static Comment COMMENT1_BY_ITEM1_MEMBER1;
    public static Comment COMMENT2_BY_ITEM1_MEMBER1;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final MemberRoleRepository memberRoleRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryDetailRepository categoryDetailRepository;
    private final CommentRepository commentRepository;
    private final ItemSizesRepository itemSizesRepository;
    private final SaleRepository saleRepository;

    @Override
    public void run(ApplicationArguments args) {
        if (isNotInitialized) {
            // memberRole
            ROLE_MEMBER = saveMemberRole("member");
            ROLE_ADMIN = saveMemberRole("admin");
            // brand
            BRAND1_STARTED_WITH_CHAR_B = saveBrand("b_brand1");
            BRAND2_STARTED_WITH_CHAR_B = saveBrand("b_brand2");
            BRAND1_STARTED_WITH_CHAR_C = saveBrand("c_brand1");
            BRAND1_STARTED_WITH_CHAR_D = saveBrand("d_brand1");
            BRAND2_STARTED_WITH_CHAR_D = saveBrand("d_brand2");
            BRAND1_STARTED_WITH_CHAR_E = saveBrand("e_brand1");
            // category
            CATEGORY1 = saveCategory("category1");
            // categoryDetail
            DETAIL1_WITH_CATEGORY1 = saveCategoryDetail(CATEGORY1, "categoryDetail1");
            // item
            ITEM1_WITH_BRAND1_CATEGORY1_DETAIL1_AND_COMMENT_CNT_IS_2 = saveItem("item1", "subName1",
                BRAND1_STARTED_WITH_CHAR_B, CATEGORY1,
                DETAIL1_WITH_CATEGORY1,
                "http://dummyImgUrl.com");
            ITEM2_WITH_BRAND1_CATEGORY1_DETAIL1 = saveItem("item2", "subName2",
                BRAND1_STARTED_WITH_CHAR_B, CATEGORY1,
                DETAIL1_WITH_CATEGORY1,
                "http://dummyImgUrl2.com");
            // member
            MEMBER1 = saveMember("dummyEmail", ROLE_MEMBER, "dummyPassword");
            // comment
            COMMENT1_BY_ITEM1_MEMBER1 = saveComment(
                ITEM1_WITH_BRAND1_CATEGORY1_DETAIL1_AND_COMMENT_CNT_IS_2, MEMBER1,
                "comment1");
            COMMENT2_BY_ITEM1_MEMBER1 = saveComment(
                ITEM1_WITH_BRAND1_CATEGORY1_DETAIL1_AND_COMMENT_CNT_IS_2, MEMBER1,
                "comment2");

            ITEMSIZES_WITH_ITEM1 = saveItemSizes(
                ITEM1_WITH_BRAND1_CATEGORY1_DETAIL1_AND_COMMENT_CNT_IS_2
            );

            SALE_WITH_ITEMSIZES_MEMBER1 = saveSale(
                ITEMSIZES_WITH_ITEM1,
                MEMBER1,
                10000L
            );

            isNotInitialized = !isNotInitialized;
        }
    }

    private Sale saveSale(ItemSizes itemSizes, Member member, Long price) {
        return saleRepository.save(
            Sale.builder()
                .salePrice(price)
                .itemSizes(itemSizes)
                .member(member)
                .saleStatus(DealStatus.PENDING)
                .build()
        );
    }

    private ItemSizes saveItemSizes(Item item) {
        return itemSizesRepository.save(
            ItemSizes.builder()
                .item(item)
                .size("size")
                .build()
        );
    }

    private Comment saveComment(Item item, Member member, String content) {
        return commentRepository.save(Comment.of(item, member, content));
    }

    private Member saveMember(String email, MemberRole roleMember, String password) {
        return memberRepository.save(Member.builder().memberRole(roleMember).email(email)
            .password(password).build());
    }

    private Item saveItem(String itemName, String itemSubName, Brand brand, Category category,
        CategoryDetail categoryDetail, String imgUrl) {
        return itemRepository.save(
            Item.of(itemName, itemSubName, brand, category, categoryDetail, imgUrl));
    }

    private CategoryDetail saveCategoryDetail(Category category, String categoryDetailName) {
        return categoryDetailRepository.save(CategoryDetail.of(category, categoryDetailName));
    }

    private Category saveCategory(String categoryName) {
        return categoryRepository.save(Category.of(categoryName));
    }

    private Brand saveBrand(String brandName) {
        return brandRepository.save(Brand.of(brandName));
    }

    private MemberRole saveMemberRole(String roleName) {
        return memberRoleRepository.save(MemberRole.builder().name(roleName).build());
    }
}