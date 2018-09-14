package com.jt.web.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.web.pojo.User;
import com.jt.web.thread.UserThreadLocal;

import redis.clients.jedis.JedisCluster;

public class UserInterceptor implements HandlerInterceptor{

	@Autowired
	private JedisCluster jedisCluster;
	
	private static ObjectMapper objectMapper = new ObjectMapper();
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 判断用户是否登录，token
		// 判断redis中是否有数据
		Cookie[] cookies = request.getCookies();
		String token = null;
		for (Cookie cookie : cookies) {
			if("JT_TICKET".equals(cookie.getName())){
				token = cookie.getValue();
				break;
			}
		}
		System.out.println(token);
		if(!StringUtils.isEmpty(token)){
			// 检查缓存
			String userJSON = jedisCluster.get(token);
			User user = objectMapper.readValue(userJSON, User.class);
			UserThreadLocal.set(user);
			// request.getSession().setAttribute("JT_USER", user);
			if(!StringUtils.isEmpty(userJSON)){
				return true;
			}
		}
		response.sendRedirect("/user/login.html");
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		UserThreadLocal.remove();
	}
	
}
