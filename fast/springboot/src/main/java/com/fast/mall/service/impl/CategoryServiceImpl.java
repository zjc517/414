package com.fast.mall.service.impl;

import com.fast.mall.domain.Category;
import com.fast.mall.mapper.CategoryMapper;
import com.fast.mall.service.ICategoryService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

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
}