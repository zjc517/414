package com.fast.mall.domain;

import com.fast.system.general.core.domain.BaseEntity;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;

/**
 * 收货地址对象 address
 *
 * @author fast
 * @date 2025-12-11
 */

@Data
public class Address
{

    /** 收货地址ID */
    private String addressId;

    /** 收货人 */
    private String name;

    /** 联系电话 */
    private String phone;

    /** 收货地址 */
    private String detail;

    /** 用户ID */
    private Long userId;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    //用户名
    private String userName;

}