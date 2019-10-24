package com.huangzhipeng.cms.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.huangzhipeng.cms.entity.Article;
import com.huangzhipeng.cms.entity.Category;
import com.huangzhipeng.cms.entity.Channel;
import com.huangzhipeng.cms.entity.User;
import com.huangzhipeng.cms.service.ArticleService;
import com.huangzhipeng.cms.service.CategoryService;
import com.huangzhipeng.cms.service.ChannelService;
import com.huangzhipeng.cms.service.CommentService;
import com.huangzhipeng.cms.utils.ConstantFinal;
import com.huangzhipeng.cms.utils.ESUtils;
import com.huangzhipeng.cms.utils.PageUtil;

/**
 * @author huangzhipeng
 * @version 创建时间：2019年9月21日 下午4:31:50 文章控制层
 */
@Controller
@RequestMapping("article")
public class ArticleController {

	private Logger log = Logger.getLogger(ArticleController.class);

	@Autowired
	CommentService commentService;
	
	@Autowired
	ElasticsearchTemplate elasticsearchTemplate;
	
	@Autowired
	private ChannelService channelService;

	@Autowired
	private CategoryService catService;

	@Autowired
	ArticleService articleService;
	
	@Autowired
	CategoryService categoryService;
	
// ## 管理员(admin) ##----------------------------------------------------------------------------------------------------------

	/**
	 * 查询文章列表并返回到管理员审核文章页面
	 * @param request
	 * @param status  文章的状态  1 待审  2 已经审核通过  3 审核未通过
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	@GetMapping("checkList")
	public String checkList(HttpServletRequest request,
			@RequestParam(defaultValue="0")  Integer status,
			@RequestParam(defaultValue="1",value = "page") int pageNum ,
			@RequestParam(defaultValue="3")  int pageSize) {
		PageInfo<Article> articlePage =  articleService.checkList(status,pageNum,pageSize);
		String pageString = PageUtil.page(articlePage.getPageNum(), articlePage.getPages(), "/article/checkList?status="+status, articlePage.getPageSize());
		request.setAttribute("pageStr", pageString);
		request.setAttribute("articles", articlePage);
		return "admin/article/checkList";
		
	}
	
	/**
	 * 查询本文章详情，返回到文章审核详情页，可进行审核操作
	 * @param request
	 * @param id
	 * @return
	 */
	@GetMapping("get")
	public String getCheckDetail(HttpServletRequest request,Integer id) {
		
		Article article = articleService.findById(id);
		request.setAttribute("article", article);
		return "admin/article/detail";
	}
	
	/**
	 *  审核文章  
	 * @param request
	 * @param id  文章的id
	 * @param status  1 通过   2 不通过
	 * @return
	 */
	@PostMapping("pass")
	@ResponseBody
	public boolean pass(HttpServletRequest request,Integer id,Integer status) {
		
		int result = articleService.check(id,status);
		return result>0;
		
	}
	
	/**
	 * 设置热点文章
	 * @param request
	 * @param id
	 * @param status
	 * @return
	 */
	@PostMapping("sethot")
	@ResponseBody
	public boolean sethot(HttpServletRequest request,Integer id,Integer status) {
		
		int result = articleService.setHot(id,status);
		return result>0;
		
	}
	
// ## 用户(user) ##----------------------------------------------------------------------------------------------------------

// ## 主页(index) ##----------------------------------------------------------------------------------------------------------
	/**
	 * 查询文章返回主页文章列表页面
	 * @param request
	 * @param cid     文章的分类Id
	 * @return
	 */
	@RequestMapping("listbyCatId")
	public String getListByCatId(HttpServletRequest request, @RequestParam(defaultValue = "0") Integer channelId,
			@RequestParam(defaultValue = "0") Integer catId, @RequestParam(defaultValue = "1",value="page") Integer pageNum) {

		PageInfo<Article> arPage = articleService.list(pageNum, channelId, catId);
		
		  String pageString = PageUtil.page(arPage.getPageNum(), arPage.getPages(),
		  "/article/listbyCatId?catId="+catId, arPage.getPageSize());
		  request.setAttribute("pageStr", pageString);
		 
		request.setAttribute("pageInfo", arPage);
		return "index/article/list";
	}
	
	/**
	 * 获取热门文章列表，返回到主页文章列表页面
	 * @param request
	 * @param pageSize
	 * @param pageNum
	 * @return
	 */
	@RequestMapping("hots")
	public String hots(HttpServletRequest request, 
			 @RequestParam(defaultValue = "") String key ,
			 @RequestParam( value="pageSize",defaultValue = "3") Integer pageSize,
			 @RequestParam(value="page",defaultValue = "1") Integer pageNum) {
		
		
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
			List<Article> arPage =new ArrayList<Article>(list);
			pageInfo = new PageInfo<Article>(arPage);
			pageString=PageUtil.page(pageNum, pageList.getTotalPages(), "/article/hots?key="+key, 3);
			
		}
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("pageStr", pageString);
		return "index/hot/list";
	}
	
	/**
	 * 根据文章id查找文章并返回主页文章详情页
	 * @param request
	 * @param aId
	 *            文章的id
	 * @return
	 */
	@RequestMapping("getDetail")
	public String getDetail(HttpServletRequest request, Integer aId) {

		Article article = articleService.findById(aId);
		PageInfo<Article> list = articleService.list(0, article.getChannelId(), 0);
		List<Article> articleList=list.getList();
		//增加点击量
		articleService.addhits(article);
		//查询评论文章天梯
		PageInfo<Article> desclist=articleService.getcommentdesc();
		int res=0;
		for(int i=0;i<articleList.size();i++) {
			if(articleList.get(i).getId()==article.getId()) {
				res=i;
				break;
			}
		}
		if(articleList.size()<=1) {
			request.setAttribute("mes", "only page");
		}
		else if(res==0) {
			request.setAttribute("mes", "this is one page");
			request.setAttribute("nextArticle", articleList.get(res+1));
		}else if(res==articleList.size()-1){
			request.setAttribute("mes", "this is last page");
			request.setAttribute("lastArticle", articleList.get(res-1));
		}else {
			request.setAttribute("mes", "it is ok");
			request.setAttribute("nextArticle", articleList.get(res+1));
			request.setAttribute("lastArticle", articleList.get(res-1));
		}
		request.setAttribute("article", article);
		request.setAttribute("CommentDesc",desclist.getList());
		return "index/article/detail";
	}
	
	
// ## 我的(my) ##----------------------------------------------------------------------------------------------------------
	
	/**
	 * 前往我的文章发布页面
	 * @return
	 */
	@RequestMapping("toPublish")
	public String toPublish() {

		return "my/article/publish";
	}
	
	/**
	 * 进行我的文章发布
	 * @param request
	 * @param img
	 * @param article
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@RequestMapping("publish")
	@ResponseBody
	public boolean publish(HttpServletRequest request,
			@RequestParam("file") MultipartFile img, Article article) 
					throws IllegalStateException, IOException {

		 CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		    // 设置编码
		    commonsMultipartResolver.setDefaultEncoding("utf-8");
		    
		    
		 // 判断是否有文件上传
		if (commonsMultipartResolver.isMultipart(request) && img != null) {
			log.debug("img777  is " + img );
			// 获取原文件的名称
			String oName = img.getOriginalFilename();
			log.debug("oName is " + oName );
			
			// 得到扩展名
			String suffixName = oName.substring(oName.lastIndexOf('.'));
			// 新的文件名称
			String newName = UUID.randomUUID() + suffixName;
			img.transferTo(new File("D:\\pic\\" + newName));//另存
			article.setPicture(newName);//
		}
		User loginUser = (User)request.getSession().getAttribute(ConstantFinal.USER_SESSION_KEY);
		if(loginUser==null)
			return false;
		article.setUserId(loginUser.getId());
		int result = articleService.add(article);
		return result > 0;
	}
	
	/**
	 * 获取某人文章，并返回个人文章列表
	 * @param request
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@GetMapping("listMyArticle")
	public String listMyArticle(HttpServletRequest request,
			@RequestParam(value="page",defaultValue= "1") int pageNum,
			@RequestParam(defaultValue= "5") int pageSize) {
		
		// 获取当前登陆的用户
		User currUser = (User)request.getSession().getAttribute(ConstantFinal.USER_SESSION_KEY);
		if(currUser==null)  return "my/article/list";
		
		PageInfo<Article> articlePage = articleService.getByUserId(currUser.getId(),pageNum,pageSize);
		System.out.println("articlePage is "  + articlePage);
		request.setAttribute("myarticles", articlePage);
		
		String pageStr = PageUtil.page(articlePage.getPageNum(), articlePage.getPages(), "/article/listMyArticle", articlePage.getPageSize());
		
		request.setAttribute("pageStr", pageStr);
		
		return "my/article/list";
	}
	
	/**
	 * 获取文章信息用于回显，并返回到我的文章修改页面
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping("toUpdate")
	public String toUpdate(HttpServletRequest request,Integer id) {
		Article article = articleService.findById(id);
		request.setAttribute("article", article);
		request.setAttribute("content1", article.getContent());
		return "my/article/update";	
	}
	
	/**
	 * 修改文章
	 * @param id
	 * @param title
	 * @param content1
	 * @param channelId
	 * @param categoryId
	 * @return
	 */
	@RequestMapping("update")
	@ResponseBody
	public boolean Update(Integer id ,String title,String content1,Integer channelId,Integer categoryId) {
		return articleService.updatea(id,title,categoryId,channelId,content1)>0;
	}
	
// ## 公用(common) ##----------------------------------------------------------------------------------------------------------
	
	/**
	 * 获取所有的频道
	 * 
	 * @return
	 */
	@RequestMapping("getAllChn")
	@ResponseBody
	public List<Channel> getAllChn() {
		List<Channel> channels = channelService.getChannels();
		return channels;
	}
	
	/**
	 * 获取某个频道下的所有的分类
	 * 
	 * @return
	 */
	@RequestMapping("getCatsByChn")
	@ResponseBody
	public List<Category> getCatsByChn(Integer channelId) {
		List<Category> cats = catService.getCategoryByChId(channelId);
		return cats;
	}
}
