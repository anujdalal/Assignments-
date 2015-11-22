/* 
 * NET 4005A Assignment 3
 * Date: Nov, 16th, 2015
 * Author: Anuj Dalal (100893744)
 * This assignment was created using the examples provided to the class in the assignment description: 
 * http://www.mkyong.com/webservices/jax-ws/jax-ws-hello-world-example/
 * In partial collaboration with: Marc Charlebois, Itai Marongwe
 * No code was directly exchanged for this assignment
 */

package com.ad.server;
import javax.xml.ws.Endpoint;
import com.ad.server.ServerImpl;;

public class ServerPublisher {
	
	public static void main(String[] args) {
			/*
			 * for testing purposes, all four servers will be implemented
			 * on the same host, using the same class files, but listening 
			 * on different ports.
			 */
		   Endpoint.publish("http://localhost:1111/ws/service", new ServerImpl()); //server 1
		   System.out.println("Server 1 listening on 1111");
		   
		   Endpoint.publish("http://localhost:1112/ws/service", new ServerImpl()); //server 2
		   System.out.println("Server 2 listening on 1112");
		   
		   Endpoint.publish("http://localhost:1113/ws/service", new ServerImpl()); //server 3
		   System.out.println("Server 3 listening on 1113");
		   
		   Endpoint.publish("http://localhost:1114/ws/service", new ServerImpl()); //server 4
		   System.out.println("Server 4 listening on 1114");
	    }
}
	