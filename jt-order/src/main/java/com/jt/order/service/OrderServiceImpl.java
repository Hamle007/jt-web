package com.jt.order.service;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jt.order.mapper.OrderItemMapper;
import com.jt.order.mapper.OrderMapper;
import com.jt.order.mapper.OrderShippingMapper;
import com.jt.order.pojo.Order;
import com.jt.order.pojo.OrderItem;
import com.jt.order.pojo.OrderShipping;

@Service
public class OrderServiceImpl implements OrderService{
	
	@Autowired
	private OrderMapper orderMapper;
	
	@Autowired
	private OrderItemMapper orderItemMapper;
	
	@Autowired
	private OrderShippingMapper orderShippingMapper;

	@Override
	public String saveOrder(Order order) {
		Date date = new Date();
		String orderId = order.getUserId() + "" + System.currentTimeMillis();
		// status 1.未付款 2.已付款 3.未发货 4.已发货 5.交易完成
		order.setStatus(1);
		order.setCreated(date);
		order.setOrderId(orderId);
		order.setUpdated(date);
		orderMapper.insert(order);
		System.out.println("order已入库");
		OrderShipping orderShipping = order.getOrderShipping();
		orderShipping.setOrderId(orderId);
		orderShipping.setCreated(date);
		orderShipping.setUpdated(date);
		orderShippingMapper.insert(orderShipping);
		System.out.println("orderShipping已入库");
		List<OrderItem> orderItems = order.getOrderItems();
		for (OrderItem orderItem : orderItems) {
			orderItem.setOrderId(orderId);
			orderItem.setCreated(date);
			orderItem.setUpdated(date);
			orderItemMapper.insert(orderItem);
		}
		System.out.println("订单商品入库");
		System.out.println("order入库完成");
		return orderId;
	}

	@Override
	public Order findOrderById(String id) {
		return orderMapper.findOrderById(id);
	}

}
