package com.fast.mall.coontroller;

import com.fast.mall.domain.Category;
import com.fast.mall.service.ICategoryService;
import com.fast.system.general.core.controller.BaseController;
import com.fast.system.general.core.page.TableDataInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}