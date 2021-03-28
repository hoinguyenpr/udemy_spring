package com.example.demo.config;

import org.apache.camel.component.servlet.CamelHttpTransportServlet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;

@Configuration
public class ProducerConfig {

	@Bean(name = "sqsClient")
	public AmazonSQS amazonSQSClient() {
		return AmazonSQSClientBuilder.defaultClient(); 
	}
	
//	@Value("${truongdd.api.path}")
//	String contextPath;
//	
//	@Bean
//	ServletRegistrationBean servletRegistrationBean() {
//	    ServletRegistrationBean servlet = new ServletRegistrationBean
//	      (new CamelHttpTransportServlet(), contextPath);
//	    servlet.setName("CamelServlet");
//	    return servlet;
//	}

}
