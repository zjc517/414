package com.fast.mall.domain;

import lombok.Data;

/**
 * 图书分类对象 category
 */
@Data
public class Category {
    //分类ID
    private String categoryId;
    //分类名称
    private String name;
    //排序
    private Long sort;
}