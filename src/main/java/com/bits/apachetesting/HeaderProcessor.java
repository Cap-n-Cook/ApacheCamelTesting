package com.bits.apachetesting;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;


/**
 * Takes the messages containing data from the FTP server and
 * modifies them so that they can be allowed to pass through 
 * a firewall.
 * @author kbazagonza
 *
 */
public class HeaderProcessor implements Processor {

	/**
	 * Modifies the header of a message so that it can pass through the firewall.
	 */
	public void process(Exchange exchange) throws Exception {
		System.out.println("I have modified the header of the message: " + exchange.getIn().getHeader("CamelFileName"));
/*		String oldBody = exchange.getIn().getBody(String.class);
		System.out.println(oldBody);
		exchange.getIn().setHeader("LetMeIn", oldBody);
		System.out.println(exchange.getProperties());*/
		
	}

}
