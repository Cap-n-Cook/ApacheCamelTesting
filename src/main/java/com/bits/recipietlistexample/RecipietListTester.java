package com.bits.recipietlistexample;

import org.apache.camel.CamelContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class RecipietListTester {

	public static void main(String[] args) {
		
		// Spring runtime instance.
		ApplicationContext springContext = 
				new ClassPathXmlApplicationContext(new String[] {"recipient-list-spring.xml"});
		
		// Camel runtime instance
		CamelContext camelContext = (CamelContext) springContext.getBean("camel-1");
		
		

	}

}
