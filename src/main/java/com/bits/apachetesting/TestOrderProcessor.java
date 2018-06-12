package com.bits.apachetesting;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 * Prints out if we received a test message.
 * @author kbazagonza
 *
 */
public class TestOrderProcessor implements Processor {

	public void process(Exchange exchange) throws Exception {
		System.out.println("Received a real order: " + exchange.getIn().getHeader("CamelFileName"));
		
	}

}
