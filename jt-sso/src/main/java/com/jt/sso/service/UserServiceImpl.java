package com.jt.sso.service;

import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.BaseService;
import com.jt.sso.mapper.UserMapper;
import com.jt.sso.pojo.User;

import redis.clients.jedis.JedisCluster;

@Service
public class UserServiceImpl extends BaseService<User> implements UserService{
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private JedisCluster jedisCluster;
	
	private static ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public boolean findUserByName(String param, Integer type) {
		String column = null;
		switch(type){
		case 1:	column = "username"; break;
		case 2: column = "phone"; break;
		case 3: column = "email"; break;
		default: break;
		}
		int count = userMapper.findCheck(column, param);
		return count == 0 ? false : true;
	}

	@Override
	public void saveUser(User user) {
		// 密码加密
		String md5Password = DigestUtils.md5Hex(user.getPassword());
		user.setPassword(md5Password);
		user.setCreated(new Date());
		user.setUpdated(new Date());
		userMapper.insert(user);
	}

	@Override
	public String findUserByUP(User user) {
		String md5Password = DigestUtils.md5Hex(user.getPassword());
		user.setPassword(md5Password);
		User userDB = super.queryByWhere(user);
		if(userDB == null){
			throw new RuntimeException();
		}
		String token = DigestUtils.md5Hex("JT_TICKET"+System.currentTimeMillis()+userDB.getUsername());
		String jsonUser = null;
		try {
			jsonUser = objectMapper.writeValueAsString(userDB);
			jedisCluster.setex(token, 3600*24*7, jsonUser);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return token;
	}
}
