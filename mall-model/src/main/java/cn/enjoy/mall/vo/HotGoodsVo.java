package cn.enjoy.mall.vo;

import java.io.Serializable;
import java.util.List;

/**
 * @author Ray
 * @date 2018/2/11.
 */
public class HotGoodsVo implements Serializable{

    private static final long serialVersionUID = -3109172279209621891L;
    private int id;
    private String name;

    private List<GoodsPageVo> list;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<GoodsPageVo> getList() {
        return list;
    }

    public void setList(List<GoodsPageVo> list) {
        this.list = list;
    }
}
