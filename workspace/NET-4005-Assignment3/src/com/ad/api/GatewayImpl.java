/* 
 * NET 4005A Assignment 3
 * Date: Nov, 16th, 2015
 * Author: Anuj Dalal (100893744)
 * This assignment was created using the examples provided to the class in the assignment description: 
 * http://www.mkyong.com/webservices/jax-ws/jax-ws-hello-world-example/
 * In partial collaboration with: Marc Charlebois (lines 25-39: explained by Marc), Itai Marongwe
 * No code was directly exchanged for this assignment
 */
package com.ad.api;
import java.net.MalformedURLException;
import java.net.URL;

import javax.jws.WebService;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import com.ad.server.*;

@WebService(endpointInterface = "com.ad.api.GatewayInt", targetNamespace = "http://localhost:1115/ws/service" )

public class GatewayImpl implements GatewayInt {

	
	boolean busy = false;   //if a server for any service is busy
	public String srvBusy(){
		
		//if the first server is idle (there is no incoming request initially)
		if (busy == false){
			
			busy = true;
			return "x"; //the first server (1, or 3) will be used for the service 
			 
		//if the first server is busy
		} else if (busy == true) 
			
			busy = false;
			return "y"; //the second server (2, or 4) will be used for the service
	}
	
	/*
	 * access the services and respective servers as per clients
	 * request and server busy state
	 * @see com.ad.api.GatewayInt#accessServers()
	 */
	@Override
	public String accessServers(String input) throws MalformedURLException {
		
		String[] result = new String[3];
		String test = "";
		
		if (input == "a||A") {
			
			if (srvBusy().equals("x")){
				
				//redirect to server 1
				URL url = new URL("http://localhost:1111/ws/service?wsdl");
				QName qname = new QName("http://localhost:1111/ws/service", "ServerImplService");
				Service service = Service.create(url, qname);
				ServerInt srv = service.getPort(ServerInt.class);
				result [0] = "a"; //set service type to a
				result [1] = "1"; //set server number to 1
				srv.services(result);
				test = srv.services(result).toString();
				return test;
				
			} else if (srvBusy().equals("y")){
				
				//redirect to server 2
				URL url = new URL("http://localhost:1112/ws/service?wsdl");
				QName qname = new QName("http://localhost:1112/ws/service", "ServerImplService");
				Service service = Service.create(url, qname);
				ServerInt srv = service.getPort(ServerInt.class);
				result [0] = "a"; //set service type to a
				result [1] = "2"; //set server number to 2
				srv.services(result);
				test = srv.services(result).toString();
				return test;
			}
			
		} else if (input == "b||B") {
			
			if (srvBusy().equals("x")){
				
				//redirect to server 3
				URL url = new URL("http://localhost:1113ws/service?wsdl");
				QName qname = new QName("http://localhost:1113/ws/service", "ServerImplService");
				Service service = Service.create(url, qname);
				ServerInt srv = service.getPort(ServerInt.class);
				result [0] = "b"; //set service type to b
				result [1] = "3"; //set server number to 3
				srv.services(result);
				test = srv.services(result).toString();
				return test;
				
			} else if (srvBusy().equals("y")){
				
				//redirect to server 4
				URL url = new URL("http://localhost:1114/ws/service?wsdl");
				QName qname = new QName("http://localhost:1114/ws/service", "ServerImplService");
				Service service = Service.create(url, qname);
				ServerInt srv = service.getPort(ServerInt.class);
				result [0] = "b"; //set service type to b
				result [1] = "4"; //set server number to 4
				srv.services(result);
				test = srv.services(result).toString();
				return test;
				
			}
		}
		return test;
	}
}