package com.fast.mall.service;

import com.fast.mall.domain.Category;

import java.util.List;

/**
 * 图书分类Service接口
 */
public interface ICategoryService {
    /**
     * 查询图书分类列表
     * @param category 图书分类对象(查询参数)
     * @return 图书分类列表
     */
    List<Category> selectCategoryList(Category category);
}