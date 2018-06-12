package com.bits.apachetesting;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class FtpToJMSExample {

	public static void main(String[] args) {
		
		// Spring Runtime instance.
		/*ApplicationContext mySpringContext = 
		/*		new ClassPathXmlApplicationContext(new String[] {"spring.xml"});
		
		CamelContext myCamelContext = (CamelContext) mySpringContext.getBean("camel-1");
		*/
		
		ConnectionFactory connFac = new ActiveMQConnectionFactory("vm://localhost");
		
		CamelContext myCamelContext = new DefaultCamelContext();
		myCamelContext.addComponent("jms", JmsComponent.jmsComponentAutoAcknowledge(connFac));
		try {
			myCamelContext.addRoutes(new RouteBuilder() {
				public void configure() {
					// Basic Route to the JMS component.
					from("file:src/data?noop=true").to("jms:incomingOrders");
					
					// Content Based Router.
					// THis router is a little weak though. It drops orders
					// if they use the wron csv extension. 
					/*from("jmd:incomingOrders")
					.choice()
						.when(header("CamelFileName")
						.endsWith(".xml"))
							.to("jms:xmlOrders")
						.when(header("CamelFileName")
						.endsWith(".csv"))
							.to("jms:csvOrders");
					*/
					
					// This router uses regex.
					// Also handles bad orders.
/*					from("jms:incomingOrders")
					.choice()
						.when(header("CamelFileName")
						.endsWith(".xml"))
							.to("jms:xmlOrders")
						.when(header("CamelFileName")
						.regex("^.*(csv | csl)$"))	// regex to accept files with csl or csv extension.
							.to("jms:csvOrders")
						.otherwise()
							.to("jms:badOrders");*/
					
					// This next part is considered a continuation of the route that ended at the 
					// CBR.
					// Add processors that prints out what type of file we received.
					// Xml processor
					// the from method return a RouteDefinition object, which has a process method
					// whihc has a processor of a parameter. this processor is an interface which needs
					// to be implemented., which is why we have the anon class. the process method of the
					//processor interface takes in an excehange because that is what is created by the consumer.
					
/*					from("jms:xmlOrders").process(new Processor() {
						public void process(Exchange exchange) throws Exception {
							System.out.println("XML got Got: " + exchange.getIn().getHeader("CamelFileName"));
						}
					});*/
/*					// Csv processor.
					from("jms:csvOrders").process(new Processor() {
						public void process(Exchange exchange) throws Exception {
							System.out.println("CSV got Got: " + exchange.getIn().getHeader("CamelFileName"));
						}
					});*/
					
					// This route is ended by closing the choice() via the end() method.
					// This require a to() to continue routing.
					
					from("jms:incomingOrders")
					.choice()
						.when(header("CamelFileName")
						.endsWith(".xml"))
							.to("jms:xmlOrders", "file:src/test")
						.when(header("CamelFileName")
						.regex("^.*(csv | csl)$"))	// regex to accept files with csl or csv extension.
							.to("jms:csvOrders")
						.otherwise()
							.to("jms:badOrders")				
							.stop()					// prevents bad orders from propagating to the next endpoint
					.end()
					.to("jms:continuedProcessing");	// messages come here after being sent to their correct endpoints too.
									
					// Testing processors.
					/*from("jms:xmlOrders").process(new Processor() {
						public void process(Exchange exchange) throws Exception {
							System.out.println("XML got Got: " + exchange.getIn().getHeader("CamelFileName"));
						}
					})
					.to("file:src/test/");
					*/
					// This filter checks to see that the XML order has a "test" element.
					// If it does, then it does not allow the message through.
					// Only allow the messages that have a false value for the test element.
					// /order is a message in the jms queue.
					from("jms:xmlOrders").filter(xpath("/order[(@test='False')]"))
					.process(new Processor() {
						public void process(Exchange exchange) throws Exception {
							System.out.println("Filtering out test messages.");
						}
					})
					.to("file:src/realOrders");
					
					
					
					/*from("jms:xmlOrders")
					.to("file:src/output/xmlOrders.txt");*/
					
					// By default parallel processing uses 10 thread pools.
					// By default multicast continues to send messages even if one of them failed.
					// To fix this, you can use the stopOnException feature of multicast.
					
					from("jms:continuedProcessing")
					.multicast().stopOnException().parallelProcessing()
					.to("file:src/output/continuedProcessing/TheFirstDB", 
							"file:src/output/continuedProcessing/TheSecondDB");
					
					/*
					 * Example of changing the underlying thread pool for parallel processing.*/
					ExecutorService executor = Executors.newFixedThreadPool(16);
					
					from("jms:continuedProcessing")
					.multicast().parallelProcessing().executorService(executor)
					.to("file:src/output/continuedProcessing/TheFirstDB", 
							"file:src/output/continuedProcessing/TheSecondDB");
					
				}
			});
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		// Run the context.
		try {
			myCamelContext.start();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			Thread.sleep(10000);
		} catch(InterruptedException e) {
			System.out.println(e);
		}
		System.out.println("Completed :)");
		
		try {
			myCamelContext.stop();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
