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
import com.google.gson.GsonBuilder;
import com.oose2013.group7.roommates.common.commands.InterfaceAdapter;
import com.oose2013.group7.roommates.common.commands.SignInCommand;
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

/**
 * Handle all the communications between the client and
 * the server.
 * Connect to the server, send/receive messages, convert 
 * messages to/from json strings. 
 */
public class NetworkServices extends Activity {
	
	/** The network services. */
	private static NetworkServices networkServices = null;

	/** The Constant APP_TAG. */
	private static final String APP_TAG = "Roommates";
	
	/** The Constant ACT_TAG. */
	private static final String ACT_TAG = "NetworkServices: ";
	
	/** The server message. */
	private String serverMessage;
	
	/** The event. */
	private Event event;
    
    /** The Constant SERVERIP. */
    public static final String SERVERIP = "10.164.22.29"; //your computer IP address
    
    /** The Constant SERVERPORT. */
    public static final int SERVERPORT = 4456;
    
    /** The socket. */
    private Socket socket;
    
    /** The listeners. */
    private ArrayList<EventListener<?>> listeners = null;
    
    /** The m run. */
    private boolean mRun = false;
 
    /** The out. */
    PrintWriter out;
    
    /** The in. */
    BufferedReader in;
    
	/**
	 * Gets the network services class.
	 *
	 * @return the network services
	 */
	public static NetworkServices getNetworkServices() {
		if (networkServices == null) {
			networkServices = new NetworkServices();
		}
		return networkServices;
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		
	}
	
    /**
     * Checks if is internet on.
     *
     * @param context the context
     * @return true, if is internet on
     */
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
     * Add listeners for the socket.
     *
     * @param listener the listener
     */
    public void addListener (EventListener<?> listener) {
    	listeners.add(listener);
    }
    
    /**
     * Remove listeners for the socket.
     *
     * @param listener the listener
     */
    public void removeListener (EventListener<?> listener) {
    	listeners.remove(listener);
    }
    
	/**
	 * Fire listeners.
	 *
	 * @param <T> the generic type
	 * @param event the event
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public <T extends Event> void fireListeners (T event) throws IOException {
    	for (Iterator<EventListener<?>> it = listeners.iterator(); it.hasNext(); ) {
    		EventListener<T> listener = (EventListener<T>) it.next();
		    listener.eventReceived(event);
		}
    }
    
    /**
     * Sends the message entered by client to the server.
     *
     * @param command the command
     */
    public void sendMessage(Command command){
    	String message = createJson(command);
        if (out != null && !out.checkError()) {
            out.println(message);
            out.flush();
        }
    }
 
	/**
	 * * Serializes an object to be sent.
	 *
	 * @param objectToSend the object to send
	 * @return the string
	 */
	public String createJson(Object objectToSend) {
		//Gson gson = new Gson();
		GsonBuilder builder = new GsonBuilder(); 
        builder.registerTypeAdapter(Command.class, new InterfaceAdapter<Command>()); 
        Gson gson = builder.create(); 
		String json = gson.toJson(objectToSend);
		Log.e("Ahhhh", "??"+json);
		try {
			Class<?> mClass = Class.forName("com.oose2013.group7.roommates.common.commands.SignInCommand");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SignInCommand cmd = gson.fromJson(json, SignInCommand.class);
		Log.e("Ahhhh", "!!"+cmd);
		return json;

	}

	/**
	 * * Deserializes an object.
	 *
	 * @param json the json
	 * @return the object from json
	 */
	public Event getObjectFromJson(String json) {
		//Gson gson = new Gson();
		GsonBuilder builder = new GsonBuilder(); 
        builder.registerTypeAdapter(Event.class, new InterfaceAdapter<Event>()); 
        Gson gson = builder.create(); 
		Event eventObject = gson.fromJson(json, Event.class);
		return eventObject;
	}
	
	/**
	 * Sets the connected flag.
	 */
	public void setConnected() {
		mRun = true;
	}
	
    /**
     * Stop client.
     */
    public void stopClient(){
    	try {
			socket.close();
			mRun = false;
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
 
    /**
     * Checks if is connected.
     *
     * @return true, if is connected
     */
    public boolean isConnected() {
    	return mRun;
    }
    
    /**
     * Connect.
     */
    public void connect() {
    	new Connect().execute(networkServices);
    }
    
    /**
     * Start client.
     */
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
    
	/**
	 * The async connect task.
	 */
	public class Connect extends AsyncTask<NetworkServices, Void, NetworkServices> {
		
		/* (non-Javadoc)
		 * @see android.os.AsyncTask#doInBackground(Params[])
		 */
		@Override
		protected NetworkServices doInBackground(NetworkServices... ns) {
			ns[0].startClient();
			return null;
		}
	}
}    
