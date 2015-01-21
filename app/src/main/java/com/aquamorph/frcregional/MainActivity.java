package com.aquamorph.frcregional;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.aquamorph.frcregional.database.DBAdapter;


public class MainActivity extends Activity {

    DBAdapter myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button1 = (Button) findViewById(R.id.button);
        Button button2 = (Button) findViewById(R.id.button2);
        Button button3 = (Button) findViewById(R.id.button3);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayText("Clicked add record");

                long newiD = myDB.insertRow("Waldo", 987,"Blue");

                //Query record just added
                Cursor cursor = myDB.getRow(newiD);
                displayRecordSet(cursor);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayText("Clicked clear all");

                myDB.deleteAll();
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayText("Clicked display record");

                Cursor cursor = myDB.getAllRows();
                displayRecordSet(cursor);
            }
        });

        openDB();
    }

    private void displayRecordSet(Cursor cursor) {
        String message = "";
        //populate message from the cursor

        // Reset to start to check for data
        if(cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(DBAdapter.COL_ROWID);
                String name = cursor.getString(DBAdapter.COL_NAME);
                String color = cursor.getString(DBAdapter.COL_FAVCOLOUR);

                //create message
                message += " ID: " + id + " Name: " + name + " Color " + color + "\n";
            } while (cursor.moveToNext());
            //avoid leak
            cursor.close();
        }

        displayText(message);
    }

    private void openDB() {
        myDB = new DBAdapter(this);
        myDB.open();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        closeDB();
    }

    private void closeDB() {
        myDB.close();
    }

    private void displayText(String message) {
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(message);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
