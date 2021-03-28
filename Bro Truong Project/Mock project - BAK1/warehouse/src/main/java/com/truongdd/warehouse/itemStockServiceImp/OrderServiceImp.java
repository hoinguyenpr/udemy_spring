package com.truongdd.warehouse.itemStockServiceImp;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.truongdd.warehouse.entity.ItemOrder;
import com.truongdd.warehouse.itemStockDao.OrderServiceDAO;
import com.truongdd.warehouse.itemStockService.OrderService;



@Component
public class OrderServiceImp implements OrderService {

	@Autowired
	private OrderServiceDAO orderServiceDao;
	
	@Override
	public boolean saveOrder(ItemOrder order) {
		
		try {
			orderServiceDao.save(order);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Optional<ItemOrder> getOrderById(Integer orderId) {
		Optional<ItemOrder> requestedOrder = null;
		try {
				requestedOrder = orderServiceDao.findById(orderId);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return requestedOrder;
	}

	@Override
	public List<ItemOrder> getAllOrders() {
		List<ItemOrder> ordersList = new ArrayList<ItemOrder>();
		try {
			ordersList = orderServiceDao.findAll();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return ordersList;
	}
}
