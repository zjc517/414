package com.fast.mall.domain;

import com.fast.system.general.core.domain.BaseEntity;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 购物车对象 cart
 *
 * @author fast
 * @date 2025-12-10
 */

@Data
public class Cart
{

    /** 购物车ID */
    private String cartId;

    /** 图书ID */
    private String bookId;

    /** 数量 */
    private Long quantity;

    /** 用户ID */
    private Long userId;

    /** 加入购物车时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 书名 */
    private String title;
    /** 作者 */
    private String author;
    /** 封面 */
    private String cover;
    /** 价格 */
    private BigDecimal price;
    /** 用户名 */
    private String userName;
}