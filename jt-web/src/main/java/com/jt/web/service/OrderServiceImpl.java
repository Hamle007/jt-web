package com.jt.web.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.HttpClientService;
import com.jt.common.vo.SysResult;
import com.jt.web.pojo.Cart;
import com.jt.web.pojo.Order;
import com.jt.web.pojo.OrderItem;
import com.jt.web.pojo.OrderShipping;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private HttpClientService httpClient;
	
	private static ObjectMapper objectMapper = new ObjectMapper();
	
	@Override
	public String saveOrder(Order order) {
		String uri = "http://order.jt.com/service/order/submit";
		Map<String,String> params = new HashMap<>();
		String orderId = null;
		try {
			String orderJSON = objectMapper.writeValueAsString(order);
			params.put("orderJSON", orderJSON);
			String sysResultJSON = httpClient.doPost(uri, params);
			SysResult sysResult = objectMapper.readValue(sysResultJSON, SysResult.class);
			orderId = (String)sysResult.getData();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return orderId;
	}

	@Override
	public Order findOrderById(String id) {
		String uri = "http://order.jt.com/order/success/"+id;
		Order order = null;
		try {
			String orderJSON = httpClient.doGet(uri);
			order = objectMapper.readValue(orderJSON, Order.class);
			System.out.println(order);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return order;
	}

}
