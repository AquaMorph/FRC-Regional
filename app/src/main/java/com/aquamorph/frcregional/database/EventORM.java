package com.aquamorph.frcregional.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.aquamorph.frcregional.Constants;
import com.aquamorph.frcregional.connection.EventLists;

import java.util.ArrayList;
import java.util.List;

public class EventORM {

    private static final String TABLE_NAME = "events";

    private static final String COMMA_SEP = ", ";

    private static final String COLUMN_ID_TYPE = "INTEGER PRIMARY KEY";
    private static final String COLUMN_ID = "id";

    private static final String COLUMN_NAME_TYPE = "TEXT";
    private static final String COLUMN_NAME = "name";

    private static final String COLUMN_CODE_TYPE = "TEXT";
    private static final String COLUMN_CODE = "event_code";

    private static final String TAG = "EventORM";

    public static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " " + COLUMN_ID_TYPE + COMMA_SEP +
                    COLUMN_NAME  + " " + COLUMN_NAME_TYPE + COMMA_SEP +
                    COLUMN_CODE + " " + COLUMN_CODE_TYPE + COMMA_SEP +
                    ")";

    public static final String SQL_DROP_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public static void insertPost(Context context, EventLists post) {
        DatabaseWrapper databaseWrapper = new DatabaseWrapper(context);
        SQLiteDatabase database = databaseWrapper.getWritableDatabase();

        ContentValues values = postToContentValues(post);
        long postId = database.insert(EventORM.TABLE_NAME, "null", values);
        Log.i(Constants.LOG_TAG, "Inserted new Post with ID: " + postId);

        database.close();
    }

    //Packs a Post object into a ContentValues map for use with SQL inserts.
    private static ContentValues postToContentValues(EventLists eventLists) {
        ContentValues values = new ContentValues();
        values.put(EventORM.COLUMN_ID, eventLists.id);
        values.put(EventORM.COLUMN_NAME, eventLists.name);
        values.put(EventORM.COLUMN_CODE, eventLists.event_code);

        return values;
    }

    public static List<EventLists> getPosts(Context context) {
        DatabaseWrapper databaseWrapper = new DatabaseWrapper(context);
        SQLiteDatabase database = databaseWrapper.getReadableDatabase();

        Cursor cursor = database.rawQuery("SELECT * FROM " + EventORM.TABLE_NAME, null);

        Log.i(TAG, "Loaded " + cursor.getCount() + " Posts...");
        List<EventLists> postList = new ArrayList<EventLists>();

        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                EventLists post = cursorToPost(cursor);
                postList.add(post);
                cursor.moveToNext();
            }
            Log.i(TAG, "Posts loaded successfully.");
        }

        database.close();

        return postList;
    }

    //Populates a Post object with data from a Cursor
    private static EventLists cursorToPost(Cursor cursor) {
        EventLists post = new EventLists();
        post.setId(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)));
        post.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
        post.setEvent_code(cursor.getString(cursor.getColumnIndex(COLUMN_CODE)));

        return post;
    }
}
