package com.aquamorph.frcregional.connection;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

import com.aquamorph.frcregional.Constants;
import com.aquamorph.frcregional.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PostActivity extends Activity {

    private List<EventLists> eventListses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PostFetcher fetcher = new PostFetcher();
        fetcher.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void handlePostsList(List<EventLists> eventListses) {
        this.eventListses = eventListses;




        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for (EventLists eventLists : PostActivity.this.eventListses) {
                    Toast.makeText(PostActivity.this, eventLists.event_code, Toast.LENGTH_SHORT).show();
                    Toast.makeText(PostActivity.this, eventLists.name, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void failedLoad(final String error, final Exception ex) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(PostActivity.this, error + ex, Toast.LENGTH_SHORT).show();
            }
        });
    }


    private class PostFetcher extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            try {
                //Connects to the Blue Alliance
                HttpClient client = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(Constants.getEventURL("2059"));
                httpGet.addHeader("X-TBA-App-Id", Constants.HEADER);

                //Checks for valid connection
                HttpResponse response = client.execute(httpGet);
                StatusLine statusLine = response.getStatusLine();
                if (statusLine.getStatusCode() == 200) {
                    HttpEntity entity = response.getEntity();
                    InputStream content = entity.getContent();

                    try {
                        //Parse it as JSON
                        Reader reader = new InputStreamReader(content);

                        GsonBuilder gsonBuilder = new GsonBuilder();
                        Gson gson = gsonBuilder.create();
                        List<EventLists> eventListses = new ArrayList<EventLists>();
                        eventListses = Arrays.asList(gson.fromJson(reader, EventLists[].class));
                        content.close();

                        handlePostsList(eventListses);
                    } catch (Exception ex) {
                        failedLoad("Failed to parse JSON due to: ",ex);
                    }
                } else {
                    failedLoad("Server responded with status code: ", null);
                }
            } catch (Exception ex) {
                failedLoad("Failed to send HTTP POST request due to: ",ex);
            }
            return null;
        }
    }
}
