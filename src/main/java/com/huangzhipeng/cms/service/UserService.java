package com.huangzhipeng.cms.service;

import com.github.pagehelper.PageInfo;
import com.huangzhipeng.cms.entity.User;
import com.huangzhipeng.cms.entity.UserVo;

/**
*@author huangzhipeng
*@version 创建时间：2019年9月21日 下午3:40:00
*类功能说明
*/
public interface UserService {

	
	/**
	 * 修改一个用户信息
	 * @param user
	 * @return
	 */
	int update(User user);
	
	/**
	 * 
	 * @param id  用户的id
	 * @param locked 是否锁定  1 表示锁定 0 表示不锁定
	 * @return
	 */
	int updateLocked(Integer id, Integer locked);
	
	/**
	 * 登录判断
	 * @return
	 */
	User login(User user);
	
	/**
	 * 注册一个用户
	 * @return
	 */
	User register(User user);
	
	/**
	 * 通过用户名寻找用户
	 * @param name
	 * @return
	 */
	User findByName(String name);
	
	/**
	 * 
	 * @param uservo
	 * @return
	 */
	User query(UserVo  uservo);
	
	
	
	/**
	 * 根据用户id查找用户
	 * @param id
	 * @return
	 */
	User findById(Integer id);
	
	/**
	 * 根据用户名模糊查询
	 * @param pageNumber
	 * @param pageSize
	 * @param name
	 * @return
	 */
	PageInfo<User> search(int pageNumber, int pageSize, String name);
	
	
	
}
