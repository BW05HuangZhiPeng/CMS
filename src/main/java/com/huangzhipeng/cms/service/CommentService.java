package com.huangzhipeng.cms.service;

import com.github.pagehelper.PageInfo;
import com.huangzhipeng.cms.entity.Comment;

/**
*@author huangzhipeng
*@version 创建时间：2019年9月23日 下午8:39:42
*类功能说明
*/
public interface CommentService {
	
	/**
	 * 发布一个评论
	 * @param commnet
	 * @return
	 */
	int post(Comment commnet);
	
	/**
	 * 删除一个评论
	 * @param userId
	 * @param id
	 * @return
	 */
	int del(Integer userId, Integer cid);
	
	/**
	 * 查询指定文章的评论
	 * @param articleId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	PageInfo<Comment> getCommentsByArticle(Integer articleId,Integer pageNum,Integer pageSize);
	
	/**
	 * 查询指定用户的评论
	 * @param id
	 * @param page
	 * @param pageSize
	 * @return
	 */
	PageInfo<Comment> getCommentsByUser(Integer userId, Integer page,
			Integer pageSize);
	
	
}
