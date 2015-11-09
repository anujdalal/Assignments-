package com.ad.ws;

import javax.jws.WebService;


//Service Implementation
@WebService(endpointInterface = "com.ad.ws.ServiceInt")
public class ServiceImpl implements ServiceInt{

	@Override
	public String getHelloWorldAsString(String name) {
		return "Hello World JAX-WS " + name;
	}

}
