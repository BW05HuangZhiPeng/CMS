package com.huangzhipeng.cms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.huangzhipeng.cms.entity.Comment;



/**
*@author huangzhipeng
*@version 创建时间：2019年9月23日 下午8:32:33
*评论mapper
*/
public interface CommentMapper {
//## 增加 ##----------------------------------------------------------------------------------------------------------
	/**
	 * 添加一条评论
	 * @param commnet
	 * @return
	 */
	int add(Comment commnet);
//## 删除 ##----------------------------------------------------------------------------------------------------------
	/**
	 * 删除一条评论
	 * @param userId
	 * @param cid
	 * @return
	 */
	int delete(@Param("userId") Integer userId,@Param("cid")  Integer cid);
//## 修改 ##----------------------------------------------------------------------------------------------------------

//## 查找 ##----------------------------------------------------------------------------------------------------------
	/**
	 * 通过文章id查询评论
	 * @param articleId
	 * @return
	 */
	List<Comment> listByArticle(Integer articleId);
	
	/**
	 * 通过用户查询评论
	 * @param userId
	 * @return
	 */
	List<Comment> listByUser(Integer userId);
}
