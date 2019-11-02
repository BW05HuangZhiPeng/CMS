package com.huangzhipeng.cms.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.huangzhipeng.cms.dao.ArticleMapper;
import com.huangzhipeng.cms.entity.Article;
import com.huangzhipeng.cms.service.ArticleService;

/**
*@author huangzhipeng
*@version 创建时间：2019年9月21日 下午3:49:25
*文章业务层
*/
@Service
public class ArticleServiceImpl implements ArticleService {
	
	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;
	
	@Autowired
	ArticleMapper articleMapper;
	
	@Autowired
	private RedisTemplate<String,String> redisTemplate;
	
	@Autowired
	private  KafkaTemplate<String,String> kafkaTemplate;
	
	@Override
	public int post(Article article) {
		return articleMapper.add(article);
	}

	@Override
	public int update(Article article) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int logicDelete(Integer id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int logicDeleteBatch(Integer[] ids) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int add(Article article) {
		return articleMapper.add(article);
	}

	@Override
	public int check(Integer id, Integer status) {
		int updateStatus = articleMapper.updateStatus(id,status);
		Article findById = articleMapper.findById(id);
		
		Gson gson = new Gson();
		if (status==2) {
			String json = gson.toJson(findById);
			//发送到消费端
			kafkaTemplate.sendDefault(json);
			ListOperations<String, String> opsForList = redisTemplate.opsForList();
			opsForList.leftPush("listlast",json);
		}else {
			elasticsearchTemplate.delete(Article.class,id.toString());
		}
		return updateStatus;
	}

	@Override
	public int setHot(Integer id, Integer status) {
		Article article = articleMapper.findById(id);
		 ListOperations<String, String> opsForList = redisTemplate.opsForList();
		 Gson gson = new Gson();
		 String json = gson.toJson(article);
		 opsForList.leftPush("listhots",json);
		return articleMapper.updateHot(id,status);
	}

	
	@Override
	public int updatea(Integer id, String title, Integer categoryId, Integer channelId, String content1) {
		return articleMapper.updatea(id, title, categoryId, channelId, content1);
	}
	
	@Override
	public Article findById(Integer articleId) {
		return articleMapper.findById(articleId);
	}

	@Override
	public PageInfo<Article> list(Integer pageNum, Integer channelId, Integer cid) {
		if(pageNum==0) {
			PageHelper.startPage(pageNum, articleMapper.list(0, 0).size());
			List<Article> articles =   articleMapper.list(channelId,cid);
			return new PageInfo<Article>(articles);
		}else {
			PageHelper.startPage(pageNum, 3);
			List<Article> articles =   articleMapper.list(channelId,cid);
			return new PageInfo<Article>(articles);
		}
		
	}

	@Override
	public PageInfo<Article> getByUserId(Integer id, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		PageInfo<Article> pageInfo = new PageInfo<Article>(articleMapper.listByUser(id));
		
		return pageInfo;
	}

	@Override
	public PageInfo<Article> checkList(Integer status, int pageNumber, int pageSize) {
		PageHelper.startPage(pageNumber, pageSize);
		List<Article> articles=  articleMapper.checkList(status);
		
		return new PageInfo<Article>(articles);
	}

	//查询热门文章利用redis缓存
	@Override
	public List<Article> listhots(String title,Integer pageNum, Integer pageSize) {
		
		if (redisTemplate.hasKey("listhots")&&(title.trim()==null||"".equals(title.trim()))) {
			 ListOperations<String, String> opsForList = redisTemplate.opsForList();
			 List<String> range = opsForList.range("listhots",(pageNum-1)*pageSize,pageNum*pageSize-1);
			 Gson gson = new Gson();
			 List<Article> articles = new ArrayList<Article>();
			 int listcount=0;
			 for (String string : range) {
				Article article = gson.fromJson(string,Article.class);
				articles.add(article);
				listcount++;
			 }
			 return articles;
		}
		
		  List<Article> articles= articleMapper.hotList(title);
		  ListOperations<String, String> opsForList = redisTemplate.opsForList();
		  Gson gson = new Gson();
		  List<Article> articles2= articleMapper.hotList(title);
		  for (Article article : articles2) {
			  String json = gson.toJson(article);
			  opsForList.leftPush("listhots",json);
		  }
		return articles;
	}

	@Override
	public List<Article> last() {
		ListOperations<String, String> opsForList = redisTemplate.opsForList();
		List<Article> articles =null;
		if (redisTemplate.hasKey("listlast")) {
			List<String> range = opsForList.range("listlast",0,-1);
			 Gson gson = new Gson();
			 articles = new ArrayList<Article>();
			 for (String string : range) {
				Article article = gson.fromJson(string,Article.class);
				articles.add(article);
			 }
		}else {
			PageHelper.startPage(1,5);
			articles = articleMapper.lastArticles();
			PageInfo<Article> pageInfo = new PageInfo<Article>(articles);
			 Gson gson = new Gson();
			for (Article article :pageInfo.getList()) {
				String json = gson.toJson(article);
				opsForList.leftPush("listlast",json);
			}
			return pageInfo.getList();
		}
		return articles;
				
	}

	/**   
	 * @Title:         getcommentdesc
	 * @Description:   TODO
	 * @date:          2019年9月25日 上午10:06:49  
	 * @return   
	 * @see com.huangzhipeng.cms.service.ArticleService#getcommentdesc()   
	 */
	@Override
	public PageInfo<Article> getcommentdesc() {
		 PageHelper.startPage(1,10);
		 List<Article> list=articleMapper.getcommentdesc();
		return new PageInfo<Article>(list);
	}

	/**   
	 * @Title:         addhits
	 * @Description:   TODO
	 * @date:          2019年10月16日 下午7:30:11  
	 * @param aId   
	 * @see com.huangzhipeng.cms.service.ArticleService#addhits(java.lang.Integer)   
	 */
	@Override
	public void addhits(Article article) {
		//数据库中增加点击量
		articleMapper.addhits(article.getId());
		
	}

	/**   
	 * @return 
	 * @Title:         articlehitsdesc
	 * @Description:   TODO
	 * @date:          2019年10月16日 下午7:35:42     
	 * @see com.huangzhipeng.cms.service.ArticleService#articlehitsdesc()   
	 */
	@Override
	public List<Article> articlehitsdesc() {
		ListOperations<String, String> opsForList = redisTemplate.opsForList();
		List<Article> articles=null;
		if (redisTemplate.hasKey("articlehitsdesc")) {
			List<String> range = opsForList.range("articlehitsdesc",0,-1);
			 Gson gson = new Gson();
			 articles = new ArrayList<Article>();
			 for (String string : range) {
				Article article = gson.fromJson(string,Article.class);
				articles.add(article);
			 }
		}else {
			articles = articleMapper.articlehitsdesc();
			 Gson gson = new Gson();
			for (Article article : articles) {
				String json = gson.toJson(article);
				opsForList.leftPush("articlehitsdesc",json);
			}
		}
		
		return articles;
	}

	/**   
	 * @Title:         articleAll
	 * @Description:   TODO
	 * @date:          2019年10月23日 下午7:05:47  
	 * @return   
	 * @see com.huangzhipeng.cms.service.ArticleService#articleAll()   
	 */
	@Override
	public List<Article> articleAll() {
		
		return articleMapper.articleAll();
	}

	/**   
	 * @Title:         gethotscount
	 * @Description:   TODO
	 * @date:          2019年10月24日 下午2:22:04  
	 * @return   
	 * @see com.huangzhipeng.cms.service.ArticleService#gethotscount()   
	 */
	@Override
	public int gethotscount() {
		ListOperations<String, String> opsForList = redisTemplate.opsForList();
		return opsForList.size("listhots").intValue();
	}
	

}

