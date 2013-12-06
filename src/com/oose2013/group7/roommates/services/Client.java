package com.oose2013.group7.roommates.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	// TODO re-implement the methods using GSON lib 

	Socket sessionSocket;
	BufferedReader reader;
	PrintWriter writer;
	
	//public static void main(String[] args) throws UnknownHostException, IOException {
	
		//Client c = new Client(); // send connect request to a Server
	//}

	public void setNetworking() throws IOException{
		
		InputStreamReader streamReader = new InputStreamReader(sessionSocket.getInputStream());
		reader = new BufferedReader(streamReader);
		writer = new PrintWriter(sessionSocket.getOutputStream());
		System.out.println("Networking Established");
	}
	public Client() throws UnknownHostException, IOException{
		sessionSocket= new Socket("127.0.0.1",4444);
	}
	
	public void sendText(String text) throws IOException{
		PrintWriter writer = new PrintWriter(sessionSocket.getOutputStream());
		writer.println(text);
	}
	public void receiveText() throws  IOException{
		
		InputStreamReader inputStream = new InputStreamReader(sessionSocket.getInputStream());
		BufferedReader reader = new BufferedReader(inputStream);
		String message = reader.readLine();
		
	}

}
