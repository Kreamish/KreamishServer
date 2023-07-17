package com.kreamish.kream.common.runner;

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
        MemberRole roleMember = saveMemberRole("member");
        MemberRole roleAdmin = saveMemberRole("admin");

        // brand
        Brand brand1 = saveBrand("brand1");

        // category
        Category category1 = saveCategory("category1");

        // categoryDetail
        CategoryDetail categoryDetail1 = saveCategoryDetail(category1, "categoryDetail1");

        // item
        Item item1 = saveItem("item1", "subName1", brand1, category1, categoryDetail1,
            "dummyImgUrl1");
        Item item2 = saveItem("item2", "subName2", brand1, category1, categoryDetail1,
            "dummyImgUrl2");

        // member
        Member member1 = saveMember("dummyEmail", roleMember, "dummyPassword");

        // comment
        Comment comment1 = saveComment(item1, member1, "comment1");
        Comment comment2 = saveComment(item1, member1, "comment2");
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