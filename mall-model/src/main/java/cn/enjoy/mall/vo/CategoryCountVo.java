package cn.enjoy.mall.vo;

import java.io.Serializable;

/**
 * @author Ray
 * @date 2018/2/8.
 */
public class CategoryCountVo implements Serializable{

    private static final long serialVersionUID = 1868424480807118085L;
    private Integer catId;
    private String name;
    private Integer count;

    public Integer getCatId() {
        return catId;
    }

    public void setCatId(Integer catId) {
        this.catId = catId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        CategoryCountVo vo = (CategoryCountVo) obj;
        //当按关键字查询商品时，catId为Null
        return (catId == null || catId.equals(vo.catId)) && name.equals(vo.name);
    }
}
