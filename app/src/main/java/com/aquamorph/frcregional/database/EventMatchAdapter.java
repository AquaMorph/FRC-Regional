package com.aquamorph.frcregional.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.aquamorph.frcregional.Constants;

public class EventMatchAdapter {
    private static final String TAG = "EventMatchAdapter";

    // DB Fields
    public static final String KEY_ROWID = "_id";
    public static final int COL_ROWID = 0;

    // Database Columns
    public static final String KEY_COMPLEVEL      = "complevel";
    public static final String KEY_MATCHNUMBER    = "matchnumber";
    public static final String KEY_TIMESTRING     = "timestring";
    public static final String KEY_SETNUMBER      = "setnumber";
    public static final String KEY_KEY            = "key";
    public static final String KEY_SCOREBREAKDOWN = "scorebreakdown";
    public static final String KEY_BLUESCORE      = "bluescore";
    public static final String KEY_BLUETEAM1      = "blueteam1";
    public static final String KEY_BLUETEAM2      = "blueteam2";
    public static final String KEY_BLUETEAM3      = "blueteam3";
    public static final String KEY_REDSCORE       = "redscore";
    public static final String KEY_REDTEAM1       = "redteam1";
    public static final String KEY_REDTEAM2       = "redteam2";
    public static final String KEY_REDTEAM3       = "redteam3";

    // Database Column ID Numbers
    public static final int COL_COMPLEVEL      = 1;
    public static final int COL_MATCHNUMBER    = 2;
    public static final int COL_TIMESTRING     = 3;
    public static final int COL_SETNUMBER      = 4;
    public static final int COL_KEY            = 5;
    public static final int COL_SCOREBREAKDOWN = 6;
    public static final int COL_BLUESCORE      = 7;
    public static final int COL_BLUETEAM1      = 8;
    public static final int COL_BLUETEAM2      = 9;
    public static final int COL_BLUETEAM3      = 10;
    public static final int COL_REDSCORE       = 11;
    public static final int COL_REDTEAM1       = 12;
    public static final int COL_REDTEAM2       = 13;
    public static final int COL_REDTEAM3       = 14;

    public static final String[] ALL_KEYS = new String[] {KEY_ROWID, KEY_COMPLEVEL,
            KEY_MATCHNUMBER, KEY_TIMESTRING, KEY_SETNUMBER, KEY_KEY, KEY_SCOREBREAKDOWN,
            KEY_BLUESCORE};

    // Set Database Info
    public static final String DATABASE_NAME = "FRCRegional";
    public static final String DATABASE_TABLE = "eventMatches";
    public static final int DATABASE_VERSION = Constants.DATABASE_VERSION;

    private static final String DATABASE_CREATE_SQL =
            "create table " + DATABASE_TABLE
                    + " (" + KEY_ROWID   + " integer primary key autoincrement, "
                    + KEY_COMPLEVEL      + " string not null, "
                    + KEY_MATCHNUMBER    + " string not null, "
                    + KEY_TIMESTRING     + " string not null, "
                    + KEY_SETNUMBER      + " string not null, "
                    + KEY_KEY            + " string not null, "
                    + KEY_SCOREBREAKDOWN + " string not null, "
                    + KEY_BLUESCORE      + " string not null, "
                    + KEY_BLUETEAM1      + " string not null, "
                    + KEY_BLUETEAM2      + " string not null, "
                    + KEY_BLUETEAM3      + " string not null, "
                    + KEY_REDSCORE       + " string not null, "
                    + KEY_REDTEAM1       + " string not null, "
                    + KEY_REDTEAM2       + " string not null, "
                    + KEY_REDTEAM3       + " string not null "
                    + ");";

    private final Context context;
    private DatabaseHelper myDBHelper;
    private SQLiteDatabase db;

    public EventMatchAdapter(Context ctx) {
        this.context = ctx;
        myDBHelper = new DatabaseHelper(context);
    }

    // Open Database Connection
    public EventMatchAdapter open() {
        db = myDBHelper.getWritableDatabase();
        return this;
    }

    // Close Database Connection
    public void close() {
        myDBHelper.close();
    }

    // Add a new set of values to the database
    public long insertRow(String complevel, String matchnumber, String timestring,
                          String setnumber, String key, String scorebreakdown, String bluescore,
                          String blueteam1, String blueteam2, String blueteam3, String redscore,
                          String redteam1, String redteam2, String redteam3) {
        // Create row's data:
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_COMPLEVEL      , complevel);
        initialValues.put(KEY_MATCHNUMBER    , matchnumber);
        initialValues.put(KEY_TIMESTRING     , timestring);
        initialValues.put(KEY_SETNUMBER      , setnumber);
        initialValues.put(KEY_KEY            , key);
        initialValues.put(KEY_SCOREBREAKDOWN , scorebreakdown);
        initialValues.put(KEY_BLUESCORE      , bluescore);
        initialValues.put(KEY_BLUETEAM1      , blueteam1);
        initialValues.put(KEY_BLUETEAM2      , blueteam2);
        initialValues.put(KEY_BLUETEAM3      , blueteam3);
        initialValues.put(KEY_REDSCORE       , redscore);
        initialValues.put(KEY_REDTEAM1       , redteam1);
        initialValues.put(KEY_REDTEAM2       , redteam2);
        initialValues.put(KEY_REDTEAM3       , redteam3);

        // Insert it into the database
        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    // Delete a row from the database, by rowId (primary key)
    public boolean deleteRow(long rowId) {
        String where = KEY_ROWID + "=" + rowId;
        return db.delete(DATABASE_TABLE, where, null) != 0;
    }

    public void deleteAll() {
        Cursor c = getAllRows();
        long rowId = c.getColumnIndexOrThrow(KEY_ROWID);
        if (c.moveToFirst()) {
            do {
                deleteRow(c.getLong((int) rowId));
            } while (c.moveToNext());
        }
        c.close();
    }

    // Return all data in the database.
    public Cursor getAllRows() {
        String where = null;
        Cursor c = 	db.query(true, DATABASE_TABLE, ALL_KEYS,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    // Get a specific row (by rowId)
    public Cursor getRow(long rowId) {
        String where = KEY_ROWID + "=" + rowId;
        Cursor c = 	db.query(true, DATABASE_TABLE, ALL_KEYS,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    // Change an existing row to be equal to new data.
    public boolean updateRow(long rowId, String complevel, String matchnumber, String timestring,
                             String setnumber, String key, String scorebreakdown, String bluescore,
                             String blueteam1, String blueteam2, String blueteam3, String redscore,
                             String redteam1, String redteam2, String redteam3) {
        String where = KEY_ROWID + "=" + rowId;

        // Create row's data:
        ContentValues newValues = new ContentValues();
        newValues.put(KEY_COMPLEVEL      , complevel);
        newValues.put(KEY_MATCHNUMBER    , matchnumber);
        newValues.put(KEY_TIMESTRING     , timestring);
        newValues.put(KEY_SETNUMBER      , setnumber);
        newValues.put(KEY_KEY            , key);
        newValues.put(KEY_SCOREBREAKDOWN , scorebreakdown);
        newValues.put(KEY_BLUESCORE      , bluescore);
        newValues.put(KEY_BLUETEAM1      , blueteam1);
        newValues.put(KEY_BLUETEAM2      , blueteam2);
        newValues.put(KEY_BLUETEAM3      , blueteam3);
        newValues.put(KEY_REDSCORE       , redscore);
        newValues.put(KEY_REDTEAM1       , redteam1);
        newValues.put(KEY_REDTEAM2       , redteam2);
        newValues.put(KEY_REDTEAM3       , redteam3);

        // Insert it into the database.
        return db.update(DATABASE_TABLE, newValues, where, null) != 0;
    }

    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase _db) {
            _db.execSQL(DATABASE_CREATE_SQL);
        }

        @Override
        public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading application's database from version " + oldVersion
                    + " to " + newVersion + ", which will destroy all old data!");

            // Destroy old database:
            _db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);

            // Recreate new database:
            onCreate(_db);
        }
    }
}
