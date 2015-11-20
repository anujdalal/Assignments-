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

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
	 

@WebService
@SOAPBinding(style = Style.RPC)
public interface ServerInt {
	 
	
		//define the service methods that will be used by each set of servers
		@WebMethod String[] services(String[]r);
		
		
	
}
