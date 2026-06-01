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
}