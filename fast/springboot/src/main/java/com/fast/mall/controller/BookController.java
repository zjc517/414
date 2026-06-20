package com.fast.mall.controller;

import java.util.List;

import com.fast.mall.domain.vo.BookTopFiveVO;
import jakarta.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;
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

    /**
     * 图书推荐
     */
    @PutMapping("/recommend/{bookId}")
    @Transactional
    public AjaxResult recommend(@PathVariable String bookId) {
        // 1. 查询是否已有推荐的图书
        Boolean recommendBoolean = bookService.selectIsRecommend();

        // 2. 如果有推荐图书，先取消之前的推荐
        // 使用 Boolean.TRUE.equals() 安全处理 null
        if (Boolean.TRUE.equals(recommendBoolean)) {
            // 查询之前已经推荐的图书ID
            String oldRecommendBookId = bookService.selectBookIdByIsRecommend();
            if (oldRecommendBookId != null && !oldRecommendBookId.isEmpty()) {
                Book oldRecommendBook = new Book();
                oldRecommendBook.setBookId(oldRecommendBookId);
                oldRecommendBook.setIsRecommend(false);
                bookService.updateBook(oldRecommendBook);
            }
        }

        // 3. 设置当前图书为推荐
        Book book = new Book();
        book.setBookId(bookId);
        book.setIsRecommend(true);
        return toAjax(bookService.updateBook(book));
    }

    //查询推荐的图书
    @GetMapping("/selectRecommendBook")
    public AjaxResult selectRecommendBook() {
        Book book = bookService.selectRecommendBook();
        return success(book);
    }

    //查询销售数量前5的图书以及销售额
    @GetMapping("/selectTopFiveBooks")
    public AjaxResult selectTopFiveBooks() {
        List<BookTopFiveVO> list = bookService.selectTopFiveBooks();
        return success(list);
    }
}
