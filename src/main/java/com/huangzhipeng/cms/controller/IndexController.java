package com.huangzhipeng.cms.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.huangzhipeng.cms.entity.Article;
import com.huangzhipeng.cms.entity.Channel;
import com.huangzhipeng.cms.entity.Link;
import com.huangzhipeng.cms.service.ArticleService;
import com.huangzhipeng.cms.service.ChannelService;
import com.huangzhipeng.cms.utils.ESUtils;
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
	
	@Autowired
	ElasticsearchTemplate elasticsearchTemplate;
	
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
			 @RequestParam( value="pageSize",defaultValue = "3") Integer pageSize,
			 @RequestParam(value="page",defaultValue = "1") Integer pageNum,
			 @RequestParam(defaultValue = "") String key 
			) {
		System.err.println(pageNum);
		log.info("this is log test");
		
		List<Channel> channels = cService.getChannels();
		request.setAttribute("channels", channels);
		
		//获取热门
		PageInfo<Article> pageInfo=null;
		String pageString =null;
		 if ("".equals(key.trim())||key.trim()==null) { 
			PageHelper.startPage(pageNum,3);
			List<Article> arPage =articleService.listhots(key,pageNum,3);
			 pageInfo = new PageInfo<Article>(arPage);
			int count=articleService.gethotscount();
			if (count>0) {
				int pages=0;
				if (count%3==0) {
					pages=count/3;
				}else {
					pages=count/3+1;
				}
				
				pageString = PageUtil.page(pageNum,pages, "/article/hots?key="+key,3);
			}else{
				pageString = PageUtil.page(pageNum,pageInfo.getPages(), "/article/hots?key="+key,pageInfo.getPageSize());
			}
		}else {
			//如果有查询条件,则从elasticsearch中查询数据
			AggregatedPage<Article> pageList = (AggregatedPage<Article>) ESUtils.selectObjects(elasticsearchTemplate,Article.class,pageNum, 3,"id", new String[] {"title"}, key);
			
			//获取查询到的结果
			List<Article> list = pageList.getContent();
			//将数据封装到对象
			PageHelper.startPage(pageNum,3);
			List<Article> arPage=new ArrayList<Article>(list);
			 pageInfo = new PageInfo<Article>(arPage);
			pageString=PageUtil.page(pageNum,pageList.getTotalPages(), "/article/hots?key="+key,3);
			
		}
	    request.setAttribute("key",key);
		request.setAttribute("pageInfo", pageInfo);
		//获取最新
		List<Article> lastArticles = articleService.last();
		request.setAttribute("lasts", lastArticles);
		//获取最多点击量文章
		List<Article> hits=articleService.articlehitsdesc();
		request.setAttribute("hits", hits);
		//友情链接
		List<Link> links =  new ArrayList<Link>();
		links.add(new Link("http://www.bwie.net","八维好厉害"));
		links.add(new Link("http://www.bwie.org","八维真牛"));
		links.add(new Link("http://www.bwie.com","八维顶呱呱"));
		request.setAttribute("links", links);
		
		
		request.setAttribute("pageStr", pageString);
		return "index/index";
	}
	
}
