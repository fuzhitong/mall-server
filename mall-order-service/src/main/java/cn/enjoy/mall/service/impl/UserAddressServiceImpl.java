package cn.enjoy.mall.service.impl;

import cn.enjoy.mall.dao.UserAddressMapper;
import cn.enjoy.mall.model.UserAddress;
import cn.enjoy.mall.service.IUserAddressService;
import com.alibaba.dubbo.config.annotation.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class UserAddressServiceImpl implements IUserAddressService {
    @Resource
    private UserAddressMapper userAddressMapper;

    @Override
    public void save(UserAddress userAddress) {
        if(userAddress.getAddressId()==null || userAddress.getAddressId() == 0){
            userAddressMapper.insert(userAddress);
        }else{
            userAddressMapper.updateByPrimaryKey(userAddress);
        }
    }

    @Override
    public void remove(Integer addressId) {
        userAddressMapper.deleteByPrimaryKey(addressId);
    }

    @Override
    public List<UserAddress> selectByUserId(String userId) {
        return userAddressMapper.selectByUserId(userId);
    }
}
