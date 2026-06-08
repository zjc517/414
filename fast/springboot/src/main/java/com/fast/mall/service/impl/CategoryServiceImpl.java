package com.fast.mall.service.impl;

import com.fast.mall.domain.Category;
import com.fast.mall.domain.vo.CategoryVO;
import com.fast.mall.mapper.CategoryMapper;
import com.fast.mall.service.ICategoryService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * 图书分类Service业务层处理
 */
@Service
public class CategoryServiceImpl implements ICategoryService {
    @Resource
    private CategoryMapper categoryMapper;
    /**
     * 查询图书分类列表
     * @param category 图书分类对象(查询参数)
     * @return 图书分类列表
     */
    @Override
    public List<Category> selectCategoryList(Category category) {
        return categoryMapper.selectCategoryList(category);
    }
    /**
     * 获取图书分类详细信息
     * @param categoryId 图书分类主键
     * @return 图书分类
     */
    @Override
    public Category selectCategoryByCategoryId(String categoryId) {
        return categoryMapper.selectCategoryByCategoryId(categoryId);
    }

    /**
     * 新增图书分类
     * @param category 图书分类
     * @return 结果
     */
    @Override
    public int insertCategory(Category category) {
        //生成一个UUID并插入至对象中
        category.setCategoryId(String.valueOf(UUID.randomUUID()));
        return categoryMapper.insertCategory(category);
    }
    /**
     * 修改图书分类
     * @param category 图书分类
     * @return 结果
     */
    @Override
    public int updateCategory(Category category) {
        return categoryMapper.updateCategory(category);
    }

    /**
     * 批量删除图书分类
     * @param categoryIds 需要删除的图书分类ID集合
     * @return 结果
     */
    @Override
    public int deleteCategoryByCategoryIds(String[] categoryIds) {
        return categoryMapper.deleteCategoryByCategoryIds(categoryIds);
    }

    /**
     * 查询所有分类
     * @return 所有分类列表
     */
    @Override
    public List<CategoryVO> selectAllCategory() {
        return categoryMapper.selectAllCategory();
    }
}