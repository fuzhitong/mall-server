package cn.enjoy.mall.vo;

import java.util.List;

public class UserShoppingGoodsVo {
    private String id;
    private String userId;
    private List<ShoppingGoodsVo> shoppingGoodsList ;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<ShoppingGoodsVo> getShoppingGoodsList() {
        return shoppingGoodsList;
    }

    public void setShoppingGoodsList(List<ShoppingGoodsVo> shoppingGoodsList) {
        this.shoppingGoodsList = shoppingGoodsList;
    }
}
