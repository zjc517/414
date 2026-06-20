package com.fast.mall.service;

import java.util.List;
import com.fast.mall.domain.Book;
import com.fast.mall.domain.vo.BookTopFiveVO;

/**
 * 图书Service接口
 *
 * @author fast
 * @date 2025-12-10
 */
public interface IBookService
{
    /**
     * 查询图书
     *
     * @param bookId 图书主键
     * @return 图书
     */
    public Book selectBookByBookId(String bookId);

    /**
     * 查询图书列表
     *
     * @param book 图书
     * @return 图书集合
     */
    public List<Book> selectBookList(Book book);

    /**
     * 新增图书
     *
     * @param book 图书
     * @return 结果
     */
    public int insertBook(Book book);

    /**
     * 修改图书
     *
     * @param book 图书
     * @return 结果
     */
    public int updateBook(Book book);

    /**
     * 批量删除图书
     *
     * @param bookIds 需要删除的图书主键集合
     * @return 结果
     */
    public int deleteBookByBookIds(String[] bookIds);

    /**
     * 查询之前是否已经有推荐的图书
     * @return 是否已经有推荐的图书
     */
    Boolean selectIsRecommend();

    /**
     * 查询之前已经推荐的图书ID
     * @return 图书ID
     */
    String selectBookIdByIsRecommend();

    /**
     * 查询推荐的图书
     * @return 图书信息
     */
    Book selectRecommendBook();

    /**
     * 查询销售数量前5的图书以及销售额
     * @return 销售图书列表
     */
    List<BookTopFiveVO> selectTopFiveBooks();

}
