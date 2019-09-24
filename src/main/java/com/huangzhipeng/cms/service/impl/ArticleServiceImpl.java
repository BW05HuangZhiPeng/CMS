package com.huangzhipeng.cms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
	ArticleMapper articleMapper;
	
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
		System.err.println(article);
		return articleMapper.add(article);
	}

	@Override
	public int check(Integer id, Integer status) {
		return articleMapper.updateStatus(id,status);
	}

	@Override
	public int setHot(Integer id, Integer status) {
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

	@Override
	public PageInfo<Article> listhots(String title,Integer pageNum, Integer pageSize) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageNum, pageSize);
		System.out.println("title   is ============ " + title);
		List<Article> articles=  articleMapper.hotList(title);
		
		return new PageInfo<Article>(articles);
	}

	@Override
	public List<Article> last() {
		return articleMapper.lastArticles();
	}
	

}

