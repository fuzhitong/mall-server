/**
 * Created on 2015年9月2日 by Caiming
 */
package cn.enjoy.users.service.impl;


import cn.enjoy.users.dao.SysDictDataMapper;
import cn.enjoy.users.dao.SysDictTypeMapper;
import cn.enjoy.core.exception.BusinessException;
import cn.enjoy.core.utils.GridModel;
import cn.enjoy.core.utils.UUIDGenerator;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.miemiedev.mybatis.paginator.domain.Order;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import cn.enjoy.sys.model.SelectModel;
import cn.enjoy.sys.model.SysDictData;
import cn.enjoy.sys.model.SysDictType;
import cn.enjoy.sys.service.IDictTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

@Service
public class DictTypeServiceImpl implements IDictTypeService {
	
	@Autowired
	private SysDictDataMapper dictDataMapper;
	
	@Autowired
	private SysDictTypeMapper dictTypeMapper;

	@Override
	public List<SysDictData> findDictByType(String type) {
		return dictDataMapper.queryDictDataByType(type);
	}

	@Override
	public List<SysDictData> findAllDict() {
		return dictDataMapper.queryAllDictData();
	}
	
	@Override
	public List<SysDictType> findAllDictType(){
		return dictTypeMapper.queryAllDictType();
	}
	
	@Override
	public List<SelectModel> findSelectModelsByType(String type){
		return dictDataMapper.querySelectModelByType(type);
	}

	@Override
	public List<SysDictData> queryDictDataByTypeAndORDER(Map param) {
		return dictDataMapper.queryDictDataByTypeAndORDER(param);
	}

	@Override
	public GridModel<SysDictType> queryAllDictTypePage(String param, Integer pageNo, Integer pageSize, String sidx, String sord) {
		String orderString = "";
		if (!StringUtils.isEmpty(sidx)) {
			orderString = sidx + "." + sord;
		}
		PageBounds pageBounds = new PageBounds(pageNo, pageSize, Order.formString(orderString));
		PageList<SysDictType> pageList =(PageList<SysDictType>)dictTypeMapper.queryAllDictTypePage(param, pageBounds);
		return new GridModel<SysDictType>(pageList);
	}

	@Override
	public void deleteByIds(List<String> ids) {
		dictTypeMapper.deleteByIds(ids);
	}


	@Override
	@Transactional
	public void addDictType(SysDictType sysDictType){
		sysDictType.setId(UUIDGenerator.getUUID());
		dictTypeMapper.insert(sysDictType);
	}

	@Override
	public SysDictType 	selectByPrimaryKey(String id){
		return dictTypeMapper.selectByPrimaryKey(id);
	}

	@Override
	public void deleteDictType(String id){
		SysDictType sy =dictTypeMapper.selectByPrimaryKey(id);
		List<SysDictData> list = dictDataMapper.queryDictDataByCode(sy.getType());
		if(list.size()>0){
			throw new BusinessException("操作失败，该字典组下面还有字典项，不能删除！");
		}
		 dictTypeMapper.deleteByPrimaryKey(id);
	}

	@Override
	@Transactional
	public void freezeDictTyp(String id,String status){
		SysDictType sy =dictTypeMapper.selectByPrimaryKey(id);
		sy.setValid(Integer.parseInt(status));
		dictTypeMapper.updateByPrimaryKeySelective(sy);
	}

	@Override
	@Transactional
	public void updateDictType(SysDictType sysDictType){
		dictTypeMapper.updateByPrimaryKeySelective(sysDictType);
	}

}

