package gr.hua.dit.android.geofenceapplication;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CoordinatesDao {
    @Query("SELECT * FROM COORDINATES")
    public List<Coordinates> getAllCoordinates();

    @Query("SELECT * FROM COORDINATES")
    public Cursor getAllCoordinatesCursor();

    @Insert
    public void insertCoordinates(Coordinates... coordinates);
}
