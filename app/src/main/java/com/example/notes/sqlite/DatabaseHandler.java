package com.example.notes.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.notes.models.NoteModel;

import java.util.ArrayList;
import java.util.List;

public class   DatabaseHandler extends SQLiteOpenHelper {

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

    public void insertData(NoteModel noteModel) {

        SQLiteDatabase db = this.getWritableDatabase();

        // t's a good idea to wrap our insert in a transaction. This helps with performance and ensures
        //consistency of the database.
        db.beginTransaction();
        try {
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_NAME_NOTE, noteModel.getNote());
            cv.put(COLUMN_NAME_TITLE, noteModel.getTitle());

            long result = db.insert(TABLE_NAME, null, cv);

            //// Checks if data insert succeeded
            if (result == -1) {
                Log.i(TAG, "Save Note Success = " + "False");
            } else {
                Log.i(TAG, "Save Note Success = " + "True");
            }

            db.setTransactionSuccessful();

        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add Note to database");
            e.printStackTrace();
            db.endTransaction();

        } finally {
            db.endTransaction();

        }


    }

    public List<NoteModel> getAllData() {
        List<NoteModel> returnList = new ArrayList<>();

        //get data from database
        String queryString = "Select * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(0);
                    String note = cursor.getString(1);
                    String title = cursor.getString(2);


                    NoteModel noteModel = new NoteModel(title, note);
                    noteModel.setId(id);
                    returnList.add(noteModel);
                } while (cursor.moveToNext());
            } else {
                Log.i(TAG, "No Notes Found");
            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "Error while trying to get Notes from database");
        } finally {
            cursor.close();
        }

        return returnList;

    }


    public void deleteData(NoteModel noteModel) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            int delete_count = db.delete(TABLE_NAME, "ID = ?", new String[] {Integer.toString(noteModel.getId())} );
            //// Checks if data delete succeeded
            if (delete_count > 0) {
                Log.i(TAG, "Note deleted successfully");
                db.setTransactionSuccessful();
            } else {
                Log.i(TAG, "Note deleted Unsuccessfully");
            }
        }
        catch (Exception e){
            e.printStackTrace();
            Log.d(TAG,"Error while trying to delete Note");
        }
        finally {
            db.endTransaction();
        }
    }

    public void updateData(NoteModel noteModel){
        // The database connection is cached so it's not expensive to call getWriteableDatabase() multiple times.
        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();
        try {
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_NAME_TITLE,noteModel.getTitle());
            cv.put(COLUMN_NAME_NOTE,noteModel.getNote());


            int rows = db.update(TABLE_NAME, cv, "ID = ?", new String[] {Integer.toString(noteModel.getId())});
            //// Checks if data insert succeeded
            if (rows > 0){
                Log.i(TAG, "Update Note Success = " + "True");
                db.setTransactionSuccessful();
            }
            else {
                Log.i(TAG, "Update Note Success = " + "False");
            }
        }
        catch (Exception e){
            e.printStackTrace();
            Log.d(TAG,"Error while trying to Update Note");
        }
        finally {
            db.endTransaction();
        }




    }
}
