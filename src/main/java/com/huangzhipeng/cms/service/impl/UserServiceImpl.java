package com.huangzhipeng.cms.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.huangzhipeng.cms.dao.UserMapper;
import com.huangzhipeng.cms.entity.User;
import com.huangzhipeng.cms.entity.UserVo;
import com.huangzhipeng.cms.service.UserService;
import com.huangzhipeng.cms.utils.Md5Utils;

/**
*@author huangzhipeng
*@version 创建时间：2019年9月21日 下午4:26:56
*用户业务层
*/
@Service
public class UserServiceImpl implements UserService {

	
	@Autowired
	UserMapper userMapper;
	
	@Override
	public int update(User user) {
		return userMapper.update(user);
	}

	@Override
	public int updateLocked(Integer userId, Integer locked) {
		return userMapper.updateLocked( userId, locked);
	}

	@Override
	public User login(User user) {
		User loginUser = userMapper.findByName(user.getUsername());
		// 用户存在 而且 密码不为空  且加密后的密码与数据库中的保存一致
		if(loginUser!=null && user.getPassword() !=null 
				&& Md5Utils.md5(user.getPassword())
				.equals(loginUser.getPassword())){
			return loginUser;
		}
		return null;
	}

	@Override
	public User register(User user) {
		user.setCreateTime(new Date());//设置注册时间
		user.setLocked(0); // 不锁定
		user.setRole("0");//设置角色  普通用户
		user.setPassword(Md5Utils.md5(user.getPassword()));
		
		if(userMapper.add(user)>0) {
			// 添加成功
			return user;
		}else {
			return null;
		}
	}

	@Override
	public User findByName(String name) {
		return userMapper.findByName(name);
	}

	@Override
	public User query(UserVo uservo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User findById(Integer id) {
		return userMapper.findById(id);
	}

	@Override
	public PageInfo<User> search(int pageNumber, int pageSize, String name) {
		PageHelper.startPage(pageNumber,pageSize);
		List<User> users = userMapper.queryList(name);
		return new PageInfo<User>(users);
	}

}
