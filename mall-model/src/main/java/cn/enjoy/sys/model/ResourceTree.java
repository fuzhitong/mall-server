/**
 * Created on 2015年9月28日 by xiaopu
 */
package cn.enjoy.sys.model;

import java.io.Serializable;
import java.util.List;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company:享学信息科技有限公司 Co., Ltd.</p>
 * @author xiaopu
 * @version 1.0
 * 修改记录：
 * 修改序号，修改日期，修改人，修改内容
 */
public class ResourceTree implements Serializable{

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = -3082177672018673925L;

	private String id;
	private String title;
	private Boolean selected;
	private String roleId;
	private String sysCode;
	private List<ResourceTree> list;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Boolean getSelected() {
		return selected;
	}
	public void setSelected(Boolean selected) {
		this.selected = selected;
	}
	public List<ResourceTree> getList() {
		return list;
	}
	public void setList(List<ResourceTree> list) {
		this.list = list;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getSysCode() {
		return sysCode;
	}
	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
	}




}

