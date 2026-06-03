package com.fast.mall.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.fast.mall.domain.Book;

/**
 * 图书Mapper接口
 *
 * @author fast
 * @date 2025-12-10
 */
@Mapper
public interface BookMapper
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
     * @param bookIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBookByBookIds(String[] bookIds);
}
