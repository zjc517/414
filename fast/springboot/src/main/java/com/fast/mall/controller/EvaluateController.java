package com.fast.mall.controller;

import java.util.List;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fast.system.general.core.controller.BaseController;
import com.fast.system.general.core.domain.AjaxResult;

import com.fast.mall.domain.Evaluate;
import com.fast.mall.service.IEvaluateService;
import com.fast.system.general.core.page.TableDataInfo;

/**
 * 评价Controller
 *
 * @author fast
 * @date 2025-12-14
 */
@RestController
@RequestMapping("/mall/evaluate")
public class EvaluateController extends BaseController {
    @Resource
    private IEvaluateService evaluateService;

    /**
     * 查询评价列表
     */
    @GetMapping("/list")
    public TableDataInfo list(Evaluate evaluate) {
        startPage();
        List<Evaluate> list = evaluateService.selectEvaluateList(evaluate);
        return getDataTable(list);
    }

    /**
     * 获取评价详细信息
     */
    @GetMapping(value = "/{evaluateId}")
    public AjaxResult getInfo(@PathVariable("evaluateId") String evaluateId) {
        return success(evaluateService.selectEvaluateByEvaluateId(evaluateId));
    }

    /**
     * 新增评价
     */
    @PostMapping
    public AjaxResult add(@RequestBody Evaluate evaluate) {
        return toAjax(evaluateService.insertEvaluate(evaluate));
    }

    /**
     * 修改评价
     */
    @PutMapping
    public AjaxResult edit(@RequestBody Evaluate evaluate) {
        return toAjax(evaluateService.updateEvaluate(evaluate));
    }

    /**
     * 删除评价
     */
    @DeleteMapping("/{evaluateIds}")
    public AjaxResult remove(@PathVariable String[] evaluateIds) {
        return toAjax(evaluateService.deleteEvaluateByEvaluateIds(evaluateIds));
    }

    /**
     * 根据图书ID查询评价列表
     */
    @GetMapping("/selectEvaluateListByBookId/{bookId}")
    public AjaxResult selectEvaluateListByBookId(@PathVariable String bookId) {
    Evaluate evaluate = new Evaluate();
    evaluate.setBookId(bookId);
    List<Evaluate> list = evaluateService.selectEvaluateList(evaluate);
    return success(list);
    }
}
