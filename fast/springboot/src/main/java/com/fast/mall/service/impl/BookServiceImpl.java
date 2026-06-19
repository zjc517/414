package com.fast.mall.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fast.mall.mapper.BookMapper;
import com.fast.mall.domain.Book;
import com.fast.mall.service.IBookService;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.util.CollectionUtils;

/**
 * 图书Service业务层处理
 *
 * @author fast
 * @date 2025-12-10
 */
@Service
public class BookServiceImpl implements IBookService
{
    @Resource
    private BookMapper bookMapper;

    /**
     * 查询图书
     *
     * @param bookId 图书主键
     * @return 图书
     */
    @Override
    public Book selectBookByBookId(String bookId)
    {
        return bookMapper.selectBookByBookId(bookId);
    }

    /**
     * 查询图书列表
     *
     * @param book 图书
     * @return 图书
     */
    @Override
    public List<Book> selectBookList(Book book)
    {
        return bookMapper.selectBookList(book);
    }

    /**
     * 新增图书
     *
     * @param book 图书
     * @return 结果
     */
    @Override
    public int insertBook(Book book) {
        book.setCreateTime(new Date());
        //生成一个UUID并插入至对象中
        book.setBookId(String.valueOf(UUID.randomUUID()));
        return bookMapper.insertBook(book);
    }

    /**
     * 修改图书
     *
     * @param book 图书
     * @return 结果
     */
    @Override
    public int updateBook(Book book)
    {
        return bookMapper.updateBook(book);
    }

    /**
     * 批量删除图书
     *
     * @param bookIds 需要删除的图书主键
     * @return 结果
     */
    @Override
    public int deleteBookByBookIds(String[] bookIds)
    {
        return bookMapper.deleteBookByBookIds(bookIds);
    }

    @Override
    public Boolean selectIsRecommend() {
        return null;
    }

    /**
     * 查询之前已经推荐的图书ID
     * @return 图书ID
     */
    @Override
    public String selectBookIdByIsRecommend() {
        return bookMapper.selectBookIdByIsRecommend();
    }

    /**
     * 查询推荐的图书
     * @return 图书信息
     */
    @Override
    public Book selectRecommendBook() {
        return bookMapper.selectRecommendBook();
    }
}

