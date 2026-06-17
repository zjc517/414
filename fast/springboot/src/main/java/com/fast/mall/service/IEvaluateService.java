package com.fast.mall.service;

import java.util.List;
import com.fast.mall.domain.Evaluate;

/**
 * 评价Service接口
 *
 * @author fast
 * @date 2025-12-14
 */
public interface IEvaluateService
{
    /**
     * 查询评价
     *
     * @param evaluateId 评价主键
     * @return 评价
     */
    public Evaluate selectEvaluateByEvaluateId(String evaluateId);

    /**
     * 查询评价列表
     *
     * @param evaluate 评价
     * @return 评价集合
     */
    public List<Evaluate> selectEvaluateList(Evaluate evaluate);

    /**
     * 新增评价
     *
     * @param evaluate 评价
     * @return 结果
     */
    public int insertEvaluate(Evaluate evaluate);

    /**
     * 修改评价
     *
     * @param evaluate 评价
     * @return 结果
     */
    public int updateEvaluate(Evaluate evaluate);

    /**
     * 批量删除评价
     *
     * @param evaluateIds 需要删除的评价主键集合
     * @return 结果
     */
    public int deleteEvaluateByEvaluateIds(String[] evaluateIds);
}
