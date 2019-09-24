package com.huangzhipeng.cms.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.huangzhipeng.cms.entity.User;
import com.huangzhipeng.cms.service.UserService;
import com.huangzhipeng.cms.utils.ConstantFinal;
import com.huangzhipeng.cms.utils.PageUtil;


/**
*@author huangzhipeng
*@version 创建时间：2019年9月21日 下午5:05:21
*用户控制层
*/
@RequestMapping("user")
@Controller
public class UserController {

	@Autowired
	UserService userService;
	
//## 管理员(admin) ##----------------------------------------------------------------------------------------------------------
	/**
	 * 获取用户列表，返回到管理员管理用户页面，进行管理
	 * @param request
	 * @param name  模糊查询条件
	 * @return
	 */
	@RequestMapping("list")
	public String list(HttpServletRequest request,
			@RequestParam( defaultValue="") String name,
			@RequestParam(value="page",defaultValue="1") int pageNumber,
			@RequestParam(defaultValue="3")  int pageSize) {
		
		PageInfo<User> users =  userService.search(pageNumber,pageSize,name);
		String pageStr = PageUtil.page(users.getPageNum(), users.getPages(), "/user/list", users.getPageSize());
		request.setAttribute("pageStr", pageStr);
		request.setAttribute("pageuser", users);
		return "admin/user/list";
		
	}
	
	/**
	 * 修改用户的状态（管理员状态）
	 * @param request
	 * @param id
	 * @param locked
	 * @return
	 */
	@RequestMapping("update")
	@ResponseBody
	public boolean update(HttpServletRequest request,Integer id,Integer locked) {
		return userService.updateLocked(id,locked)>0;
		
	
	}
	
//## 用户(user) ##----------------------------------------------------------------------------------------------------------
		
	/**
	 *  跳转到注册的页面
	 * @return
	 */
	@GetMapping("register")
	public String register() {
		return "user/register";
	}
	
	/**
	 * 保存提交注册的数据(进行注册)
	 * @param request
	 * @param user
	 * @return
	 */
	@PostMapping("register")
	public String register(HttpServletRequest request,User user) {
		
		if(null==userService.findByName(user.getUsername())){
			User register = userService.register(user);
			if(register==null) {
				request.setAttribute("error", "注册失败,请与管理员联系");
				return "/user/register";
			}else {
				
				return "redirect:/user/login";
				
			}	
		}else {
			request.setAttribute("error", "注册失败,用户名已经存在");
			return "user/register";
		}
	}
	
	/**
	 *  跳转登录的页面
	 * @return
	 */
	@GetMapping("login")
	public String login() {
		return "user/login";
	}
	
	/**
	 *  登录判断
	 * @param request
	 * @param user
	 * @return
	 */
	@PostMapping("login")
	public String login(HttpServletRequest request,User user) {
		
		User loginUser=  userService.login(user);
				
		if(loginUser==null) {
			request.setAttribute("error", "用户密码错误");
			return "user/login";
		}else if(loginUser.getLocked()==1){
			request.setAttribute("error", "很遗憾，阁下已经被冻结了！！哈哈");
			return "user/login";
		}else
		{
			
			request.getSession().setAttribute(ConstantFinal.USER_SESSION_KEY, loginUser);
			return "1".equals(loginUser.getRole())?"redirect:/admin/index" : "redirect:/user/home";	
		}
		
	}
	
	/**
	 * 查看用户是否存在
	 * @param username
	 * @return
	 */
	@RequestMapping("check")
	@ResponseBody
	public boolean checkName(String username) {
		return (null==userService.findByName(username));
	}
	
//## 主页(index) ##----------------------------------------------------------------------------------------------------------

//## 我的(my) ##----------------------------------------------------------------------------------------------------------
	
	/**
	 * 登录成功跳转到我的首页
	 * @return
	 */
	@RequestMapping("home")
	public String home() {
		
		return "my/index";
		
	}
	
//## 公用(common) ##----------------------------------------------------------------------------------------------------------
	
	/**
	 * 退出用户
	 * @param request
	 * @return
	 */
	@RequestMapping("logout")
	public String logout(HttpServletRequest request) {
		request.getSession().removeAttribute(ConstantFinal.USER_SESSION_KEY);
		return "redirect:/index";
	}
}
