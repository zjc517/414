package com.fast.mall.domain;

import com.fast.system.general.core.domain.BaseEntity;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;

/**
 * 评价对象 evaluate
 *
 * @author fast
 * @date 2025-12-14
 */

@Data
public class Evaluate
{
    //图书名称
    private String title;
    //评价用户
    private String userName;
    /** 评价ID */
    private String evaluateId;

    /** 图书ID */
    private String bookId;

    /** 评分 */
    private Long rating;

    /** 评价内容 */
    private String content;

    /** 评价用户ID */
    private Long userId;

    /** 评价时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;


}