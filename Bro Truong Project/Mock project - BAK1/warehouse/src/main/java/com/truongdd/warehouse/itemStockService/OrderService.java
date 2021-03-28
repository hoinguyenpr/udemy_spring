package com.truongdd.warehouse.itemStockService;

import java.util.List;
import java.util.Optional;

import com.truongdd.warehouse.entity.ItemOrder;


public interface OrderService {

	public boolean saveOrder(ItemOrder order);
	
	public Optional<ItemOrder> getOrderById(Integer orderId);
	
	public List<ItemOrder> getAllOrders();
	
}
