package com.fast.mall.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import com.fast.system.domain.SysUser;
import com.fast.system.service.ISysUserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import com.fast.system.general.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.fast.mall.domain.OrderBook;
import com.fast.mall.mapper.OrderMapper;
import com.fast.mall.domain.Order;
import com.fast.mall.service.IOrderService;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.util.CollectionUtils;

import static com.fast.system.general.utils.SecurityUtils.getUserId;

/**
 * 订单Service业务层处理
 *
 * @author fast
 * @date 2025-12-12
 */
@Service
public class OrderServiceImpl implements IOrderService
{
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private ISysUserService userService;

    /**
     * 查询订单
     *
     * @param orderId 订单主键
     * @return 订单
     */
    @Override
    public Order selectOrderByOrderId(String orderId)
    {
        return orderMapper.selectOrderByOrderId(orderId);
    }

    /**
     * 查询订单列表
     *
     * @param order 订单
     * @return 订单
     */
    @Override
    public List<Order> selectOrderList(Order order)
    {
        return orderMapper.selectOrderList(order);
    }


    /**
     * 新增订单
     *
     * @param order 订单
     * @return 结果
     */
    @Transactional
    @Override
    public int insertOrder(Order order) {
        order.setCreateTime(new Date());
        order.setUserId(getUserId());

        //获取当前日期时间
        LocalDateTime now = LocalDateTime.now();
        //定义格式器
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        //格式化为字符串
        String formatDateTime = now.format(formatter);

        //订单号: OR + 当前日期时间字符串 + 当前用户ID
        order.setOrderId("OR" + formatDateTime + getUserId());
        int rows = orderMapper.insertOrder(order);
        insertOrderBook(order);
        return rows;
    }

    /**
     * 修改订单
     *
     * @param order 订单
     * @return 结果
     */
    @Transactional
    @Override
    public int updateOrder(Order order)
    {
        orderMapper.deleteOrderBookByOrderId(order.getOrderId());
        insertOrderBook(order);
        return orderMapper.updateOrder(order);
    }

    /**
     * 批量删除订单
     *
     * @param orderIds 需要删除的订单主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteOrderByOrderIds(String[] orderIds)
    {
        orderMapper.deleteOrderBookByOrderIds(orderIds);
        return orderMapper.deleteOrderByOrderIds(orderIds);
    }

    /**
     * 付款
     * @param orderId 订单ID
     * @return 是否付款成功
     */
    @Override
    @Transactional
    public int payment(String orderId) {
        //该订单需要支付的金额
        BigDecimal totalAmount = orderMapper.selectOrderByOrderId(orderId).getTotalAmount();
        //当前用户的账户余额
        BigDecimal oldBalance = userService.selectUserById(getUserId()).getBalance();
        //对比余额是否小于金额
        if (oldBalance.compareTo(totalAmount) < 0) {
            //余额不足的处理逻辑
            throw new RuntimeException("余额不足, 当前余额: " + oldBalance + "需支付: " + totalAmount);
        }
        //余额充足, 进行扣费
        BigDecimal newBalance = oldBalance.subtract(totalAmount);
        //更新用户余额
        SysUser user = new SysUser();
        user.setUserId(getUserId());
        user.setBalance(newBalance);
        userService.updateUser(user);
        //将订单状态修改为待发货
        List<OrderBook> orderBookList = orderMapper.selectOrderByOrderId(orderId).getOrderBookList();
        Order order = new Order();
        order.setOrderId(orderId);
        order.setStatus("待发货");
        order.setOrderBookList(orderBookList);
        return orderMapper.updateOrder(order);
    }

    /**
     * 新增订单图书信息
     *
     * @param order 订单对象
     */
    public void insertOrderBook(Order order)
    {
        List<OrderBook> orderBookList = order.getOrderBookList();
        String orderId = order.getOrderId();
        if (StringUtils.isNotNull(orderBookList))
        {
            List<OrderBook> list = new ArrayList<OrderBook>();
            for (OrderBook orderBook : orderBookList)
            {
                orderBook.setOrderId(orderId);
                list.add(orderBook);
            }
            if (list.size() > 0)
            {
                orderMapper.batchOrderBook(list);
            }
        }
    }


}