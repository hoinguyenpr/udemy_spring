package com.example.demo.routeBuilder;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.orderProcessor.GetAllOrderProcessor;
import com.example.demo.partService.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ProducerRouteBuilder extends RouteBuilder{
	@Autowired
	ObjectMapper objectMapper;
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	AddOrderProcessor addOrderProcessor;
	
	@Autowired
	GetAllOrderProcessor getAllOrder;
	
	CamelContext camel = new DefaultCamelContext();
	@Override
	public void configure() throws Exception {
		
		rest("/itemOrder")
			.post("/add")
				.route()
				.to("direct:addNewOrder")
			.endRest()
			.get("/allOrder")
				.route()
				.to("direct:allOrder")
			.endRest();
			
		
		
		from("direct:addNewOrder")
		.process(addOrderProcessor)
		.log("Sending new order request: + (${body})")
		.to("aws-sqs://newOrder.fifo?messageGroupIdStrategy=useExchangeId");
		
		from("direct:allOrder")
		.process(getAllOrder)
		.log("All orders: ${body}")
		.to("aws-sqs://newOrder.fifo?messageGroupIdStrategy=useExchangeId");
		
		from("aws-sqs://orderCompleted.fifo?messageGroupIdStrategy=useExchangeId")
		.log("Received new message: ${body}");
		
	}
	
	
	
}
