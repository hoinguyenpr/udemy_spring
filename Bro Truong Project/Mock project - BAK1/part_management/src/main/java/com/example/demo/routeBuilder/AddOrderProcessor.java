package com.example.demo.routeBuilder;

import java.time.LocalDateTime;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.entity.ItemOrder;
import com.example.demo.partService.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class AddOrderProcessor implements Processor {

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	OrderService orderService;

	@Override
	public void process(Exchange exchange) throws Exception {
		Message in = exchange.getIn();
		Message out = in.copy();
		
		System.out.println("ABC camel");
		String body = out.getBody(String.class);
		System.out.println(body);
		ItemOrder newOrder = objectMapper.readValue(body, ItemOrder.class);
		LocalDateTime orderTime = LocalDateTime.now();
		newOrder.setTime(orderTime);
		System.out.println(newOrder.toString());
		newOrder.getContents().forEach(orderDetail -> orderDetail.setParentOrder(newOrder));
		orderService.saveOrder(newOrder);
		out.setBody(objectMapper.writeValueAsString(newOrder));
		exchange.setMessage(out);
	}
}
