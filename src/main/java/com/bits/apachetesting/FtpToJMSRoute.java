package com.bits.apachetesting;

import org.apache.camel.spring.SpringRouteBuilder;


/**
 * This route grabs a file from an FTP server and sends it to a 
 * JMS component. In out case, the JMS component is an ActiveMQ 
 * message broker (queue). Use this if you want to define routes
 * with the Java DSL. If routes are being defined in the spring,
 * then this class may not be needed.
 * @author kbazagonza
 *
 */
public class FtpToJMSRoute extends SpringRouteBuilder {

	/**
	 * 
	 */
	@Override
	public void configure() throws Exception {
		// Make Routes here.
		from("ftp://rider.com/orders?username=rider&amp;password=secret")
		.to("jms.incomingOrders");
		
	}

}
