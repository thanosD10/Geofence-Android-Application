package gr.hua.dit.android.geofenceapplication;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Coordinates.class}, version = 1)
public abstract class CoordinatesDatabase extends RoomDatabase {
    public abstract CoordinatesDao CoordinatesDao();
}
