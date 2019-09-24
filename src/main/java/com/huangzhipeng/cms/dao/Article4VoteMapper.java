package com.huangzhipeng.cms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;

import com.huangzhipeng.cms.entity.Article4Vote;
import com.huangzhipeng.cms.entity.VoteStatic;


/**
 * @author huangzhipeng
 * @version 创建时间：2019年9月21日 上午10:07:47 关于投票的mapper
 */
@Mapper
public interface Article4VoteMapper {

//## 增加 ##----------------------------------------------------------------------------------------------------------

	/**
	 * 发布一个投票包括标题，内容
	 * 
	 * @param av
	 * @return
	 */
	int add(Article4Vote av);

	/**
	 * 增加一条投票信息，包括投票文章，投票人，投票项
	 * 
	 * @param userId
	 * @param articleId
	 * @param option
	 * @return
	 */
	int vote(@Param("userId") Integer userId, @Param("articleId") Integer articleId, @Param("option") Character option);

//## 删除 ##----------------------------------------------------------------------------------------------------------

//## 修改 ##----------------------------------------------------------------------------------------------------------

//## 查找 ##----------------------------------------------------------------------------------------------------------	

	/**
	 * 查询已有的投票信息
	 * 
	 * @param id
	 * @return
	 */
	Article4Vote getById(Integer id);

	/**
	 * 统计每个人所获取的票数
	 * 
	 * @param userId
	 * @param articleId
	 * @param option
	 * @return
	 */
	List<VoteStatic> getVoteStatics(Integer articleId);

	/**
	 * 查询最新10条投票信息
	 * 
	 * @return
	 */
	@ResultType(Article4Vote.class)
	List<Article4Vote> list();

}
