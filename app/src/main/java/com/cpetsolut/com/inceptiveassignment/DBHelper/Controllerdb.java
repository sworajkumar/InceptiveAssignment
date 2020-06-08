package com.cpetsolut.com.inceptiveassignment.DBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class Controllerdb extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "PatientDB";
    private static final String TABLE_Users = "PatientDetails";
    private static final String KEY_ID = "id";
    private static final String KEY_PATIENTNAME = "pname";
    private static final String KEY_UHIDNUMBER = "pUHIDnumber";
    private static final String KEY_GENDER = "PGender";
    private static final String KEY_AGE = "PAge";
    private static final String KEY_DOB = "Pdob";
    private static final String KEY_MOBILENUMBER = "Pmobilenumber";
    private static final String KEY_ADDRESS = "paddress";

    public Controllerdb(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_Users + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_PATIENTNAME + " TEXT,"
                + KEY_UHIDNUMBER + " TEXT,"
                + KEY_GENDER + " TEXT,"
                + KEY_AGE + " TEXT,"
                + KEY_DOB + " TEXT,"
                + KEY_MOBILENUMBER + " TEXT,"
                + KEY_ADDRESS + " TEXT" + ")";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Users);
        // Create tables again
        onCreate(db);
    }
    // **** CRUD (Create, Read, Update, Delete) Operations ***** //

    // Adding new User Details
    public void insertUserDetails(String Pname, String Puhidnumber, String pdob, String page, String pGender, String pmobilenumber, String paddress) {
        //Get the Data Repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();
        //Create a new map of values, where column names are the keys
        ContentValues cValues = new ContentValues();
        cValues.put(KEY_PATIENTNAME, Pname);
        cValues.put(KEY_UHIDNUMBER, Puhidnumber);
        cValues.put(KEY_DOB, pdob);
        cValues.put(KEY_AGE, page);
        cValues.put(KEY_GENDER, pGender);
        cValues.put(KEY_MOBILENUMBER, pmobilenumber);
        cValues.put(KEY_ADDRESS, paddress);
        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(TABLE_Users, null, cValues);
        db.close();
    }

    // Get User Details
    public ArrayList<HashMap<String, String>> GetUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
        String query = "SELECT pname,pUHIDnumber,PAge,paddress FROM " + TABLE_Users;
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            HashMap<String, String> user = new HashMap<>();
            user.put("pname", cursor.getString(cursor.getColumnIndex(KEY_PATIENTNAME)));
            user.put("pUHIDnumber", cursor.getString(cursor.getColumnIndex(KEY_UHIDNUMBER)));
            //user.put("PGender", cursor.getString(cursor.getColumnIndex(KEY_GENDER)));
            user.put("PAge", cursor.getString(cursor.getColumnIndex(KEY_AGE)));
            //user.put("Pdob", cursor.getString(cursor.getColumnIndex(KEY_DOB)));
            //user.put("Pmobilenumber", cursor.getString(cursor.getColumnIndex(KEY_MOBILENUMBER)));
            user.put("paddress", cursor.getString(cursor.getColumnIndex(KEY_ADDRESS)));
            userList.add(user);
        }
        return userList;
    }
}