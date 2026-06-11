package com.fast.mall.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.fast.mall.domain.Order;
import com.fast.mall.domain.OrderBook;

/**
 * 订单Mapper接口
 *
 * @author fast
 * @date 2025-12-12
 */
@Mapper
public interface OrderMapper
{
    /**
     * 查询订单
     *
     * @param orderId 订单主键
     * @return 订单
     */
    public Order selectOrderByOrderId(String orderId);

    /**
     * 查询订单列表
     *
     * @param order 订单
     * @return 订单集合
     */
    public List<Order> selectOrderList(Order order);

    /**
     * 新增订单
     *
     * @param order 订单
     * @return 结果
     */
    public int insertOrder(Order order);

    /**
     * 修改订单
     *
     * @param order 订单
     * @return 结果
     */
    public int updateOrder(Order order);

    /**
     * 批量删除订单
     *
     * @param orderIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOrderByOrderIds(String[] orderIds);

    /**
     * 批量删除订单图书
     *
     * @param orderIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOrderBookByOrderIds(String[] orderIds);

    /**
     * 批量新增订单图书
     *
     * @param orderBookList 订单图书列表
     * @return 结果
     */
    public int batchOrderBook(List<OrderBook> orderBookList);


    /**
     * 通过订单主键删除订单图书信息
     *
     * @param orderId 订单ID
     * @return 结果
     */
    public int deleteOrderBookByOrderId(String orderId);
}
