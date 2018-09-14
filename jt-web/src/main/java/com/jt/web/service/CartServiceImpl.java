package com.jt.web.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.HttpClientService;
import com.jt.common.vo.SysResult;
import com.jt.web.pojo.Cart;

@Service
public class CartServiceImpl implements CartService{

	@Autowired
	private HttpClientService httpClient;
	
	private static ObjectMapper objectMapper = new ObjectMapper();
	
	@Override
	public List<Cart> findCartListByUserId(Long userId) {
		String uri = "http://cart.jt.com/cart/query/"+userId;
		List<Cart> cartList = new ArrayList<Cart>();
		String sysResultJSON = httpClient.doGet(uri);
		try {
			SysResult sysResult = objectMapper.readValue(sysResultJSON, SysResult.class);
			cartList = (List<Cart>)sysResult.getData();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cartList;
	}

	@Override
	public void updateCartNum(Cart cart) {
		String uri = "http://cart.jt.com/cart/update/num/"+cart.getUserId()+"/"+cart.getItemId()+"/"+cart.getNum();
		try {
			String sysResultJSON = httpClient.doGet(uri);
			SysResult sysResult = objectMapper.readValue(sysResultJSON, SysResult.class);
			if(sysResult.getStatus() != 200){
				throw new RuntimeException();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		
		
	}

	@Override
	public void saveCart(Cart cart) {
		String uri = "http://cart.jt.com/cart/save";
		Map<String,String> map = new HashMap<>();
		String cartJSON = null;
		try {
			cartJSON = objectMapper.writeValueAsString(cart);
			map.put("cartJSON", cartJSON);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		httpClient.doPost(uri, map);
		System.out.println("购物车加入成功！");
	}

	@Override
	public void deleteCart(Cart cart) {
		String uri = "http://cart.jt.com/cart/delete/"+cart.getUserId()+"/"+cart.getItemId();
		httpClient.doGet(uri);
		System.out.println("购物车删除成功！");
		
	}

}
