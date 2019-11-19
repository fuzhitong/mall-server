package cn.enjoy.mall.service;

import cn.enjoy.mall.model.UserAddress;

import java.util.List;

public interface IUserAddressService {
    void save(UserAddress userAddress);
    void remove(Integer addressId);
    List<UserAddress> selectByUserId(String userId);
}
