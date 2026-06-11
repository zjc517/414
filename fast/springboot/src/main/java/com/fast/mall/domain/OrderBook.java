package com.fast.mall.domain;

import java.math.BigDecimal;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;

/**
 * 订单图书对象 order_book
 *
 * @author fast
 * @date 2025-12-12
 */
@Data
public class OrderBook
{
    /** 订单图书ID */
    private Long orderBookId;

    /** 订单ID */
    private String orderId;

    /** 图书ID */
    private String bookId;

    /** 图书名称 */
    private String title;

    /** 作者 */
    private String author;

    /** 封面图片 */
    private String cover;

    /** 价格 */
    private BigDecimal price;

    /** 数量 */
    private Long quantity;


}