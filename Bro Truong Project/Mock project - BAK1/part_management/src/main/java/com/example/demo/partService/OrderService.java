package com.example.demo.partService;

import java.util.List;
import java.util.Optional;

import com.example.demo.entity.ItemOrder;

public interface OrderService {

	public boolean saveOrder(ItemOrder order);
	
	public Optional<ItemOrder> getOrderById(Integer orderId);
	
	public List<ItemOrder> getAllOrders();
	
}
