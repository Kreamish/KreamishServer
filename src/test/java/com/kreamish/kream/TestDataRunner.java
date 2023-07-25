package com.kreamish.kream;

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
import com.kreamish.kream.member.entity.Member;
import com.kreamish.kream.member.entity.MemberRole;
import com.kreamish.kream.member.repository.MemberRepository;
import com.kreamish.kream.member.repository.MemberRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("data & test")
@Component
@RequiredArgsConstructor
public class TestDataRunner implements ApplicationRunner {

    public static MemberRole ROLE_MEMBER;
    public static MemberRole ROLE_ADMIN;
    public static Brand BRAND1;
    public static Category CATEGORY1;
    public static CategoryDetail DETAIL1_WITH_CATEGORY1;
    public static Item ITEM1_WITH_BRAND1_CATEGORY1_DETAIL1_AND_COMMENT_CNT_IS_2;
    public static Item ITEM2_WITH_BRAND1_CATEGORY1_DETAIL1;
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

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // memberRole
        ROLE_MEMBER = saveMemberRole("member");
        ROLE_ADMIN = saveMemberRole("admin");
        // brand
        BRAND1 = saveBrand("brand1");
        // category
        CATEGORY1 = saveCategory("category1");
        // categoryDetail
        DETAIL1_WITH_CATEGORY1 = saveCategoryDetail(CATEGORY1, "categoryDetail1");
        // item
        ITEM1_WITH_BRAND1_CATEGORY1_DETAIL1_AND_COMMENT_CNT_IS_2 = saveItem("item1", "subName1",
            BRAND1, CATEGORY1,
            DETAIL1_WITH_CATEGORY1,
            "http://dummyImgUrl.com");
        ITEM2_WITH_BRAND1_CATEGORY1_DETAIL1 = saveItem("item2", "subName2", BRAND1, CATEGORY1,
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