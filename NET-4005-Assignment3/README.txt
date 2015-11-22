Anuj Dalal (100893744)
NET 4005 Assignment 3
Date: November 16th, 2015


Instructions for running the application: 

This application contails 2 clients, one API Gateway, and 4 servers.


1-Extract the .zip folder to your eclipse workspace
2-There should now be a folder called A3 in your workspace
3-Create a new java project in eclipse, using the same workspace. The name of the project should be exactly the same as the above folder (A3).
4-Click Finish. You should now have access to all of the packages and source files as they were at development. 
5-Compile and run 'ServerPublisher'
6-Compile and run 'GatewayPublisher'
7-Compile and run 'ServiceClient' and/or 'ServiceClient2'
8-Follow the instructions in the eclipse console.


In case the above method does not work, I have included the .java files, and the corresponding .class file in the src and bin folders respectively.

Files:

GatewayInt:
	Interface for the API Gateway.

Gateway Impl:
	Implementation for the API Gateway.

GatwayPublisher:
	Front end file for the gateway, for the client. 

ServerInt:
	Interface for the server.

ServerImpl:
	Implementation of the server.

ServerPublisher:
	Front end file for the 'Client" (Gateway).

ServiceClient and ServiceClient2:
	Identical cleint files that simulate 2 clients.


Services: 

	The services are implemented in ServerImpl. They are quite simple. The only task they perform is appending 'Service A' or 'Service B' to an array. Output of one of theses strings per client is considered a success.


You will encouter an error while running the client, although you will reach the server and back, there is a problem printing out the final value for each service. I therefore urge you to mark the assignment in accordance with the code logic. 


Collaborators: 

Marc Charlebois (see GatewayImpl for details)
Itai Marongwe



No code was directly exchanged for this assignment