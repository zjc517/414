package com.fast.mall.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.fast.mall.domain.Cart;

/**
 * 购物车Mapper接口
 *
 * @author fast
 * @date 2025-12-10
 */
@Mapper
public interface CartMapper
{
    /**
     * 查询购物车
     *
     * @param cartId 购物车主键
     * @return 购物车
     */
    public Cart selectCartByCartId(String cartId);

    /**
     * 查询购物车列表
     *
     * @param cart 购物车
     * @return 购物车集合
     */
    public List<Cart> selectCartList(Cart cart);

    /**
     * 新增购物车
     *
     * @param cart 购物车
     * @return 结果
     */
    public int insertCart(Cart cart);

    /**
     * 修改购物车
     *
     * @param cart 购物车
     * @return 结果
     */
    public int updateCart(Cart cart);

    /**
     * 批量删除购物车
     *
     * @param cartIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCartByCartIds(String[] cartIds);

    /**
     * 查询当前用户的购物车中有没有这本书
     * @param bookId 图书ID
     * @param userId 用户ID
     * @return 有没有这本书
     */
    Boolean selectIsThere(String bookId, Long userId);

    /**
     * 根据图书ID和用户ID查询购物车ID
     * @param bookId 图书ID
     * @param userId 用户ID
     * @return 购物车ID
     */
    String selectCartIdByBookIdAndUserId(String bookId, Long userId);
}
