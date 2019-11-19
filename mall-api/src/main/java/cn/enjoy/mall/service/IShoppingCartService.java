package cn.enjoy.mall.service;

import cn.enjoy.mall.vo.ShoppingGoodsVo;

import java.util.List;

public interface IShoppingCartService {
    static final Integer SAVE_MODE_APPEND = 0;
    static final Integer SAVE_MODE_UPDATE = 1;

    void save(Integer specGoodsId,Integer num,String userId,Integer saveMode);
    void remove(Integer specGoodsId,String userId);
    void removeAll(String userId);
    List<ShoppingGoodsVo> list(String userId);
    ShoppingGoodsVo findBySpecGoodsId(Integer specGoodsId,String userId);
    void updateStatus(String specGoodsIds,Integer status,String userId);
    List<ShoppingGoodsVo> findCheckedGoodsList(String userId);
    void removeCheckedGoodsList(String userId);
}
