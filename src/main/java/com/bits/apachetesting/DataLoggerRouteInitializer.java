package com.bits.apachetesting;

import org.apache.camel.CamelContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DataLoggerRouteInitializer {

	// Spring context runtime instance.
	private ApplicationContext springContext;
	// Camel context runtime instance.
	private	CamelContext camelContext;
	
	/**
	 * Initializes bean context and camel context. 
	 */
	public DataLoggerRouteInitializer(String springXmlFile, String camelContextBean){
		// Initialize spring context.
		springContext = new ClassPathXmlApplicationContext(new String[] {springXmlFile});
		// Initialize camel context.
		camelContext = (CamelContext) springContext.getBean(camelContextBean);
	}
	
	/**
	 * Starts the camel context. It will send data in the 
	 * FTP server to the ESPN Queue. 
	 * @throws Exception 
	 */
	public void start() throws Exception {
		camelContext.start(); 
		System.out.println("started routing");
	}
	
	/**
	 * Ends the camel context.
	 * @throws Exception 
	 */
	public void stop() throws Exception {
		System.out.println("stopped routing");
		camelContext.stop();
	}
	
	
	
	

	
}
