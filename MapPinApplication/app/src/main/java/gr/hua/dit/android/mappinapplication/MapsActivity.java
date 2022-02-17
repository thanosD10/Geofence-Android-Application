package gr.hua.dit.android.mappinapplication;

import androidx.fragment.app.FragmentActivity;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Calendar;
import java.util.Locale;

import gr.hua.dit.android.mappinapplication.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Move the camera close to HUA
        LatLng hua = new LatLng(37.962182124998506, 23.700916588356975);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(hua, 12));

        addMarkers();
    }

    public void addMarkers() {
        mMap.clear();

        ContentResolver resolver = getContentResolver();
        Uri uri = Uri.parse("content://"+DbContract.AUTHORITY+"/"+DbContract.PATH);

        Cursor cursor = resolver.query(uri, null, null, null, null);

        if(cursor.moveToFirst()) {
            do {

                MarkerOptions marker = new MarkerOptions();
                LatLng latLng = new LatLng(cursor.getDouble(0), cursor.getDouble(1));
                        marker.position(latLng);
                        marker.title(cursor.getString(2));
                        if(cursor.getString(2).equals("ENTER")) {
                            marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                        }
                        String convertedDate = getDate(cursor.getLong(3));
                        marker.snippet("Time: "+convertedDate);

                        mMap.addMarker(marker).showInfoWindow();

//                Log.d("Coordinates: ",  "[Item]: "+cursor.getString(0)+", "+cursor.getString(1)+", "+cursor.getString(2)+", "+cursor.getString(3));
//                Toast.makeText(MainActivity.this, "[Item]: "+cursor.getString(0)+", "+cursor.getString(1)+", "+cursor.getString(2)+", "+cursor.getString(3), Toast.LENGTH_LONG).show();
            } while (cursor.moveToNext());
        }
    }

    private String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time * 1000);
        String date = DateFormat.format("dd-MM-yyyy hh:mm:ss", cal).toString();
        return date;
    }
}