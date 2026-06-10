package com.fast.mall.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fast.mall.mapper.AddressMapper;
import com.fast.mall.domain.Address;
import com.fast.mall.service.IAddressService;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.util.CollectionUtils;

import static com.fast.system.general.utils.SecurityUtils.getUserId;

/**
 * 收货地址Service业务层处理
 *
 * @author fast
 * @date 2025-12-11
 */
@Service
public class AddressServiceImpl implements IAddressService
{
    @Resource
    private AddressMapper addressMapper;

    /**
     * 查询收货地址
     *
     * @param addressId 收货地址主键
     * @return 收货地址
     */
    @Override
    public Address selectAddressByAddressId(String addressId)
    {
        return addressMapper.selectAddressByAddressId(addressId);
    }

    /**
     * 查询收货地址列表
     *
     * @param address 收货地址
     * @return 收货地址
     */
    @Override
    public List<Address> selectAddressList(Address address)
    {
        return addressMapper.selectAddressList(address);
    }

    /**
     * 新增收货地址
     *
     * @param address 收货地址
     * @return 结果
     */
    @Override
    public int insertAddress(Address address) {
        address.setCreateTime(new Date());
        address.setAddressId(String.valueOf(UUID.randomUUID()));
        address.setUserId(getUserId());
        return addressMapper.insertAddress(address);
    }

    /**
     * 修改收货地址
     *
     * @param address 收货地址
     * @return 结果
     */
    @Override
    public int updateAddress(Address address)
    {
        return addressMapper.updateAddress(address);
    }

    /**
     * 批量删除收货地址
     *
     * @param addressIds 需要删除的收货地址主键
     * @return 结果
     */
    @Override
    public int deleteAddressByAddressIds(String[] addressIds)
    {
        return addressMapper.deleteAddressByAddressIds(addressIds);
    }
}