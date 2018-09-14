package com.jt.sso.mapper;

import org.apache.ibatis.annotations.Param;

import com.jt.common.mapper.SysMapper;
import com.jt.sso.pojo.User;

public interface UserMapper extends SysMapper<User>{

	int findCheck(@Param("column") String column, @Param("param")String param);
	
}
