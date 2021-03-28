package com.example.demo.apiController;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.ItemOrder;
import com.example.demo.partService.OrderService;

@RestController
@RequestMapping("/itemOrder")
public class ApiOrderController {
	@Autowired
	private OrderService orderService;
	
	@GetMapping("/show/{orderId}")
	public Optional<ItemOrder> getOrder(@PathVariable Integer orderId) {
		Optional<ItemOrder> itemOrder = null;
		itemOrder = orderService.getOrderById(orderId);
		return itemOrder;
	}
	
	@PostMapping("/add")
	public String addOrder(@RequestBody ItemOrder itemOrder) {
		LocalDateTime orderTime = LocalDateTime.now();
		itemOrder.setTime(orderTime);
		System.out.println(itemOrder.toString());
		itemOrder.getContents().forEach(orderDetail -> orderDetail.setParentOrder(itemOrder));
		orderService.saveOrder(itemOrder);
		return "order added success";
	}
	
}
