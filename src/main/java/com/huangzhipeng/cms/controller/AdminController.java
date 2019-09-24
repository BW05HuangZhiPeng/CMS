package com.huangzhipeng.cms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
*@author huangzhipeng
*@version 创建时间：2019年9月21日 下午4:30:22
*管理员控制层
*/
@Controller
@RequestMapping("admin")
public class AdminController {
	
//## 管理员(admin) ##----------------------------------------------------------------------------------------------------------
	/**
	 * 跳转到管理员管理页面
	 * @return
	 */
	@GetMapping("index")
	public String index() {
		return "admin/index";
	}
	
//## 用户(user) ##----------------------------------------------------------------------------------------------------------

//## 主页(index) ##----------------------------------------------------------------------------------------------------------

//## 我的(my) ##----------------------------------------------------------------------------------------------------------

//## 公用(common)  ##----------------------------------------------------------------------------------------------------------
	
	

}
