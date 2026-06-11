package com.fast.mall.domain;

import java.math.BigDecimal;
import java.util.List;
import com.fast.system.general.core.domain.BaseEntity;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;

/**
 * 订单对象 order
 *
 * @author fast
 * @date 2025-12-12
 */

@Data
public class Order
{

    /** 订单号 */
    private String orderId;

    /** 订单总价 */
    private BigDecimal totalAmount;

    /** 收货人 */
    private String name;

    /** 联系电话 */
    private String phone;

    /** 收货地址 */
    private String address;

    /** 订单状态 */
    private String status;

    /** 下单用户ID */
    private Long userId;

    /** 订单创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 订单图书信息 */
    private List<OrderBook> orderBookList;

    //下单用户
    private String userName;


}