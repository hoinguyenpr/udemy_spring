package com.truongdd.warehouse.routeBuilder;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;

@Configuration
public class AWSRouteConfig {
	@Bean(name = "sqsClient")
	public AmazonSQS amazonSQSClient() {
		return AmazonSQSClientBuilder.defaultClient(); 
	}
}
