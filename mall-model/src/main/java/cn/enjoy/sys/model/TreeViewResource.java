package cn.enjoy.sys.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Ray on 2016/3/24 10:14.
 */
public class TreeViewResource implements Serializable {

    private static final long serialVersionUID = -246069402846695431L;
    private String id;
    private String pid;
    private String text;
    private List<TreeViewResource> nodes;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<TreeViewResource> getNodes() {
        return nodes;
    }

    public void setNodes(List<TreeViewResource> nodes) {
        this.nodes = nodes;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }
}
