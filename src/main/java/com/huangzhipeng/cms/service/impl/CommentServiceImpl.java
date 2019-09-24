package com.huangzhipeng.cms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.huangzhipeng.cms.dao.ArticleMapper;
import com.huangzhipeng.cms.dao.CommentMapper;
import com.huangzhipeng.cms.entity.Comment;
import com.huangzhipeng.cms.service.CommentService;

/**
*@author huangzhipeng
*@version 创建时间：2019年9月23日 下午8:42:44
*评论业务层
*/
@Service
public class CommentServiceImpl implements CommentService {

	
	@Autowired
	CommentMapper commentMapper; 
	
	@Autowired
	ArticleMapper articleMapper; 
	
	@Override
	public int post(Comment commnet) {
		if( commentMapper.add(commnet)>0){
			articleMapper.increaseCommentCnt(commnet.getArticleId());
		}
		return 1;
	}

	@Override
	public int del(Integer userId, Integer cid) {
		return commentMapper.delete(userId,cid);
	}

	@Override
	public PageInfo<Comment> getCommentsByArticle(Integer articleId, Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum,pageSize);
		return new PageInfo(commentMapper.listByArticle(articleId));
	}

	@Override
	public PageInfo<Comment> getCommentsByUser(Integer userId, Integer page, Integer pageSize) {
		PageHelper.startPage(page,pageSize);
		return new PageInfo(commentMapper.listByUser(userId));
	}

}
