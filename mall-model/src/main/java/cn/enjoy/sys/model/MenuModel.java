/**
 * Created on 2015年9月14日 by Caiming
 */
package cn.enjoy.sys.model;

import java.io.Serializable;
import java.util.List;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company:享学信息科技有限公司 Co., Ltd.</p>
 * @author Caiming
 * @version 1.0
 * 修改记录：
 * 修改序号，修改日期，修改人，修改内容
 */
public class MenuModel implements Serializable {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = -1984426118993480032L;
	
	private String id;
	
	private String title;
	
	private String icon;
	
	private String url;
	
	private String code;
	
	private List<MenuModel> children;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<MenuModel> getChildren() {
		return children;
	}

	public void setChildren(List<MenuModel> children) {
		this.children = children;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
	

}

