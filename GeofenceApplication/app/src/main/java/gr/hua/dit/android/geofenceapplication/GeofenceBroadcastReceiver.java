package gr.hua.dit.android.geofenceapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.util.Log;
import android.widget.Toast;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import java.util.List;

public class GeofenceBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = "GeofenceBroadcastReceiv";

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
//        Toast.makeText(context, "Geofence Triggered...", Toast.LENGTH_SHORT).show();

        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        
        if(geofencingEvent.hasError()) {
            Log.d(TAG, "onReceive: Error receiving geofence event...");
            return;
        }

        List<Geofence> geofenceList = geofencingEvent.getTriggeringGeofences();
        for(Geofence geofence: geofenceList) {
            Log.d(TAG, "onReceive: " + geofence.getRequestId());
        }

        CoordinatesDatabase database = Room.databaseBuilder(context, CoordinatesDatabase.class, DbContract.DB_NAME).build();
        CoordinatesDao coordinatesDao = database.CoordinatesDao();

        Location location = geofencingEvent.getTriggeringLocation();
        long timestamp = System.currentTimeMillis()/1000;

        Coordinates coordinates = new Coordinates();
        coordinates.latitude = location.getLatitude();
        coordinates.longitude = location.getLongitude();
        coordinates.timestamp = timestamp;

        int transitionType = geofencingEvent.getGeofenceTransition();

        switch (transitionType) {
            case Geofence.GEOFENCE_TRANSITION_ENTER:
                Toast.makeText(context, "GEOFENCE_TRANSITION_ENTER", Toast.LENGTH_SHORT).show();
//                Toast.makeText(context, "LAT:" + location.getLatitude() + " ,LNG:" + location.getLongitude() + ", ACTION: ENTER, TIME:" + timestamp, Toast.LENGTH_SHORT).show();
                coordinates.action = "ENTER";

                break;
            case Geofence.GEOFENCE_TRANSITION_EXIT:
                Toast.makeText(context, "GEOFENCE_TRANSITION_EXIT", Toast.LENGTH_SHORT).show();
//                Toast.makeText(context, "LAT:" + location.getLatitude() + " ,LNG:" + location.getLongitude() + ", ACTION: EXIT, TIME:" + timestamp, Toast.LENGTH_SHORT).show();
                coordinates.action = "EXIT";
                break;
        }

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
//                Toast.makeText(context, "MPHKE!", Toast.LENGTH_SHORT).show();
                coordinatesDao.insertCoordinates(coordinates);

                List<Coordinates> coordinatesList = coordinatesDao.getAllCoordinates();
                int i = 1;
                for(Coordinates coordinates: coordinatesList) {
                    Log.d(TAG, "[Coordinates "+i+"]: " + coordinates.latitude+", "+coordinates.longitude+", "+coordinates.action+", "+coordinates.timestamp);
                    i++;
                }

            }
        });
        t.start();
    }
}