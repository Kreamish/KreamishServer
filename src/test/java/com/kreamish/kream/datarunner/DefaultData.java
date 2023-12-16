package com.kreamish.kream.datarunner;

import com.kreamish.kream.brand.entity.Brand;
import com.kreamish.kream.brand.repository.BrandRepository;
import com.kreamish.kream.category.entity.Category;
import com.kreamish.kream.category.repository.CategoryRepository;
import com.kreamish.kream.categorydetail.entity.CategoryDetail;
import com.kreamish.kream.categorydetail.repository.CategoryDetailRepository;
import com.kreamish.kream.comment.entity.Comment;
import com.kreamish.kream.comment.repository.CommentRepository;
import com.kreamish.kream.item.entity.Item;
import com.kreamish.kream.item.repository.ItemRepository;
import com.kreamish.kream.itemsizes.entity.ItemSizes;
import com.kreamish.kream.itemsizes.repository.ItemSizesRepository;
import com.kreamish.kream.member.entity.Member;
import com.kreamish.kream.member.entity.MemberRole;
import com.kreamish.kream.member.repository.MemberRepository;
import com.kreamish.kream.member.repository.MemberRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Profile("test & default-data")
@Component
@RequiredArgsConstructor
@Order(1)
public class DefaultData implements ApplicationRunner {

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
    public static ItemSizes ITEM_SIZES1_WITH_ITEM1;
    public static ItemSizes ITEM_SIZES2_WITH_ITEM1;
    public static ItemSizes ITEM_SIZES3_WITH_ITEM1;
    public static ItemSizes ITEM_SIZES4_WITH_ITEM1;
    public static ItemSizes ITEM_SIZES5_WITH_ITEM1;
    public static Member MEMBER1;
    public static Member MEMBER2;
    public static Comment COMMENT1_BY_ITEM1_MEMBER1;
    public static Comment COMMENT2_BY_ITEM1_MEMBER1;
    public static int i = 0;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final MemberRoleRepository memberRoleRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryDetailRepository categoryDetailRepository;
    private final CommentRepository commentRepository;
    private final ItemSizesRepository itemSizesRepository;

    @Override
    public void run(ApplicationArguments args) {
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
        // itemSizes
        ITEM_SIZES1_WITH_ITEM1 = saveItemSizes("245",
            ITEM1_WITH_BRAND1_CATEGORY1_DETAIL1_AND_COMMENT_CNT_IS_2);
        ITEM_SIZES2_WITH_ITEM1 = saveItemSizes("255",
            ITEM1_WITH_BRAND1_CATEGORY1_DETAIL1_AND_COMMENT_CNT_IS_2);
        ITEM_SIZES3_WITH_ITEM1 = saveItemSizes("265",
            ITEM1_WITH_BRAND1_CATEGORY1_DETAIL1_AND_COMMENT_CNT_IS_2);
        ITEM_SIZES4_WITH_ITEM1 = saveItemSizes("275",
            ITEM1_WITH_BRAND1_CATEGORY1_DETAIL1_AND_COMMENT_CNT_IS_2);
        ITEM_SIZES5_WITH_ITEM1 = saveItemSizes("285",
            ITEM1_WITH_BRAND1_CATEGORY1_DETAIL1_AND_COMMENT_CNT_IS_2);
        // member
        MEMBER1 = saveMember("kang@naver.com", ROLE_MEMBER, "dummyPassword");
        MEMBER2 = saveMember("tmddn645@naver.com", ROLE_MEMBER, "dummyPassword");

        // comment
        COMMENT1_BY_ITEM1_MEMBER1 = saveComment(
            ITEM1_WITH_BRAND1_CATEGORY1_DETAIL1_AND_COMMENT_CNT_IS_2, MEMBER1,
            "comment1");
        COMMENT2_BY_ITEM1_MEMBER1 = saveComment(
            ITEM1_WITH_BRAND1_CATEGORY1_DETAIL1_AND_COMMENT_CNT_IS_2, MEMBER1,
            "comment2");
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

    private ItemSizes saveItemSizes(String size, Item item) {
        return itemSizesRepository.save(ItemSizes.of(size, item));
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