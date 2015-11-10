package com.ad.endpoint;

import javax.xml.ws.Endpoint;
import com.ad.ws.ServiceImpl;

public class ServicePublisher {

	
	public static void main(String[] args) {
	   Endpoint.publish("http://localhost:9999/ws/hello", new ServiceImpl()); //define url for the publisher
    }
	
}
