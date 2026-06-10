package com.fast.system.controller;

import com.fast.system.general.core.controller.BaseController;
import com.fast.system.general.core.domain.AjaxResult;
import com.fast.system.general.core.page.TableDataInfo;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 代码生成 操作处理
 *
 * @author fast
 */
@RestController
@RequestMapping("/tool/gen")
public class GenController extends BaseController
{
    /**
     * 查询生成表数据
     */
    @GetMapping("/list")
    public TableDataInfo list(String tableName, String tableComment)
    {
        startPage();

        List<Map<String, Object>> list = new ArrayList<>();

        return getDataTable(list);
    }

    /**
     * 修改代码生成配置
     */
    @PutMapping
    public AjaxResult edit(@RequestBody Map<String, Object> data)
    {
        return success();
    }

    /**
     * 删除表数据
     */
    @DeleteMapping("/{tableId}")
    public AjaxResult remove(@PathVariable Long tableId)
    {
        return success();
    }

    /**
     * 预览生成代码
     */
    @GetMapping("/preview/{tableId}")
    public AjaxResult preview(@PathVariable("tableId") Long tableId)
    {
        Map<String, Object> result = new HashMap<>();
        result.put("templates", new ArrayList<>());
        return success(result);
    }

    /**
     * 导入表
     */
    @PostMapping("/importTable")
    public AjaxResult importTable(String tables)
    {
        return success();
    }

    /**
     * 创建表
     */
    @PostMapping("/createTable")
    public AjaxResult createTable(String sql)
    {
        return success();
    }

    /**
     * 生成代码
     */
    @GetMapping("/genCode/{tableName}")
    public AjaxResult genCode(@PathVariable("tableName") String tableName)
    {
        return success();
    }

    /**
     * 同步数据库
     */
    @GetMapping("/synchDb/{tableName}")
    public AjaxResult synchDb(@PathVariable("tableName") String tableName)
    {
        return success();
    }

    /**
     * 获取表详细信息
     */
    @GetMapping(value = "/{tableId}")
    public AjaxResult getInfo(@PathVariable("tableId") Long tableId)
    {
        Map<String, Object> result = new HashMap<>();
        result.put("info", new HashMap<>());
        result.put("rows", new ArrayList<>());
        return success(result);
    }

    /**
     * 查询db数据库列表
     */
    @GetMapping("/db/list")
    public TableDataInfo listDbTable(String tableName, String tableComment)
    {
        startPage();

        List<Map<String, Object>> list = new ArrayList<>();

        return getDataTable(list);
    }
}
