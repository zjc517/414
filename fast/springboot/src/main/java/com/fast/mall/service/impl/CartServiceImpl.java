package com.fast.mall.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fast.mall.mapper.CartMapper;
import com.fast.mall.domain.Cart;
import com.fast.mall.service.ICartService;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.util.CollectionUtils;

import static com.fast.system.general.utils.SecurityUtils.getUserId;

/**
 * 购物车Service业务层处理
 *
 * @author fast
 * @date 2025-12-10
 */
@Service
public class CartServiceImpl implements ICartService
{
    @Resource
    private CartMapper cartMapper;

    /**
     * 查询购物车
     *
     * @param cartId 购物车主键
     * @return 购物车
     */
    @Override
    public Cart selectCartByCartId(String cartId)
    {
        return cartMapper.selectCartByCartId(cartId);
    }

    /**
     * 查询购物车列表
     *
     * @param cart 购物车
     * @return 购物车
     */
    @Override
    public List<Cart> selectCartList(Cart cart)
    {
        return cartMapper.selectCartList(cart);
    }

    /**
     * 新增购物车
     *
     * @param cart 购物车
     * @return 结果
     */
    @Override
    public int insertCart(Cart cart) {
        cart.setCreateTime(new Date());
        //生成一个UUID并插入至对象中
        cart.setCartId(String.valueOf(UUID.randomUUID()));
        //获取当前操作用户的用户ID并插入至对象中
        cart.setUserId(getUserId());
        //查询当前用户的购物车中有没有这本书
        Boolean isThere = cartMapper.selectIsThere(cart.getBookId(), getUserId());
        //如果有这本书, 就在原有数量上+1, 如果没有, 直接新增
        if (isThere) {
            //原有数量上+1
            //根据图书ID和用户ID查询购物车ID
            String cartId = cartMapper.selectCartIdByBookIdAndUserId(cart.getBookId(), getUserId());
            //加入购物车之前的数量
            Long quantity = cartMapper.selectCartByCartId(cartId).getQuantity();
            Cart updCart = new Cart();
            updCart.setCartId(cartId);
            updCart.setQuantity(quantity + 1);
            return cartMapper.updateCart(updCart);
        }
        //直接新增
        return cartMapper.insertCart(cart);
    }

    /**
     * 修改购物车
     *
     * @param cart 购物车
     * @return 结果
     */
    @Override
    public int updateCart(Cart cart)
    {
        return cartMapper.updateCart(cart);
    }

    /**
     * 批量删除购物车
     *
     * @param cartIds 需要删除的购物车主键
     * @return 结果
     */
    @Override
    public int deleteCartByCartIds(String[] cartIds)
    {
        return cartMapper.deleteCartByCartIds(cartIds);
    }
}