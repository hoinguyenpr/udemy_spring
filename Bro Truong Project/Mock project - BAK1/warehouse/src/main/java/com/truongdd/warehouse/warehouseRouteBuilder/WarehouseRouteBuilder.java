package com.truongdd.warehouse.warehouseRouteBuilder;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.truongdd.warehouse.warehouseProcessor.OrderCompletion;

@Component
public class WarehouseRouteBuilder extends RouteBuilder {
	@Autowired
	OrderCompletion orderCompletion;

	@Override
	public void configure() throws Exception {
		
		//Receive and inform about new order
		from("aws-sqs://newOrder.fifo?messageGroupIdStrategy=useExchangeId")
		.log("Recieved new order request: + (${body})");

		rest("/orderItem")
			.post("/completed/{id}")
				.route().to("direct:orderCompletion")
			.endRest();
		
		from("direct:orderCompletion")
		.setHeader("id", simple("${id}"))
		.process(orderCompletion)
		.log("Order ID ${body} is completed")
		.to("aws-sqs://orderCompleted.fifo?messageGroupIdStrategy=useExchangeId");
	}
}
