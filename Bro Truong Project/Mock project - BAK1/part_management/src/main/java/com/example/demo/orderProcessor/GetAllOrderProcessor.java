package com.example.demo.orderProcessor;

import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.entity.ItemOrder;
import com.example.demo.partService.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class GetAllOrderProcessor implements Processor{
	
	@Autowired
	OrderService orderService; 

	@Override
	public void process(Exchange exchange) throws Exception {
		ObjectMapper mapper = new ObjectMapper();

		Message outMessage = exchange.getIn() ;
		
		List<ItemOrder> allOrderList = orderService.getAllOrders();
		String allOrderListAsString = mapper.writeValueAsString(allOrderList);
		outMessage.setBody(allOrderListAsString);
		exchange.setMessage(outMessage);
	}

}
