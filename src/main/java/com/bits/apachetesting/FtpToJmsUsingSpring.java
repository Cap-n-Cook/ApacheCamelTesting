package com.bits.apachetesting;

import org.apache.camel.CamelContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * An attempt to get camel to work using spring xml
 * instead of the java DSL.
 * @author kbazagonza
 *
 */
public class FtpToJmsUsingSpring {

	
	public static void main(String[] args) throws Exception {
/*		// Create the spring runtime instance.
		ApplicationContext mySpringContext = 
						new ClassPathXmlApplicationContext(new String[] {"camel-context-spring.xml"});
		
		
		
		// Create camel runtime instance via spring.
		CamelContext myCamelContext = (CamelContext) mySpringContext.getBean("camel-1");
		
		// Start camel
		myCamelContext.start();
		Thread.sleep(10000);
		myCamelContext.stop();
		System.out.println("Done");*/
		
		DataLoggerRouteInitializer routeCreator = 
				new DataLoggerRouteInitializer("camel-context-spring.xml", "camel-1");
		
		routeCreator.start();
		Thread.sleep(5000);
		routeCreator.stop();
		
		
	}
}
