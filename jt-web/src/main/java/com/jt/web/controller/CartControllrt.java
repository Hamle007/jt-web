package com.jt.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.jt.common.vo.SysResult;
import com.jt.web.pojo.Cart;
import com.jt.web.pojo.User;
import com.jt.web.service.CartService;
import com.jt.web.thread.UserThreadLocal;


@Controller
@RequestMapping("/cart")
public class CartControllrt {

	@Autowired
	private CartService cartService;
	User user;
	// 跳转购物车页面
	@RequestMapping("/show")
	public String findCartListByUserId(Model model) {
		//User user = UserThreadLocal.get();
		user = UserThreadLocal.get();
		Long userId = user.getId();
		List<Cart> cartList = cartService.findCartListByUserId(userId);
		model.addAttribute("cartList", cartList);
		return "cart";
	}
	
	@RequestMapping("/add/{itemId}")
	public String saveCart(Cart cart, @PathVariable Long itemId) {
		//User user = UserThreadLocal.get();
		Long userId = user.getId();
		cart.setItemId(itemId);
		cart.setUserId(userId);
		cartService.saveCart(cart);
		return "redirect:/cart/show.html";
	}
	
	@RequestMapping("/update/num/{itemId}/{num}")
	@ResponseBody
	public SysResult updateCartNum(@PathVariable Long itemId, @PathVariable Integer num, HttpServletRequest request) {
		try {
			//User user = UserThreadLocal.get();
			Cart cart = new Cart();
			cart.setUserId(user.getId());
			cart.setItemId(itemId);
			cart.setNum(num);
			cartService.updateCartNum(cart);
			SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "更新商品数量失败");
	}
	
	@RequestMapping("/delete/{itemId}")
	public String delete(@PathVariable Long itemId) {
		//User user = UserThreadLocal.get();
		Long userId = user.getId();
		Cart cart = new Cart();
		cart.setItemId(itemId);
		cart.setUserId(userId);
		cartService.deleteCart(cart);
		return "redirect:/cart/show.html";
	}
}
