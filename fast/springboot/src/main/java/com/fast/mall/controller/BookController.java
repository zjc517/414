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

import com.fast.mall.domain.Book;
import com.fast.mall.service.IBookService;
import com.fast.system.general.core.page.TableDataInfo;

/**
 * 图书Controller
 *
 * @author fast
 * @date 2025-12-10
 */
@RestController
@RequestMapping("/mall/book")
public class BookController extends BaseController {
    @Resource
    private IBookService bookService;

    /**
     * 查询图书列表
     */
    @GetMapping("/list")
    public TableDataInfo list(Book book) {
        startPage();
        List<Book> list = bookService.selectBookList(book);
        return getDataTable(list);
    }

    /**
     * 获取图书详细信息
     */
    @GetMapping(value = "/{bookId}")
    public AjaxResult getInfo(@PathVariable("bookId") String bookId) {
        return success(bookService.selectBookByBookId(bookId));
    }

    /**
     * 新增图书
     */
    @PostMapping
    public AjaxResult add(@RequestBody Book book) {
        return toAjax(bookService.insertBook(book));
    }

    /**
     * 修改图书
     */
    @PutMapping
    public AjaxResult edit(@RequestBody Book book) {
        return toAjax(bookService.updateBook(book));
    }

    /**
     * 删除图书
     */
    @DeleteMapping("/{bookIds}")
    public AjaxResult remove(@PathVariable String[] bookIds) {
        return toAjax(bookService.deleteBookByBookIds(bookIds));
    }
}
