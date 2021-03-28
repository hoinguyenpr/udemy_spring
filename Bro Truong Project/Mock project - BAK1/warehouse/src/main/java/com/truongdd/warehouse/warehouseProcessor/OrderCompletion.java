package com.truongdd.warehouse.warehouseProcessor;

import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.truongdd.warehouse.entity.ItemOrder;
import com.truongdd.warehouse.entity.OrderDetail;
import com.truongdd.warehouse.itemStockService.ItemStockService;
import com.truongdd.warehouse.itemStockService.OrderService;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class OrderCompletion implements Processor {
	@Autowired
	OrderService orderService;
	
	@Autowired
	ItemStockService itemStockService;
	
	ObjectMapper mapper = new ObjectMapper();

	@Override
	public void process(Exchange exchange) throws Exception {
		Message inMessage = exchange.getIn();
		Message outMessage = inMessage.copy();
		Integer orderId = outMessage.getHeader("id", Integer.class);
		try {
			ItemOrder currentOrder = orderService.getOrderById(orderId).get();
			if (currentOrder.getStatus().equalsIgnoreCase("NEW")) {
				List<OrderDetail> orderDetailList = currentOrder.getContents();
				log.debug(orderDetailList);
				for (OrderDetail orderDetail : orderDetailList) {
					itemStockService.stockWithdraw(orderDetail);
				}
				currentOrder.setStatus("COMPLETED");
				orderService.saveOrder(currentOrder);
				
				outMessage.setBody("Order ID = " + orderId + " is completed!");
			}
			else {
				outMessage.setBody("Order ID = " + orderId + " is already completed!");
			}
		} catch (Exception e) {
			outMessage.setBody("Order ID = " + orderId + " is unavailable or invalid. Please check again!");
		}
		exchange.setMessage(outMessage);
	}

}
