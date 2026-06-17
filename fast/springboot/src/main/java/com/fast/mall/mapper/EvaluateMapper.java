package com.fast.mall.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.fast.mall.domain.Evaluate;

/**
 * 评价Mapper接口
 *
 * @author fast
 * @date 2025-12-14
 */
@Mapper
public interface EvaluateMapper
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
     * @param evaluateIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEvaluateByEvaluateIds(String[] evaluateIds);
}
