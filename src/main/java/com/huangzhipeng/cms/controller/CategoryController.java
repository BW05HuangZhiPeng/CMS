package com.huangzhipeng.cms.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.huangzhipeng.cms.entity.Article;
import com.huangzhipeng.cms.entity.Category;
import com.huangzhipeng.cms.service.ArticleService;
import com.huangzhipeng.cms.service.CategoryService;
import com.huangzhipeng.cms.utils.PageUtil;


/**
*@author huangzhipeng
*@version 创建时间：2019年9月24日 上午8:27:44
*分类控制层
*/
@Controller
public class CategoryController {

	@Autowired
	ArticleService articleService; 
	

	@Autowired
	private CategoryService  categoryService;
	
	/**
	 * 根据频道的id 获取所有的分类 同时获取频道下文章 以及分页数据
	 * @param cid
	 * @return
	 */
	@RequestMapping("getCategories")
	public String getCategoryByChId(HttpServletRequest request, Integer cid
			,@RequestParam(defaultValue = "1",value="page") Integer pageNum
			){
		
		
		List<Category> categoris = categoryService.getCategoryByChId(cid);
		System.out.println("categoris is" + categoris);
		
		// 获取这个频道下的所有的文章  
		PageInfo<Article> arPage =  articleService.list(pageNum,cid, 0);
		request.setAttribute("pageInfo",arPage);
		
		request.setAttribute("catygories",categoris);
		request.setAttribute("channelId",cid);
		
		String pageString = PageUtil.page(arPage.getPageNum(), arPage.getPages(), "/getCategories?cid="+cid, arPage.getPageSize());
		request.setAttribute("pageStr", pageString);
		
		return "index/category";
		
		
		
	}
}
