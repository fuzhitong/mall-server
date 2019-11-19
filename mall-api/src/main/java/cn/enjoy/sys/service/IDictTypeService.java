/**
 * Created on 2015年9月2日 by Caiming
 */
package cn.enjoy.sys.service;


import cn.enjoy.sys.model.SelectModel;
import cn.enjoy.sys.model.SysDictData;
import cn.enjoy.sys.model.SysDictType;
import cn.enjoy.core.utils.GridModel;

import java.util.List;
import java.util.Map;

public interface IDictTypeService {

	/**
	 * 根据TYPE查询字典数据
	 * <li>创建人：Caiming</li>
	 * <li>创建时间：2015年9月2日</li>
	 * <li>创建目的：【】</li>
	 * <li>修改目的：【修改人：，修改时间：】</li>
	 * @param type
	 * @return
	 */
	List<SysDictData> findDictByType(String type);
	
	/**
	 * 查询所有字典数据
	 * <li>创建人：Caiming</li>
	 * <li>创建时间：2015年9月2日</li>
	 * <li>创建目的：【】</li>
	 * <li>修改目的：【修改人：，修改时间：】</li>
	 * @return
	 */
	List<SysDictData> findAllDict();

	List<SysDictType> findAllDictType();

	/**
	 * 根据字典type查询字典下拉框
	 * <li>创建人：Caiming</li>
	 * <li>创建时间：2015年9月9日</li>
	 * <li>创建目的：【】</li>
	 * <li>修改目的：【修改人：，修改时间：】</li>
	 * @param type
	 * @return
	 */
	List<SelectModel> findSelectModelsByType(String type);

	/**
	 * 根据字典type查询 并且 RESV1 升序排序
	 * <li>创建人：maojia</li>
	 * <li>创建时间：2015年9月9日</li>
	 * <li>创建目的：【】</li>
	 * <li>修改目的：【修改人：，修改时间：】</li>
	 * @param
	 *     param
	 *     必须参数:
	 *            type : 字典类型
	 * @return
	 */
	List<SysDictData> queryDictDataByTypeAndORDER(Map param);

	public GridModel<SysDictType> queryAllDictTypePage(String param, Integer pageNo, Integer pageSize, String sidx, String sord);

	void deleteByIds(List<String> ids);

	void addDictType(SysDictType sysDictType);

	SysDictType selectByPrimaryKey(String id);

	void deleteDictType(String id);

	void freezeDictTyp(String id, String status);

	void updateDictType(SysDictType sysDictType);


};

