package com.example.notes.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.notes.models.Model;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "notes.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "NOTES_TABLE";

    public static final String COLUMN_NAME_ID = "ID";
    public static final String COLUMN_NAME_NOTE = "NOTE";
    public static final String COLUMN_NAME_TITLE = "TITLE";

    private static final String TAG = "DatabaseHandler";

    public DatabaseHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // this is called first time database is accessed.
    // There should be code in here to create a new database
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CreateTableStatement = "CREATE TABLE " + TABLE_NAME + " (" + COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME_NOTE + " TEXT, "
                + COLUMN_NAME_TITLE + " TEXT)";

        db.execSQL(CreateTableStatement);
    }

    // this is called when the version number of the database changes.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean insertData(Model model) {

        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME_NOTE, model.getNote());
        cv.put(COLUMN_NAME_TITLE, model.getTitle());

        long result = db.insert(TABLE_NAME, null, cv);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public List<Model> getAllData() {
        List<Model> returnList = new ArrayList<>();

        //get data from database
        String queryString = "Select NOTE, TITLE FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                String note = cursor.getString(0);
                String title = cursor.getString(1);

                Model model = new Model(title, note);
                returnList.add(model);
            } while (cursor.moveToNext());
        } else {
            Log.i(TAG, "No Data Found");
        }
        cursor.close();
        return returnList;

    }
}
