package com.fast.mall.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fast.mall.mapper.EvaluateMapper;
import com.fast.mall.domain.Evaluate;
import com.fast.mall.service.IEvaluateService;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.util.CollectionUtils;

import static com.fast.system.general.utils.SecurityUtils.getUserId;

/**
 * 评价Service业务层处理
 *
 * @author fast
 * @date 2025-12-14
 */
@Service
public class EvaluateServiceImpl implements IEvaluateService
{
    @Resource
    private EvaluateMapper evaluateMapper;

    /**
     * 查询评价
     *
     * @param evaluateId 评价主键
     * @return 评价
     */
    @Override
    public Evaluate selectEvaluateByEvaluateId(String evaluateId)
    {
        return evaluateMapper.selectEvaluateByEvaluateId(evaluateId);
    }

    /**
     * 查询评价列表
     *
     * @param evaluate 评价
     * @return 评价
     */
    @Override
    public List<Evaluate> selectEvaluateList(Evaluate evaluate)
    {
        return evaluateMapper.selectEvaluateList(evaluate);
    }

    /**
     * 新增评价
     *
     * @param evaluate 评价
     * @return 结果
     */
    @Override
    public int insertEvaluate(Evaluate evaluate) {
        evaluate.setCreateTime(new Date());
        evaluate.setEvaluateId(String.valueOf(UUID.randomUUID()));
        evaluate.setUserId(getUserId());
        return evaluateMapper.insertEvaluate(evaluate);
    }

    /**
     * 修改评价
     *
     * @param evaluate 评价
     * @return 结果
     */
    @Override
    public int updateEvaluate(Evaluate evaluate)
    {
        return evaluateMapper.updateEvaluate(evaluate);
    }

    /**
     * 批量删除评价
     *
     * @param evaluateIds 需要删除的评价主键
     * @return 结果
     */
    @Override
    public int deleteEvaluateByEvaluateIds(String[] evaluateIds)
    {
        return evaluateMapper.deleteEvaluateByEvaluateIds(evaluateIds);
    }
}