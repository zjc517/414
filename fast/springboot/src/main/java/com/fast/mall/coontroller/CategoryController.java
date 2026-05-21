package com.fast.mall.coontroller;

import com.fast.mall.domain.Category;
import com.fast.mall.service.ICategoryService;
import com.fast.system.general.core.controller.BaseController;
import com.fast.system.general.core.domain.AjaxResult;
import com.fast.system.general.core.page.TableDataInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 图书分类Controller
 */
@RestController
@RequestMapping("/mall/category")
public class CategoryController extends BaseController {
    @Resource
    private ICategoryService categoryService;

    /**
     * 查询图书分类列表
     */
    @GetMapping("/list")
    public TableDataInfo list(Category category) {
        startPage();
        List<Category> list = categoryService.selectCategoryList(category);
        return getDataTable(list);
    }
    /**
     * 获取图书分类详细信息
     */
    @GetMapping("/{categoryId}")
    public AjaxResult selectCategoryByCategoryId(@PathVariable String categoryId) {
        Category category = categoryService.selectCategoryByCategoryId(categoryId);
        return success(category);
    }
    /**
     * 新增图书分类
     */
    @PostMapping
    public AjaxResult insertCategory(@RequestBody Category category) {
        return toAjax(categoryService.insertCategory(category));
    }
}