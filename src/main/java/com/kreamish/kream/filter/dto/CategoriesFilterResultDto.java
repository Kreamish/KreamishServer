package com.kreamish.kream.filter.dto;

import com.kreamish.kream.category.dto.CategoriesDto;
import com.kreamish.kream.categorydetail.dto.CategoryDetailsDto;

public class CategoryDetailFilterResultDto {
    /*
    Map
    상의: [스니커즈, 샌들]
    하의: 자켓, 아노락
    List<ResultDto> resultDto;

    ResultDto
    cateogry
    [
        {
            category id: 1
            category name: 상의
            category detail list: [
                { category detail name: 아노락, category detail id: 2},
                { category detail name: 아노락, category detail id: 2},
                { category detail name: 아노락, category detail id: 2},
                { category detail name: 아노락, category detail id: 2}]
        }

        {상의:1} : [ {아노락:1}, {셔츠:2}, {셔츠:2}, {셔츠:2}]
        {
            category_id: 1
            category name: 상의
            category detail list: [
                { category detail name: 아노락, category detail id: 2},
                { category detail name: 아노락, category detail id: 2},
                { category detail name: 아노락, category detail id: 2},
                { category detail name: 아노락, category detail id: 2}]
        }
        {
            category_id: 1
            category name: 상의
            category detail list: [
                { category detail name: 아노락, category detail id: 2},
                { category detail name: 아노락, category detail id: 2},
                { category detail name: 아노락, category detail id: 2},
                { category detail name: 아노락, category detail id: 2}]
        }

    Map<category name, List<map<category defatil id, category detail name>>>

     */

    public static CategoryDetailFilterResultDto of(CategoriesDto allCategories, CategoryDetailsDto allCategoryDetails) {

    }
}
