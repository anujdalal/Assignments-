package com.ad.client;

import com.ad.ws.ServiceInt;
import com.ad.ws.ServiceImplService;

public class ServiceClient {

	public static void main(String[] args) {
		   
		ServiceImplService Service = new ServiceImplService();
		ServiceInt hello = Service.getServiceImplPort();
	
		System.out.println(hello.getHelloWorldAsString("Anuj Dalal"));
		
    }
	
}
