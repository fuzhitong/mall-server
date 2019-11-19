package cn.enjoy.mall.vo;

import cn.enjoy.mall.SysPropUtil;
import cn.enjoy.mall.model.GoodsImages;

/**
 * 为返回给前台的数据而特定的
 * @author Ray
 * @date 2018/3/1.
 */
public class GoodsImageVo extends GoodsImages {
    private static final long serialVersionUID = 8619089901956720295L;

    @Override
    public String getImageUrl(){
        if (super.getImageUrl() != null) {
            if (super.getImageUrl().startsWith("http")) {
                return super.getImageUrl();
            } else {
                return SysPropUtil.getPicDomain(super.getImageUrl());
            }
        }
        return null;
    }
}
