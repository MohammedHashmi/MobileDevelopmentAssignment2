package com.example.mobiledevassignment2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "locations.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "location";
    private static final String COL_ID = "id";
    private static final String COL_ADDRESS = "address";
    private static final String COL_LATITUDE = "latitude";
    private static final String COL_LONGITUDE = "longitude";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_ADDRESS + " TEXT, " +
                COL_LATITUDE + " REAL, " +
                COL_LONGITUDE + " REAL)";
        db.execSQL(createTable);

        // Insert 100 sample locations from GTA cities
        insertSampleLocations(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Method to insert sample locations from GTA cities
    private void insertSampleLocations(SQLiteDatabase db) {
        // Oshawa
        addLocation(db, "Oshawa City Hall", 43.8971, -78.8658);
        addLocation(db, "Oshawa Centre Mall", 43.8965, -78.8666);
        addLocation(db, "Oshawa GO Station", 43.8895, -78.8623);addLocation(db, "Tribute Communities Centre", 43.8909, -78.8635);
        addLocation(db, "Lakeview Park", 43.8729, -78.8182);
        addLocation(db, "Ontario Tech University", 43.9444, -78.8964);
        addLocation(db, "Durham College", 43.9454, -78.8963);
        addLocation(db, "GM Centre Oshawa", 43.9052, -78.8696);
        addLocation(db, "Ajax Community Centre", 43.8509, -79.0204);
        addLocation(db, "Ajax GO Station", 43.8474, -79.0320);
        addLocation(db, "Ajax Public Library", 43.8542, -79.0223);
        addLocation(db, "Harwood Plaza", 43.8512, -79.0205);
        addLocation(db, "Pickering Town Centre", 43.8353, -79.0896);
        addLocation(db, "Pickering GO Station", 43.8352, -79.0850);
        addLocation(db, "Pickering Public Library", 43.8371, -79.0895);
        addLocation(db, "Pickering Museum Village", 43.9646, -79.0791);
        addLocation(db, "Amberlea Park", 43.8440, -79.0892);
        addLocation(db, "David Farr Park", 43.8418, -79.0833);
        addLocation(db, "Scarborough Town Centre", 43.7766, -79.2316);
        addLocation(db, "Scarborough Civic Centre", 43.7735, -79.2578);
        addLocation(db, "Scarborough Bluffs", 43.7054, -79.2299);
        addLocation(db, "Toronto Zoo", 43.8200, -79.1827);
        addLocation(db, "Scarborough General Hospital", 43.7572, -79.2636);
        addLocation(db, "University of Toronto Scarborough", 43.7845, -79.1859);
        addLocation(db, "CN Tower", 43.6426, -79.3871);
        addLocation(db, "Royal Ontario Museum", 43.6677, -79.3948);
        addLocation(db, "Ripley's Aquarium", 43.6424, -79.3860);
        addLocation(db, "Toronto Eaton Centre", 43.6544, -79.3807);
        addLocation(db, "St. Lawrence Market", 43.6487, -79.3715);
        addLocation(db, "Hockey Hall of Fame", 43.6469, -79.3776);
        addLocation(db, "Distillery District", 43.6503, -79.3596);
        addLocation(db, "Toronto City Hall", 43.6535, -79.3840);
        addLocation(db, "Nathan Phillips Square", 43.6525, -79.3839);
        addLocation(db, "High Park", 43.6465, -79.4637);
        addLocation(db, "Square One Shopping Centre", 43.5934, -79.6411);
        addLocation(db, "Erin Mills Town Centre", 43.5610, -79.7111);
        addLocation(db, "Living Arts Centre", 43.5906, -79.6405);
        addLocation(db, "Brampton City Hall", 43.6875, -79.7604);
        addLocation(db, "Bramalea City Centre", 43.7152, -79.7262);
        addLocation(db, "Heart Lake Conservation Area", 43.7316, -79.7821);
        addLocation(db, "Rose Theatre Brampton", 43.6873, -79.7615);
        addLocation(db, "Mount Pleasant GO Station", 43.6708, -79.8210);
        addLocation(db, "Brampton Soccer Centre", 43.7275, -79.7402);
        addLocation(db, "Markham Civic Centre", 43.8561, -79.3370);
        addLocation(db, "Markville Mall", 43.8748, -79.2848);
        addLocation(db, "Pacific Mall", 43.8253, -79.3049);
        addLocation(db, "Markham Museum", 43.8830, -79.2491);
        addLocation(db, "Milne Dam Conservation Park", 43.8721, -79.2701);
        addLocation(db, "Cornell Community Centre", 43.8743, -79.2432);
        addLocation(db, "Markham Stouffville Hospital", 43.8832, -79.2518);
    }

    private void addLocation(SQLiteDatabase db, String address, double latitude, double longitude) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_ADDRESS, address);
        contentValues.put(COL_LATITUDE, latitude);
        contentValues.put(COL_LONGITUDE, longitude);
        db.insert(TABLE_NAME, null, contentValues);
    }

    // Method to add a location intop the database when the user inputs values
    public boolean addLocation(String address, double latitude, double longitude) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_ADDRESS, address);
        contentValues.put(COL_LATITUDE, latitude);
        contentValues.put(COL_LONGITUDE, longitude);
        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }

    // Method to get all locations stored in the database
    public Cursor getAllLocations() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    // Method to get latitude and longitude by address
    public Cursor getLocationByAddress(String address) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_NAME, new String[]{COL_LATITUDE, COL_LONGITUDE}, COL_ADDRESS + " = ?", new String[]{address}, null, null, null);
    }

    // Method to delete a location by address when user inputs what address they want to delete
    public boolean deleteLocation(String address) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_NAME, COL_ADDRESS + " = ?", new String[]{address});
        return result > 0;
    }

}
