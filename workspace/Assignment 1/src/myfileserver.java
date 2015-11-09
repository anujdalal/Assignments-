
/* Part of this code is Copyright (c) 1995, 2014, Oracle and/or its affiliates. All rights reserved.
 * 
 */

import java.net.*;
import java.io.*;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.*;

public class myfileserver {

	public static void main(String[] args) throws IOException {
		 
		 int soc = 50485;
		 ServerSocket serv;
		 Socket client;
		 
		 
		 while(true){
			 
			//open server socket
			try{
				System.out.println("Opening port "+soc+".");
				serv = new ServerSocket(soc);
			} catch(Exception e){System.out.println("Error 101 = failed to bind to port "+soc+"."); 
				break;}
		    //accept an incoming client connection
			try{
				
			}
		 }
	 }
}

class multiThreadServer implements Runnable {

	protected int sendRec = 50485; // define a permanent "well known" port for
									// the server
	protected ServerSocket socket;
	protected boolean stopped = false;
	private int maxCorePoolSize = 10;
	private int maxPoolSize = 150;
	private long timeout = 5;
	private ExecutorService pool = new ThreadPoolExecutor(maxCorePoolSize, maxPoolSize, timeout, TimeUnit.SECONDS,
			new LinkedBlockingQueue<Runnable>());

	public multiThreadServer(int port) {
		this.sendRec = port;
	}

	public void run() {

		openSocket();

	}

	public synchronized void stop() {
		this.stopped = true;
		try {
			this.socket.close();
		} catch (IOException e) {
			throw new RuntimeException("ERROR: Cannot close socket! Kill Maunally.", e);
		}
	}

	private void openSocket() { // open the socket
		try {
			this.socket = new ServerSocket(this.sendRec);
		} catch (IOException e) {
			throw new RuntimeException("ERROR: Unable to open Port!", e);
		}
	}

}

class ClientWorkerThread extends Thread {

}

class serverStatistics {

	public int totalReq = 0; // n initialize number total number of requests
	public int goodReq = 0; // m initialize total number of SUCCESSFUL requests

	public synchronized int getTotalReq() {

		return totalReq;
	}

	public synchronized int getGoodReq() {

		return goodReq;
	}

	public synchronized int countTotalReq() {

		return totalReq++;
	}

	public synchronized int countGoodReq() {

		return goodReq++;
	}
}
