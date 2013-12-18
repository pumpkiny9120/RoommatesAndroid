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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.oose2013.group7.roommates.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Color;
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
		public final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
		
	    public enum Direction {
	    	LEFT, RIGHT, UP, DOWN;
	    }
	    
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_findme);

	        // set up location services
	        mLocationClient = new LocationClient(this, this, this);
        
	        // wait for connecting to the google map services
	        // and then initialize
	    }

	    private void initialize() {
	        if (servicesConnected()) {
	        	Location currentLocation = mLocationClient.getLastLocation();
	            myLoc = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
	        }
	        else {
	        	// TODO give error
	        	myLoc = new LatLng(-33.867, 151.206);
	        }
	    	//myLoc = new LatLng(-33.867, 151.206);
	        // set up map
	        // Get a handle to the Map Fragment
	        map = ((MapFragment) getFragmentManager()
	                .findFragmentById(R.id.map)).getMap();

	        // TODO get destination from server
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
	        Marker marker = map.addMarker(new MarkerOptions()
            .title("Yay another guess!")
            .snippet("I'm sure you're a little bit closer...")
            .position(point));
	        marker.showInfoWindow();
			
	        // Polylines for marking paths on the map.
	        map.addPolyline(new PolylineOptions().geodesic(true).width(5).color(Color.RED)
	                .add(lastGuess) // from previous guessed point
	                .add(point)     // to the clicked point
	        );
	        
	        lastGuess = point;		// update the guess
	        checkDestination(point);
		}

		private void checkDestination(LatLng point) {
			double latA = point.latitude;
			double longA = point.longitude;
			double latB = dest.latitude;
			double longB = dest.longitude;
			
			Location myGuess = new Location("");
			myGuess.setLatitude(latA);
			myGuess.setLongitude(longA);
			Location myDest = new Location("");
			myDest.setLatitude(latB);
			myDest.setLongitude(longB);

			// calculate distance to destination
	        Float distance = myDest.distanceTo(myGuess); // in meters
	        distance = distance/1000; // in kilometers
	        
	        Direction directionLeftRight = null;
	        Direction directionUpDown = null;
	        Log.d("Location Updates",
	        		"Lat :"+(latB - latA));
	        Log.d("Location Updates",
	        		"Long:"+(longB - longA));
	        if ((longB - longA) > 0) {
	        	directionLeftRight = Direction.RIGHT;
	        }
	        else {
	        	directionLeftRight = Direction.LEFT;
	        }
	        if ((latB - latA) > 0) {
	        	directionUpDown = Direction.UP;
	        }
	        else {
	        	directionUpDown = Direction.DOWN;
	        }
	        
	        Toast.makeText(this, distance.toString(), Toast.LENGTH_LONG).show();
	        showDirection(directionLeftRight, directionUpDown, distance);
	        
		}
		
		private void showDirection(Direction directionLeftRight, Direction directionUpDown, Float distance) {
			String leftRight = null;
			if (directionLeftRight == Direction.LEFT) {
				leftRight = "Left";
			}
			else {
				leftRight = "Right";
			}
			String upDown = null;
			if (directionUpDown == Direction.UP) {
				upDown = "Up";
			}
			else {
				upDown = "Down";
			}
			
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					this);
			alertDialogBuilder.setTitle("Some hint here:");
			
			if (distance < 20) {
				alertDialogBuilder.setMessage("Only less than 20 kilometers away. We are so close.")
				.setCancelable(false)
				.setNegativeButton("Wheee I know where you are!", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						dialog.cancel();
						// TODO make a grand end of this game
					}
				});
			}
			else {
				alertDialogBuilder.setMessage(leftRight+", "+upDown + ", and " + distance.toString() + " kilometers away.")
				.setCancelable(false)
				.setNegativeButton("Make another guess!", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						dialog.cancel();
					}
				});
			}		

			AlertDialog alertDialog = alertDialogBuilder.create();
			alertDialog.show();
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
			Toast.makeText(this, "Connected", Toast.LENGTH_LONG).show();
			initialize();
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
	    
	    /*
	     * Called when the Activity becomes visible.
	     */
	    @Override
	    protected void onStart() {
	        super.onStart();
	        // Connect the client.
	        mLocationClient.connect();
	    }

	    /*
	     * Called when the Activity is no longer visible.
	     */
	    @Override
	    protected void onStop() {
	        // Disconnecting the client invalidates it.
	        mLocationClient.disconnect();
	        super.onStop();
	    }
	}
