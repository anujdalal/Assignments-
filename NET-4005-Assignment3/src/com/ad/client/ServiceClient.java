/* 
 * NET 4005A Assignment 3
 * Date: Nov, 16th, 2015
 * Author: Anuj Dalal (100893744)
 * This assignment was created using the examples provided to the class in the assignment description: 
 * http://www.mkyong.com/webservices/jax-ws/jax-ws-hello-world-example/
 * In partial collaboration with: Marc Charlebois, Itai Marongwe
 * No code was directly exchanged for this assignment
 */
package com.ad.client;

import java.net.URL;
import java.util.Scanner;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import com.ad.api.GatewayInt;

public class ServiceClient {

public static void main (String[] args) throws Exception {
		
		//define the URL for the wsdl file and the qualified name
		URL url = new URL("http://localhost:1115/ws/service?wsdl");
		QName qname = new QName("http://localhost:1115/ws/service", "GatewayImplService");
		Service service = Service.create(url, qname);
		GatewayInt srv = service.getPort(GatewayInt.class);
		

		Scanner reader = new Scanner(System.in);
		
		//ask user what service they want to run
		System.out.println("Welcome to an implementation of SOAP!\n"
				+ "\nWhich service would you like to test? Please Select an option:\n"
				+ "For Service A, type 'a' or 'A'\n"
				+ "OR\n"
				+ "For Service B, type 'b' or 'B'\n");
		
		//get input from user
		System.out.println("Enter a response: ");
		String n = reader.nextLine();	
		
		//call accessServers in GatewayInt
		srv.accessServers(n);
		System.out.println(srv.accessServers(n));
	}	
	
}
