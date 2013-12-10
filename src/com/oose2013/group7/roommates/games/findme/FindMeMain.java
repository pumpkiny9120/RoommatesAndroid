package com.oose2013.group7.roommates.games.findme;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.oose2013.group7.roommates.R;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


public class FindMeMain extends Activity implements 
	OnMapClickListener,
	GooglePlayServicesClient.ConnectionCallbacks,
    GooglePlayServicesClient.OnConnectionFailedListener {
	
		private GoogleMap map;
		private LatLng myLoc;
		private LatLng dest;
		private LatLng lastGuess;
		private LocationClient mLocationClient;
		private final static int
        CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
		
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_findme);

	        
	        // set up location services
	        mLocationClient = new LocationClient(this, this, this);
	        mLocationClient.connect();
	        Location currentLocation = mLocationClient.getLastLocation();

            // Display the current location in the UI
            myLoc = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
	        
	        
	        // set up map
	        // Get a handle to the Map Fragment
	        map = ((MapFragment) getFragmentManager()
	                .findFragmentById(R.id.map)).getMap();

	        // TODO get current location from GPS
	        // TODO get destination from server
	        myLoc = new LatLng(-33.867, 151.206);
	        dest = new LatLng(37.423, -122.091);
	        // start with my location
	        lastGuess = myLoc;
	        
	        map.setMyLocationEnabled(true);
	        map.moveCamera(CameraUpdateFactory.newLatLngZoom(myLoc, 5));

	        map.addMarker(new MarkerOptions()
	                .title("Sydney")
	                .snippet("The most populous city in Australia.")
	                .position(myLoc));
	        
	        map.setOnMapClickListener(this);
	        

	    }

		@Override
		public void onMapClick(LatLng point) {
	        map.addMarker(new MarkerOptions()
            .title("Another guess!")
            .snippet("Hope you're a little bit closer...")
            .position(point));
			
	        // Polylines for marking paths on the map.
	        map.addPolyline(new PolylineOptions().geodesic(true)
	                .add(lastGuess) // from previous guessed point
	                .add(point)     // to the clicked point
	        );
	        
	        lastGuess = point;		// update the guess
		}

		@Override
		public void onConnectionFailed(ConnectionResult connectionResult) {
			if (connectionResult.hasResolution()) {
	            try {
	                // Start an Activity that tries to resolve the error
	                connectionResult.startResolutionForResult(
	                        this,
	                        CONNECTION_FAILURE_RESOLUTION_REQUEST);
	                /*
	                 * Thrown if Google Play services canceled the original
	                 * PendingIntent
	                 */
	            } catch (IntentSender.SendIntentException e) {
	                // Log the error
	                e.printStackTrace();
	            }
	        } else {
	            /*
	             * If no resolution is available, display a dialog to the
	             * user with the error.
	             */
	        	Toast.makeText(this, connectionResult.getErrorCode(), Toast.LENGTH_SHORT).show();
	        }
		}

		@Override
		public void onConnected(Bundle connectionHint) {
			Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onDisconnected() {
			Toast.makeText(this, "Disconnected. Please re-connect.",
		                Toast.LENGTH_SHORT).show();
		}

	    /*
	     * Handle results returned to the FragmentActivity
	     * by Google Play services
	     */
	    @Override
	    protected void onActivityResult(
	            int requestCode, int resultCode, Intent data) {
	        // Decide what to do based on the original request code
	        switch (requestCode) {
	            case CONNECTION_FAILURE_RESOLUTION_REQUEST :
	            /*
	             * If the result code is Activity.RESULT_OK, try
	             * to connect again
	             */
	                switch (resultCode) {
	                    case Activity.RESULT_OK :
	                    /*
	                     * Try the request again
	                     */

	                    break;
	                }
	        }
	     }

	    private boolean servicesConnected() {
	        // Check that Google Play services is available
	        int resultCode =
	                GooglePlayServicesUtil.
	                        isGooglePlayServicesAvailable(this);
	        // If Google Play services is available
	        if (ConnectionResult.SUCCESS == resultCode) {
	            // In debug mode, log the status
	            Log.d("Location Updates",
	                    "Google Play services is available.");
	            // Continue
	            return true;
	        // Google Play services was not available for some reason
	        } else {
	        	Toast.makeText(this, "Failed to connect.",
		                Toast.LENGTH_SHORT).show();
	        	return false;
	        }
	    }
	}
