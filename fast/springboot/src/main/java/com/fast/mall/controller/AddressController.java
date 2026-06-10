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

import com.fast.mall.domain.Address;
import com.fast.mall.service.IAddressService;
import com.fast.system.general.core.page.TableDataInfo;

/**
 * 收货地址Controller
 *
 * @author fast
 * @date 2025-12-11
 */
@RestController
@RequestMapping("/mall/address")
public class AddressController extends BaseController {
    @Resource
    private IAddressService addressService;

    /**
     * 查询收货地址列表
     */
    @GetMapping("/list")
    public TableDataInfo list(Address address) {
        startPage();
        List<Address> list = addressService.selectAddressList(address);
        return getDataTable(list);
    }

    /**
     * 获取收货地址详细信息
     */
    @GetMapping(value = "/{addressId}")
    public AjaxResult getInfo(@PathVariable("addressId") String addressId) {
        return success(addressService.selectAddressByAddressId(addressId));
    }

    /**
     * 新增收货地址
     */
    @PostMapping
    public AjaxResult add(@RequestBody Address address) {
        return toAjax(addressService.insertAddress(address));
    }

    /**
     * 修改收货地址
     */
    @PutMapping
    public AjaxResult edit(@RequestBody Address address) {
        return toAjax(addressService.updateAddress(address));
    }

    /**
     * 删除收货地址
     */
    @DeleteMapping("/{addressIds}")
    public AjaxResult remove(@PathVariable String[] addressIds) {
        return toAjax(addressService.deleteAddressByAddressIds(addressIds));
    }

    /**
     * 查询用户个人的收货地址列表
     */
    @GetMapping("/selectMyAddress")
    public AjaxResult selectMyAddress() {
        Address address = new Address();
        address.setUserId(getUserId());
        List<Address> list = addressService.selectAddressList(address);
        return success(list);
    }

}
