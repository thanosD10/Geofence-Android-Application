package gr.hua.dit.android.mappinapplication;

public class DbContract {
    static public String DB_NAME = "COORDINATES_DB";
    static public int DB_VERSION = 1;
    static public String TABLE_NAME = "COORDINATES";
    static public String FIELD_1 = "LATITUDE";
    static public String FIELD_2 = "LONGITUDE";
    static public String FIELD_3 = "ACTION";
    static public String FIELD_4 = "TIMESTAMP";
    static public String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+" ('"+FIELD_1+"' REAL, '"+FIELD_2+"' REAL, '"+FIELD_3+"' TEXT, '"+FIELD_4+"' INTEGER);";
    static public String AUTHORITY = "gr.hua.dit.android.geofenceapplication";
    static public String PATH = DbContract.TABLE_NAME;
}
