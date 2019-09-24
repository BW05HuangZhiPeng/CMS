package com.huangzhipeng.cms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huangzhipeng.cms.dao.CategoryMapper;
import com.huangzhipeng.cms.entity.Category;
import com.huangzhipeng.cms.service.CategoryService;

/**
*@author huangzhipeng
*@version 创建时间：2019年9月21日 下午4:25:08
*分类业务层
*/
@Service
public class CategoryServiceImpl implements CategoryService {

	
	@Autowired
	CategoryMapper categoryMapper; 
	
	@Override
	public List<Category> getCategoryByChId(Integer cid) {
		return categoryMapper.getCategoryByChId(cid);
	}

}
