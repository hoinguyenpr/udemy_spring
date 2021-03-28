package com.example.demo.partServiceImp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.entity.ItemOrder;
import com.example.demo.entity.OrderDetail;
import com.example.demo.partService.OrderService;
import com.example.demo.partServiceDAO.OrderDetailDAO;
import com.example.demo.partServiceDAO.OrderServiceDAO;
import com.example.demo.partServiceDAO.PartServiceDAO;

@Component
public class OrderServiceImp implements OrderService {

	@Autowired
	private OrderServiceDAO orderServiceDao;
	
	@Autowired
	private PartServiceDAO partServiceDao;
	
	@Autowired
	private OrderDetailDAO orderDetailDao;
	
	@Override
	public boolean saveOrder(ItemOrder order) {
		
		try {
			List<OrderDetail> orderDetailList = order.getContents();
			order.setStatus("NEW");
			order.setTime(LocalDateTime.now());
			orderServiceDao.flush();
			orderDetailDao.flush();
			for (OrderDetail orderDetail : orderDetailList) {
				orderDetail.setDeliveredQty(0);
				orderDetail.setPartDesc(partServiceDao.findByPartNumber(orderDetail.getPartNumber()).getPartDesc());
			}
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
