package com.jt.web.service;

import java.util.List;

import com.jt.web.pojo.Cart;

public interface CartService {

	List<Cart> findCartListByUserId(Long userId);

	void updateCartNum(Cart cart);

	void saveCart(Cart cart);

	void deleteCart(Cart cart);

}
