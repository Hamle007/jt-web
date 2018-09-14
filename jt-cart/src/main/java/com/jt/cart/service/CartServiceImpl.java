package com.jt.cart.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.cart.mapper.CartMapper;
import com.jt.cart.pojo.Cart;
import com.jt.common.service.BaseService;

@Service
public class CartServiceImpl extends BaseService<Cart> implements CartService{
	
	@Autowired
	private CartMapper cartMapper;

	@Override
	public List<Cart> findCartListByUserId(Long userId) {
		Cart cart = new Cart();
		cart.setUserId(userId);
		return cartMapper.select(cart);
	}

	@Override
	public void updateCartNum(Cart cart) {
		cart.setUpdated(new Date());
		cartMapper.updateCartNum(cart);
	}

	@Override
	public void save(Cart cart) {
		Cart cartDB = cartMapper.findCartByUI(cart);
		if(cartDB != null){
			int num = cart.getNum() + cartDB.getNum();
			cartDB.setNum(num);
			cartDB.setUpdated(new Date());
			cartMapper.updateByPrimaryKeySelective(cartDB);
		}else{
			cart.setCreated(new Date());
			cart.setUpdated(cart.getCreated());
			cartMapper.insert(cart);
		}
		
		
		
	}

	@Override
	public void deleteCart(Cart cart) {
		cartMapper.delete(cart);
		
	}
}
