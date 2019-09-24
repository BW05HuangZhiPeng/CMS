package com.huangzhipeng.cms.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.huangzhipeng.cms.entity.Article;
import com.huangzhipeng.cms.entity.Channel;
import com.huangzhipeng.cms.entity.Link;
import com.huangzhipeng.cms.service.ArticleService;
import com.huangzhipeng.cms.service.ChannelService;
import com.huangzhipeng.cms.utils.PageUtil;

/**
*@author huangzhipeng
*@version 创建时间：2019年9月24日 上午8:29:10
*主页控制层
*/
@Controller
public class IndexController {
private Logger log = Logger.getLogger(IndexController.class);
	
	@Autowired
	ChannelService cService;
	
	@Autowired
	ArticleService articleService ;
	
	/**
	 * 获取热门，最新，友情链接，以及搜索功能数据
	 * @param request
	 * @param pageSize
	 * @param pageNum
	 * @param key
	 * @return
	 */
	@RequestMapping(value= {"/index","/",""},method=RequestMethod.GET)
	public String index(HttpServletRequest request,
			 @RequestParam( value="pageSize",defaultValue = "2") Integer pageSize,
			 @RequestParam(value="page",defaultValue = "1") Integer pageNum,
			 @RequestParam(defaultValue = "") String key 
			) {
		
		log.info("this is log test");
		
		List<Channel> channels = cService.getChannels();
		request.setAttribute("channels", channels);
		
		//获取热门
		PageInfo<Article> arPage = articleService.listhots(key,pageNum, pageSize);
		request.setAttribute("pageInfo", arPage);
		//获取最新
		List<Article> lastArticles = articleService.last();
		request.setAttribute("lasts", lastArticles);
		//友情链接
		List<Link> links =  new ArrayList<Link>();
		links.add(new Link("http://www.bwie.net","八维好厉害"));
		links.add(new Link("http://www.bwie.org","八维真牛"));
		links.add(new Link("http://www.bwie.com","八维顶呱呱"));
		request.setAttribute("links", links);
		
		String pageString = PageUtil.page(arPage.getPageNum(), arPage.getPages(), "/article/hots?key="+key, arPage.getPageSize());
		request.setAttribute("pageStr", pageString);
		return "index/index";
	}
	
}
