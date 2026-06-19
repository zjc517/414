package com.fast.mall.domain;

import java.math.BigDecimal;
import com.fast.system.general.core.domain.BaseEntity;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;

/**
 * 图书对象 book
 *
 * @author fast
 * @date 2025-12-10
 */

@Data
public class Book
{

    /** 图书ID */
    private String bookId;

    /** 分类ID */
    private String categoryId;

    /** 书名 */
    private String title;

    /** 作者 */
    private String author;

    /** 封面 */
    private String cover;

    /** 价格 */
    private BigDecimal price;

    /** 描述 */
    private String description;

    /** 上架时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    //图书分类
    private String name;

    //是否推荐
    private Boolean isRecommend;


}