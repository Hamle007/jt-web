package com.jt.web.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.vo.SysResult;
import com.jt.web.pojo.User;
import com.jt.web.service.UserService;

import redis.clients.jedis.JedisCluster;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private JedisCluster jedisCluster;
	
	/**
	 * 登出
	 * @return
	 */
	@RequestMapping("/logout")
	public String logOut(HttpServletRequest request, HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		String token = null;
		for(Cookie c : cookies){
			if("JT_TICKET".equals(c.getName())){
				token = c.getValue();
				break;
			}
		}
		jedisCluster.del(token);
		Cookie cookie = new Cookie("JT_TICKET", "");
		cookie.setPath("/");
		cookie.setMaxAge(0);
		response.addCookie(cookie);
		return "redirect:/index.html";
	} 
	
	@RequestMapping("/{moduleName}")
	public String module(@PathVariable String moduleName) {
		return moduleName;
	}
	
	@RequestMapping("/doRegister")
	@ResponseBody
	public SysResult saveUser(User user) {
		try {
			userService.saveUser(user);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "用户新增失败");
	}
	
	@RequestMapping("/doLogin")
	@ResponseBody
	public SysResult findUserByUP(User user, HttpServletResponse response) {
		try {
			String token = userService.findUserByUP(user);
			if(StringUtils.isEmpty(token)){
				throw new RuntimeException();
			}
			Cookie cookie = new Cookie("JT_TICKET", token);
			cookie.setPath("/");
			cookie.setMaxAge(7 * 3600 * 24);
			response.addCookie(cookie);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "登录失败");
	}
}
