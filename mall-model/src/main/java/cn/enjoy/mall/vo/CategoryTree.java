package cn.enjoy.mall.vo;

import cn.enjoy.mall.SysPropUtil;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.List;

/**
 * @author Ray
 * @date 2018/2/5.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CategoryTree implements Serializable{

    private static final long serialVersionUID = 8627927693404752496L;
    private Integer id;
    private String name;
    private Short level;
    private String image;

    private List<CategoryTree> nodes;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Short getLevel() {
        return level;
    }

    public void setLevel(Short level) {
        this.level = level;
    }

    public String getImage() {
        if(image == null || image.startsWith("http")){
            return image;
        }else {
            return SysPropUtil.getPicDomain(image);
        }
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<CategoryTree> getNodes() {
        return nodes;
    }

    public void setNodes(List<CategoryTree> nodes) {
        this.nodes = nodes;
    }
}
