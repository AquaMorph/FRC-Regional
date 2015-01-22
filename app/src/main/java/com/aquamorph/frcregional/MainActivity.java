package com.aquamorph.frcregional;

import android.app.Activity;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.aquamorph.frcregional.database.EventAdapter;
import com.aquamorph.frcregional.network.PostFetcher;


public class MainActivity extends Activity {

    EventAdapter myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PostFetcher fetcher = new PostFetcher(this);
        fetcher.execute();

        Button button1 = (Button) findViewById(R.id.button);
        Button button2 = (Button) findViewById(R.id.button2);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                populateListViewFromDB();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDB.deleteAll();
                populateListViewFromDB();
            }
        });

        openDB();
        populateListViewFromDB();
    }

    //Prevents restart from screen rotation
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    private void populateListViewFromDB() {
        Cursor cursor = myDB.getAllRows();

        //Allow activity to close activity
        startManagingCursor(cursor);

        //Set up mapping cursor to view fields
        String[] fromfieldName = new String[] {EventAdapter.KEY_NAME, EventAdapter.KEY_EVENTID};
        int[] toviewIDs = new int[] {R.id.eventName,R.id.eventID};

        //Create apter to map database listview
        SimpleCursorAdapter myCursorAdapter = new SimpleCursorAdapter(this,
                R.layout.event_layout,
                cursor,
                fromfieldName,
                toviewIDs);


        //set the adapter for list view
        ListView events = (ListView) findViewById(R.id.listView);
        events.setAdapter(myCursorAdapter);
    }

    private void openDB() {
        myDB = new EventAdapter(this);
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

    /*private class PostFetcher extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            //Checks for valid connection
            HttpResponse response = Http.getRequest(Constants.getEventURL(Constants.TEAM));
            StatusLine statusLine = response.getStatusLine();
            if (statusLine.getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                InputStream content = null;
                try {
                    content = entity.getContent();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //Parse it as JSON
                Reader reader = new InputStreamReader(content);

                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                eventListses = new ArrayList<EventLists>();
                eventListses = Arrays.asList(gson.fromJson(reader, EventLists[].class));
                try {
                    content.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                myDB.deleteAll();

                for (EventLists eventLists : MainActivity.this.eventListses) {
                    myDB.insertRow(eventLists.name, eventLists.event_code);
                }
            }
            return null;
        }
    }*/
}
