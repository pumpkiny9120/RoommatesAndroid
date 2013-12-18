package com.oose2013.group7.roommates.services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

import com.google.gson.Gson;
import com.oose2013.group7.roommates.common.interfaces.Command;
import com.oose2013.group7.roommates.common.interfaces.Event;
import com.oose2013.group7.roommates.common.interfaces.EventListener;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

public class NetworkServices extends Activity {
	
	private static NetworkServices networkServices = null;

	private static final String APP_TAG = "Roommates";
	private static final String ACT_TAG = "NetworkServices: ";
	
	private String serverMessage;
	private Event event;
    public static final String SERVERIP = "10.164.22.29"; //your computer IP address
    public static final int SERVERPORT = 4456;
    private Socket socket;
    private ArrayList<EventListener<?>> listeners = null;
    private boolean mRun = false;
 
    PrintWriter out;
    BufferedReader in;
    
	public static NetworkServices getNetworkServices() {
		if (networkServices == null) {
			networkServices = new NetworkServices();
		}
		return networkServices;
	}

	@Override
    protected void onCreate(Bundle savedInstanceState) {
		
	}
	
    public static boolean isInternetOn(Context context) {
        Log.d(APP_TAG, ACT_TAG + "Checking internet connectivity...");
        ConnectivityManager con = null;
        con =  (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if ( con != null){
            Log.d(APP_TAG, ACT_TAG + "Internet available.");
            NetworkInfo result = con.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (result != null && result.isConnectedOrConnecting());
            return true;
        }
        Log.d(APP_TAG, ACT_TAG + "Internet NOT available.");
        return false;
    }
    
    /**
     *  Add listeners for the socket
     */
    public void addListener (EventListener<?> listener) {
    	listeners.add(listener);
    }
    
    /**
     *  Remove listeners for the socket
     */
    public void removeListener (EventListener<?> listener) {
    	listeners.remove(listener);
    }
    
	public <T extends Event> void fireListeners (T event) throws IOException {
    	for (Iterator<EventListener<?>> it = listeners.iterator(); it.hasNext(); ) {
    		EventListener<T> listener = (EventListener<T>) it.next();
		    listener.eventReceived(event);
		}
    }
    
    /**
     * Sends the message entered by client to the server
     * @param message text entered by client
     */
    public void sendMessage(Command command){
    	String message = createJson(command);
        if (out != null && !out.checkError()) {
            out.println(message);
            out.flush();
        }
    }
 
	/*** Serializes an object to be sent **/
	public String createJson(Object objectToSend) {
		Gson gson = new Gson();
		String json = gson.toJson(objectToSend);
		return json;

	}

	/*** Deserializes an object **/
	public Event getObjectFromJson(String json) {
		Gson gson = new Gson();
		Event eventObject = gson.fromJson(json, Event.class);
		return eventObject;
	}
	
	public void setConnected() {
		mRun = true;
	}
	
    public void stopClient(){
    	try {
			socket.close();
			mRun = false;
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
 
    public boolean isConnected() {
    	return mRun;
    }
    
    public void connect() {
    	new Connect().execute(networkServices);
    }
    
    public void startClient() { 
        try {
            InetAddress serverAddr = InetAddress.getByName(SERVERIP);
            Log.e("TCP Client", "C: Connecting...");
            Socket socket = new Socket(serverAddr, SERVERPORT);
            mRun = true;
            try {
                out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
 
                //in this while the client listens for the messages sent by the server
                while (mRun) {
                    serverMessage = in.readLine();
                    if (serverMessage != null && listeners != null) {
                        event = getObjectFromJson(serverMessage);
                    	fireListeners(event);
                    }
                    serverMessage = null;
                }
                Log.e("RESPONSE FROM SERVER", "S: Received Message: '" + serverMessage + "'");
            } catch (Exception e) {
                Log.e("TCP", "S: Error", e);
            } finally {
                //the socket must be closed. It is not possible to reconnect to this socket
                // after it is closed, which means a new socket instance has to be created.
                socket.close();
            }
        } catch (Exception e) {
            Log.e("TCP", "C: Error", e);
        }
    } 
    
	public class Connect extends AsyncTask<NetworkServices, Void, NetworkServices> {
		@Override
		protected NetworkServices doInBackground(NetworkServices... ns) {
			ns[0].startClient();
			return null;
		}
	}
}    
