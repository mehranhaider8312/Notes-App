package com.mehran.assignmentoraxtech.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MyDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "NotesDatabase";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NOTES = "notes";
    public static final String KEY_ID = "id";
    public static final String KEY_TITLE = "title";
    public static final String KEY_CONTENT = "content";

    private static final String TAG = "MyDBHelper";

    public MyDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_NOTES + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_TITLE + " TEXT,"
                + KEY_CONTENT + " TEXT)";
        db.execSQL(createTableQuery);
        Log.d(TAG, "Database created successfully with table: " + TABLE_NOTES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
        onCreate(db);
        Log.d(TAG, "Database upgraded from version " + oldVersion + " to " + newVersion);
    }

    public ArrayList<Note> fetchNotes() {
        ArrayList<Note> notes = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_NOTES;
        Cursor cursor = db.rawQuery(query, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    String title = cursor.getString(cursor.getColumnIndexOrThrow(KEY_TITLE));
                    String content = cursor.getString(cursor.getColumnIndexOrThrow(KEY_CONTENT));

                    notes.add(new Note(title, content));
                } while (cursor.moveToNext());
                Log.d(TAG, "Fetched " + notes.size() + " notes from database");
            } else {
                Log.d(TAG, "No notes found in database");
            }
        } catch (Exception e) {
            Log.e(TAG, "Error fetching notes: " + e.getMessage());
        } finally {
            cursor.close();
            db.close();
        }

        return notes;
    }

    public void addNote(String title, String content) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, title);
        values.put(KEY_CONTENT, content);

        long result = db.insert(TABLE_NOTES, null, values);
        if (result == -1) {
            Log.e(TAG, "Failed to insert note: " + title);
        } else {
            Log.d(TAG, "Note added successfully: " + title);
        }

        db.close();
    }

    public int deleteNote(String title) {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = KEY_TITLE + " = ?";
        String[] whereArgs = new String[]{title};

        int result = db.delete(TABLE_NOTES, whereClause, whereArgs);
        if (result > 0) {
            Log.d(TAG, "Note deleted successfully: " + title);
        } else {
            Log.e(TAG, "No note found with title: " + title);
        }

        db.close();
        return result;
    }
}