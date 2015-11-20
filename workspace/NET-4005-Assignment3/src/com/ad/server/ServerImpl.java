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
import javax.jws.WebService;

@WebService(endpointInterface = "com.ad.server.ServerInt")

public class ServerImpl implements ServerInt {

	@Override
	//below is the implementation of the underlying methods for each service.
	
	
	/*using an array of strings, determine 
	 * what type of service is requested
	 * Positions of array: 
	 * 0 = service type/name (A or B)
	 * 1 = server number (1 or 2, set in the Gateway, remains unchanged)
	 * 2 = A string that says 'Service A' or 'Service B'. This is basically a dummy task for the service.
	 */
	public String[] services(String []stats) {
		
		if (stats[0] == "a") {
			
			stats[2] = "Service A";
			return stats;
		
		} else if (stats[0] == "b") 
			
			stats [2] = "Service B";
			return stats;
	}
}
