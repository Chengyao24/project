package com.example.demo.service;

import java.util.List;

import com.example.demo.model.OrderDetail;

public interface OrderDetailService {
	List<OrderDetail> getAllOrderDetail();

	void saveOrderDetail(OrderDetail orderDetail);

	OrderDetail getOrderDetailById(Long orderId); // 藉由訂單id取得訂單明細

	void deleteOrderDetailById(Long orderId);
	
}
