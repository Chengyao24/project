package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.OrderDetail;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {

	@Autowired
	private OrderDetailRepository or;

	@Override
	public List<OrderDetail> getAllOrderDetail() {

		List<OrderDetail> xx = new ArrayList<OrderDetail>();
 		List<OrderDetail> data = or.findAll();

		for (OrderDetail item : data) {

			String SS = item.getPorder().getStatus();

			if (SS != null) {
				if (!SS.equals("已完成") &&!SS.equals("製作中")) {
						xx.add(item);
				}
			} else {
				xx.add(item);
			}

		}

		return xx;
	}

	@Override
	public void saveOrderDetail(OrderDetail orderDetail) {
		this.or.save(orderDetail);

	}

	@Override
	public OrderDetail getOrderDetailById(Long orderId) {
		Optional<OrderDetail> optional = or.findById(orderId);
		OrderDetail OrderDetail = null;
		if (optional.isPresent()) {
			OrderDetail = optional.get();
		} else {
			throw new RuntimeException("OrderDetail not found for id :: " + orderId);
		}
		return OrderDetail;
	}

	@Override
	public void deleteOrderDetailById(Long orderId) {
		this.or.deleteById(orderId);
	}

}
