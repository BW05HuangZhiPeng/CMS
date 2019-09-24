package com.huangzhipeng.cms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.huangzhipeng.cms.entity.User;


/**
*@author huangzhipeng
*@version 创建时间：2019年9月21日 上午10:29:44
*用户Mapper
*/
@Mapper
public interface UserMapper {
	
//## 增加 ##----------------------------------------------------------------------------------------------------------

	/**
	 * 增加一个新的用户（用于注册）
	 * @param user
	 * @return
	 */
	int add(User user);
	
//## 删除 ##----------------------------------------------------------------------------------------------------------

//## 修改 ##----------------------------------------------------------------------------------------------------------
	
	/**
	 * 修改一个用户信息
	 * @param user
	 * @return
	 */
	int update(User user);
	
	/**
	 * 修改用户的锁定状态
	 * @param userId
	 * @param locked
	 * @return
	 */
	int updateLocked(@Param("userId") Integer userId, @Param("locked") Integer locked);
	
//## 查找 ##----------------------------------------------------------------------------------------------------------

	/**
	 *  根据用户名查找  用于登录的时候和  注册用户名称唯一性校验
	 * @param userName
	 * @return
	 */
	User findByName(String userName);
	
	/**
	 * 根据用户id查找用户
	 * @param id
	 * @return
	 */
	User findById(Integer id);
	
	/**
	 *  获取用户的列表
	 * @param user
	 * @return
	 */
	List<User> select(User user);

	/**
	 * 
	 * @param name
	 * @return
	 */
	List<User> queryList(@Param("name") String name);


	
	
}
