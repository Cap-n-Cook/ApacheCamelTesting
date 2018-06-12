package com.bits.apachetesting;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 * Basic processor just prints the file we received.
 * @author kbazagonza
 *
 */
public class ReceivingProcessor implements Processor {

	public void process(Exchange exchange) throws Exception {
		System.out.println("Received the file: " + exchange.getIn().getHeader("CamelFileName"));
		
	}

}
