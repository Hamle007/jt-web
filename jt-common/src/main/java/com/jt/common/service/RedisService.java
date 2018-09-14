package com.jt.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;
//import redis.clients.jedis.ShardedJedis;
//import redis.clients.jedis.ShardedJedisPool;


@Service
public class RedisService {

//	@Autowired(required=false)
//	private ShardedJedisPool jedisPool;
	@Autowired(required=false)
	private JedisSentinelPool sentinelPool;

	/*
	public void set(String key, String value) {
		ShardedJedis resource = jedisPool.getResource();
		resource.set(key, value);
		jedisPool.returnResource(resource);
	}
	
	public void set(String key, int seconds, String value) {
		ShardedJedis resource = jedisPool.getResource();
		resource.setex(key, seconds, value);
		jedisPool.returnResource(resource);
	}
	
	public String get(String key) {
		ShardedJedis resource = jedisPool.getResource();
		String value = resource.get(key);
		jedisPool.returnResource(resource);
		return value;
	}
	*/
	public void set(String key, String value) {
		Jedis resource = sentinelPool.getResource();
		resource.set(key, value);
		sentinelPool.returnResource(resource);
	}
	
	public void set(String key, int seconds, String value) {
		Jedis resource = sentinelPool.getResource();
		resource.setex(key, seconds, value);
		sentinelPool.returnResource(resource);
	}
	
	public String get(String key) {
		Jedis resource = sentinelPool.getResource();
		String result = resource.get(key);
		sentinelPool.returnResource(resource);
		return result;
	}
	
}
