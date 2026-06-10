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

import com.fast.mall.domain.Cart;
import com.fast.mall.service.ICartService;
import com.fast.system.general.core.page.TableDataInfo;

/**
 * 购物车Controller
 *
 * @author fast
 * @date 2025-12-10
 */
@RestController
@RequestMapping("/mall/cart")
public class CartController extends BaseController {
    @Resource
    private ICartService cartService;

    /**
     * 查询购物车列表
     */
    @GetMapping("/list")
    public TableDataInfo list(Cart cart) {
        startPage();
        List<Cart> list = cartService.selectCartList(cart);
        return getDataTable(list);
    }

    /**
     * 获取购物车详细信息
     */
    @GetMapping(value = "/{cartId}")
    public AjaxResult getInfo(@PathVariable("cartId") String cartId) {
        return success(cartService.selectCartByCartId(cartId));
    }

    /**
     * 新增购物车
     */
    @PostMapping
    public AjaxResult add(@RequestBody Cart cart) {
        return toAjax(cartService.insertCart(cart));
    }

    /**
     * 修改购物车
     */
    @PutMapping
    public AjaxResult edit(@RequestBody Cart cart) {
        return toAjax(cartService.updateCart(cart));
    }

    /**
     * 删除购物车
     */
    @DeleteMapping("/{cartIds}")
    public AjaxResult remove(@PathVariable String[] cartIds) {
        return toAjax(cartService.deleteCartByCartIds(cartIds));
    }

    /**
     * 查询用户个人的购物车列表
     */
    @GetMapping("/selectMyCartList")
    public AjaxResult selectMyCartList() {
        Cart cart = new Cart();
        cart.setUserId(getUserId());
        List<Cart> list = cartService.selectCartList(cart);
        return success(list);
    }
}
