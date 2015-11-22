/* 
 * NET 4005A Assignment 3
 * Date: Nov, 16th, 2015
 * Author: Anuj Dalal (100893744)
 * This assignment was created using the examples provided to the class in the assignment description: 
 * http://www.mkyong.com/webservices/jax-ws/jax-ws-hello-world-example/
 * In partial collaboration with: Marc Charlebois, Itai Marongwe
 * No code was directly exchanged for this assignment
 */

package com.ad.api;
import javax.xml.ws.Endpoint;
import com.ad.api.GatewayImpl;;

public class GatewayPublisher {
	
	public static void main(String[] args) {
		   Endpoint.publish("http://localhost:1115/ws/service", new GatewayImpl()); //the API Gateway will listen on port 1115
		   System.out.println("API Gateway listening on 1115");
	    }
}
	

