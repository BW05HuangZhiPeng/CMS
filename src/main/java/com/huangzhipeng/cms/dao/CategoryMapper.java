package com.huangzhipeng.cms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.huangzhipeng.cms.entity.Category;



/**
*@author huangzhipeng
*@version 创建时间：2019年9月21日 上午10:25:13
*分类Mapper
*/
@Mapper
public interface CategoryMapper {
	
//## 增加 ##----------------------------------------------------------------------------------------------------------
	
//## 删除 ##----------------------------------------------------------------------------------------------------------

//## 修改 ##----------------------------------------------------------------------------------------------------------

//## 查找 ##----------------------------------------------------------------------------------------------------------

	/**
	 * 查询分类列表(通过频道id，用于二级联动)
	 * @param cid
	 * @return
	 */
	List<Category> getCategoryByChId(Integer cid);
	
}
