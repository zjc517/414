package com.fast.mall.mapper;

import com.fast.mall.domain.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 图书分类Mapper接口
 */
@Mapper
public interface CategoryMapper {

    /**
     * 查询图书分类列表
     * @param category 图书分类对象(查询参数)
     * @return 图书分类列表
     */
    List<Category> selectCategoryList(Category category);
}