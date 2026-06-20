package com.fast.mall.domain.vo;

import lombok.Data;

/**
 * 销售图书前5VO
 */
@Data
public class BookTopFiveVO {
    private String title;
    private Integer total;
}