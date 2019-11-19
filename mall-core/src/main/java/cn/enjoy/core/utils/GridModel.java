/**
 * Created on 2015年9月3日 by Caiming
 */
package cn.enjoy.core.utils;

import com.github.miemiedev.mybatis.paginator.domain.PageList;

import java.io.Serializable;
import java.util.List;


public class GridModel<T> implements Serializable {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 7252085778810679755L;

	private Integer page;
	//总记录数
	private Integer records;
	//总页数
	private Integer total = 0;

	private List<T> rows;

	public GridModel() {

	}

	public GridModel(PageList<T> pageList) {
		this.page = pageList.getPaginator().getPage();
		this.records = pageList.getPaginator().getTotalCount();
		this.total = pageList.getPaginator().getTotalPages();
		this.rows = pageList;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getRecords() {
		return records;
	}

	public void setRecords(Integer records) {
		this.records = records;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

}
