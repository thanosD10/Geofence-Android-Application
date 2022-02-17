package gr.hua.dit.android.geofenceapplication;

import android.location.Location;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "COORDINATES")
public class Coordinates {

    @ColumnInfo(name = "LATITUDE")
    public double latitude;

    @ColumnInfo(name = "LONGITUDE")
    public double longitude;

    @ColumnInfo(name = "ACTION")
    public String action;

    @PrimaryKey
    @ColumnInfo(name = "TIMESTAMP")
    public long timestamp;

}
