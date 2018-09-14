package com.jt.cart.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.cart.pojo.Cart;
import com.jt.cart.service.CartService;
import com.jt.common.vo.SysResult;

@Controller
@RequestMapping("/cart")
public class CartController {

	@Autowired
	private CartService cartservice;
	
	private static ObjectMapper objectMapper = new ObjectMapper();
	
	@RequestMapping("/query/{userId}")
	@ResponseBody
	public SysResult findCartListByUserId(@PathVariable Long userId) {
		
		try {
			List<Cart> cartList = cartservice.findCartListByUserId(userId);
			return SysResult.oK(cartList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "购物车查询失败");
	}
	
	@RequestMapping("/update/num/{userId}/{itemId}/{num}")
	@ResponseBody
	public SysResult updateCartNum(@PathVariable Long userId, @PathVariable Long itemId, @PathVariable Integer num) {
		try {
			Cart cart = new Cart();
			cart.setUserId(userId);
			cart.setItemId(itemId);
			cart.setNum(num);
			cartservice.updateCartNum(cart);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return SysResult.build(201, "购物车更新失败");
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public SysResult saveCart(String cartJSON) {
		try {
			Cart cart = objectMapper.readValue(cartJSON, Cart.class);
			cartservice.save(cart);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "加入购物车失败");
	}
	
	@RequestMapping("/delete/{userId}/{itemId}")
	@ResponseBody
	public SysResult deleteCart(@PathVariable Long userId, @PathVariable Long itemId) {
		try {
			Cart cart = new Cart();
			cart.setUserId(userId);
			cart.setItemId(itemId);
			cartservice.deleteCart(cart);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "加入购物车失败");
	}
	
}
