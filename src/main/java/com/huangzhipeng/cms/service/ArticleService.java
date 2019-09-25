package com.huangzhipeng.cms.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.huangzhipeng.cms.entity.Article;

/**
 * @author huangzhipeng
 * @version 创建时间：2019年9月21日 下午3:39:34 类功能说明
 */
public interface ArticleService {

	/**
	 * 发布一篇文章
	 * 
	 * @param article
	 * @return
	 */
	int post(Article article);

	/**
	 * 修改一篇文章
	 * 
	 * @param article
	 * @return
	 */
	int update(Article article);

	/**
	 * 删除 逻辑删除
	 * 
	 * @param article
	 * @return
	 */
	int logicDelete(Integer id);

	/**
	 * 批量的逻辑删除
	 * 
	 * @param ids
	 * @return
	 */
	int logicDeleteBatch(Integer[] ids);

	/**
	 * 添加一篇文章
	 * 
	 * @param article
	 * @return
	 */
	int add(Article article);

	/**
	 * 审核文章
	 * 
	 * @param id     文章id
	 * @param status 期望文章修改后的状态
	 * @return 修改数据的条数
	 */
	int check(Integer id, Integer status);

	/**
	 * 设置热门
	 * 
	 * @param id
	 * @param status
	 * @return
	 */
	int setHot(Integer id, Integer status);

	/**
	 *修改文章 
	 * @param id
	 * @param title
	 * @param categoryId
	 * @param channelId
	 * @param content1
	 * @return
	 */
	int updatea(Integer id, String title, Integer categoryId, Integer channelId, String content1);

	/**
	 * 通过id寻找指定文章
	 * 
	 * @param articleId
	 * @return
	 */
	Article findById(Integer articleId);

	/**
	 * 查询文章列表，可以指定频道id和分类id
	 * 
	 * @param pageNum
	 * @param channelId
	 * @param cid
	 * @return
	 */
	PageInfo<Article> list(Integer pageNum, Integer channelId, Integer cid);

	/**
	 * 查询指定用户的文章
	 * 
	 * @param id
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	PageInfo<Article> getByUserId(Integer id, int pageNum, int pageSize);

	/**
	 * 获取指定审核状态的文章
	 * 
	 * @param status
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	PageInfo<Article> checkList(Integer status, int pageNumber, int pageSize);

	/**
	 * 获取热门文章
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	PageInfo<Article> listhots(String title,Integer pageNum, Integer pageSize);

	/**
	 * 获取最新文章
	 * 
	 * @return
	 */
	List<Article> last();

	/**   
	 * @Title:         getcommentdesc   
	 * @Description:   TODO
	 * @param:         @return      
	 * @return:        PageInfo<Article>     
	 * @date:          2019年9月25日 上午10:06:38   
	 * @throws   
	 */
	PageInfo<Article> getcommentdesc();
}
