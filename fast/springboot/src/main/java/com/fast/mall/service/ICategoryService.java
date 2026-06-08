package com.fast.mall.service;

import com.fast.mall.domain.Category;
import com.fast.mall.domain.vo.CategoryVO;

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

    /**
     * 获取图书分类详细信息
     * @param categoryId 图书分类主键
     * @return 图书分类
     */
    Category selectCategoryByCategoryId(String categoryId);

    /**
     * 新增图书分类
     * @param category 图书分类
     * @return 结果
     */
    int insertCategory(Category category);

    /**
     * 修改图书分类
     * @param category 图书分类
     * @return 结果
     */
    int updateCategory(Category category);

    /**
     * 批量删除图书分类
     * @param categoryIds 需要删除的图书分类ID集合
     * @return 结果
     */
    int deleteCategoryByCategoryIds(String[] categoryIds);

    /**
     * 查询所有分类
     * @return 所有分类列表
     */
    List<CategoryVO> selectAllCategory();
}