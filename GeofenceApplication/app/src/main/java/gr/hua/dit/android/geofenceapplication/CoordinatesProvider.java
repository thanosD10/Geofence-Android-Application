package gr.hua.dit.android.geofenceapplication;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.room.Room;

import java.util.List;

public class CoordinatesProvider extends ContentProvider {

//    private UriMatcher uriMatcher;
//    private String AUTHORITY = "gr.hua.dit.android.geofenceapplication";
//    private String PATH = "COORDINATES";

    @Override
    public boolean onCreate() {
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        Context context = getContext();
        if(context == null) {
            return null;
        }

        CoordinatesDatabase database = Room.databaseBuilder(getContext(), CoordinatesDatabase.class, DbContract.DB_NAME).build();
        CoordinatesDao coordinatesDao = database.CoordinatesDao();

        Cursor cursor = coordinatesDao.getAllCoordinatesCursor();

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
