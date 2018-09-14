package com.jt.web.thread;

import com.jt.web.pojo.User;

public class UserThreadLocal {

	private static ThreadLocal<User> userThread = new ThreadLocal<>();
	
	public static User get() {
		return userThread.get();
	}
	
	public static void set(User user) {
		userThread.set(user);
	}
	
	// 防止内存泄漏
	public static void remove() {
		userThread.remove();
	}
}
